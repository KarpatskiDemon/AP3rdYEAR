package ua.edu.fkzi.bank.service;

import ua.edu.fkzi.bank.event.EventPublisher;
import ua.edu.fkzi.bank.event.EventType;
import ua.edu.fkzi.bank.exception.AuthorizationException;
import ua.edu.fkzi.bank.exception.TransferLimitExceededException;
import ua.edu.fkzi.bank.exception.ValidationException;
import ua.edu.fkzi.bank.factory.AccountCreationRequest;
import ua.edu.fkzi.bank.factory.AccountFactoryRegistry;
import ua.edu.fkzi.bank.model.Administrator;
import ua.edu.fkzi.bank.model.BankAccount;
import ua.edu.fkzi.bank.model.CardAccount;
import ua.edu.fkzi.bank.model.Customer;
import ua.edu.fkzi.bank.model.Transaction;
import ua.edu.fkzi.bank.model.TransactionType;
import ua.edu.fkzi.bank.model.User;
import ua.edu.fkzi.bank.repository.BankRepository;
import ua.edu.fkzi.bank.util.IdGenerator;
import ua.edu.fkzi.bank.util.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public final class BankService {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final BankRepository repository;
    private final AccountFactoryRegistry factoryRegistry;
    private final EventPublisher eventPublisher;

    public BankService(
            BankRepository repository,
            AccountFactoryRegistry factoryRegistry,
            EventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.factoryRegistry = factoryRegistry;
        this.eventPublisher = eventPublisher;
    }

    public Customer createCustomer(String fullName, String email) {
        Customer customer = new Customer(
                IdGenerator.next("USR"), fullName, email
        );
        repository.addUser(customer);
        eventPublisher.publish(
                EventType.USER_CREATED,
                "Зареєстровано клієнта " + customer.getFullName(),
                Map.of("userId", customer.getId(), "role", customer.getRole())
        );
        return customer;
    }

    public Administrator createAdministrator(String fullName, String email) {
        Administrator administrator = new Administrator(
                IdGenerator.next("ADM"), fullName, email
        );
        repository.addUser(administrator);
        eventPublisher.publish(
                EventType.USER_CREATED,
                "Створено адміністратора " + administrator.getFullName(),
                Map.of("userId", administrator.getId(),
                        "role", administrator.getRole())
        );
        return administrator;
    }

    public BankAccount openAccount(AccountCreationRequest request) {
        repository.findCustomer(request.getOwnerId());
        BankAccount account = factoryRegistry.create(request);
        repository.addAccount(account);

        if (account.getBalance().signum() > 0) {
            recordTransaction(
                    TransactionType.DEPOSIT,
                    account,
                    null,
                    account.getBalance(),
                    "Початковий внесок",
                    IdGenerator.next("OP")
            );
        }

        eventPublisher.publish(
                EventType.ACCOUNT_OPENED,
                "Відкрито рахунок " + account.getId(),
                Map.of(
                        "accountId", account.getId(),
                        "ownerId", account.getOwnerId(),
                        "type", account.getType().name()
                )
        );
        return account;
    }

    public void deposit(String accountId, BigDecimal amount) {
        BankAccount account = repository.findAccount(accountId);
        BigDecimal normalized = Money.requirePositive(amount);
        account.deposit(normalized);
        recordTransaction(
                TransactionType.DEPOSIT,
                account,
                null,
                normalized,
                "Поповнення рахунку",
                IdGenerator.next("OP")
        );
        eventPublisher.publish(
                EventType.MONEY_DEPOSITED,
                "Рахунок поповнено на " + normalized,
                Map.of("accountId", accountId, "amount", normalized.toString())
        );
    }

    public void withdraw(String accountId, BigDecimal amount) {
        BankAccount account = repository.findAccount(accountId);
        BigDecimal normalized = Money.requirePositive(amount);
        account.withdraw(normalized);
        recordTransaction(
                TransactionType.WITHDRAWAL,
                account,
                null,
                normalized,
                "Зняття коштів",
                IdGenerator.next("OP")
        );
        eventPublisher.publish(
                EventType.MONEY_WITHDRAWN,
                "З рахунку знято " + normalized,
                Map.of("accountId", accountId, "amount", normalized.toString())
        );
    }

    public void transfer(
            String sourceAccountId,
            String targetAccountId,
            BigDecimal amount
    ) {
        if (sourceAccountId.equals(targetAccountId)) {
            throw new ValidationException("Рахунки відправника й отримувача збігаються");
        }

        BankAccount source = repository.findAccount(sourceAccountId);
        BankAccount target = repository.findAccount(targetAccountId);
        BigDecimal normalized = Money.requirePositive(amount);
        source.ensureActive();
        target.ensureActive();

        if (!source.getCurrency().equals(target.getCurrency())) {
            throw new ValidationException("Переказ між різними валютами не підтримується");
        }

        validateDailyLimit(source, normalized);
        source.withdraw(normalized);
        target.deposit(normalized);

        String correlationId = IdGenerator.next("TRF");
        recordTransaction(
                TransactionType.TRANSFER_OUT,
                source,
                target.getId(),
                normalized,
                "Переказ на рахунок " + target.getId(),
                correlationId
        );
        recordTransaction(
                TransactionType.TRANSFER_IN,
                target,
                source.getId(),
                normalized,
                "Переказ з рахунку " + source.getId(),
                correlationId
        );

        eventPublisher.publish(
                EventType.TRANSFER_COMPLETED,
                "Виконано переказ на суму " + normalized,
                Map.of(
                        "sourceAccountId", sourceAccountId,
                        "targetAccountId", targetAccountId,
                        "amount", normalized.toString()
                )
        );
    }

    private void validateDailyLimit(BankAccount account, BigDecimal amount) {
        if (account instanceof CardAccount cardAccount) {
            BigDecimal transferredToday = repository.getTransferOutAmountForDate(
                    account.getId(), LocalDate.now()
            );
            if (transferredToday.add(amount)
                    .compareTo(cardAccount.getDailyTransferLimit()) > 0) {
                throw new TransferLimitExceededException(
                        account.getId(), cardAccount.getDailyTransferLimit()
                );
            }
        }
    }

    public void blockAccount(String administratorId, String accountId) {
        requireAdministrator(administratorId);
        BankAccount account = repository.findAccount(accountId);
        account.block();
        eventPublisher.publish(
                EventType.ACCOUNT_BLOCKED,
                "Рахунок заблоковано адміністратором",
                Map.of("accountId", accountId, "administratorId", administratorId)
        );
    }

    public void unblockAccount(String administratorId, String accountId) {
        requireAdministrator(administratorId);
        BankAccount account = repository.findAccount(accountId);
        account.unblock();
        eventPublisher.publish(
                EventType.ACCOUNT_UNBLOCKED,
                "Рахунок розблоковано адміністратором",
                Map.of("accountId", accountId, "administratorId", administratorId)
        );
    }

    public int accrueMonthlyInterest(String administratorId) {
        requireAdministrator(administratorId);
        int changedAccounts = 0;
        for (BankAccount account : repository.getAllAccounts()) {
            BigDecimal delta = account.applyMonthlyInterest();
            if (delta.signum() == 0) {
                continue;
            }
            TransactionType type = delta.signum() > 0
                    ? TransactionType.INTEREST_CREDIT
                    : TransactionType.INTEREST_CHARGE;
            recordTransaction(
                    type,
                    account,
                    null,
                    delta.abs(),
                    type == TransactionType.INTEREST_CREDIT
                            ? "Нарахування відсотків"
                            : "Списання кредитних відсотків",
                    IdGenerator.next("INT")
            );
            changedAccounts++;
            eventPublisher.publish(
                    EventType.INTEREST_ACCRUED,
                    "Оброблено відсотки для рахунку " + account.getId(),
                    Map.of(
                            "accountId", account.getId(),
                            "delta", delta.toString()
                    )
            );
        }
        return changedAccounts;
    }

    public String buildStatement(
            String accountId,
            LocalDate from,
            LocalDate to
    ) {
        if (from.isAfter(to)) {
            throw new ValidationException("Початкова дата пізніша за кінцеву");
        }
        BankAccount account = repository.findAccount(accountId);
        LocalDateTime fromTime = from.atStartOfDay();
        LocalDateTime toTime = to.atTime(LocalTime.MAX);
        List<Transaction> selected = repository.getTransactionsForAccount(accountId)
                .stream()
                .filter(transaction -> !transaction.getOccurredAt().isBefore(fromTime))
                .filter(transaction -> !transaction.getOccurredAt().isAfter(toTime))
                .toList();

        StringBuilder result = new StringBuilder();
        result.append("ВИПИСКА ЗА РАХУНКОМ ").append(accountId).append('\n');
        result.append("Тип: ").append(account.getType()).append('\n');
        result.append("Період: ").append(from.format(DATE_FORMATTER))
                .append(" - ").append(to.format(DATE_FORMATTER)).append('\n');
        result.append("Поточний баланс: ").append(account.getBalance()).append(' ')
                .append(account.getCurrency()).append("\n\n");

        if (selected.isEmpty()) {
            result.append("За обраний період операцій немає.");
        } else {
            selected.forEach(transaction -> result
                    .append(transaction.toStatementLine()).append('\n'));
        }
        return result.toString();
    }

    public void requireAccountOwner(String customerId, String accountId) {
        BankAccount account = repository.findAccount(accountId);
        if (!account.getOwnerId().equals(customerId)) {
            throw new AuthorizationException(
                    "Клієнт не має доступу до рахунку " + accountId
            );
        }
    }

    private Administrator requireAdministrator(String administratorId) {
        User user = repository.findUser(administratorId);
        if (!(user instanceof Administrator administrator)) {
            throw new AuthorizationException("Операція доступна лише адміністратору");
        }
        return administrator;
    }

    private void recordTransaction(
            TransactionType type,
            BankAccount account,
            String relatedAccountId,
            BigDecimal amount,
            String description,
            String correlationId
    ) {
        repository.addTransaction(new Transaction(
                IdGenerator.next("TX"),
                correlationId,
                type,
                account.getId(),
                relatedAccountId,
                amount,
                account.getBalance(),
                description
        ));
    }
}
