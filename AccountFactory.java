package ua.edu.fkzi.bank.ui;

import ua.edu.fkzi.bank.event.StatisticsCollector;
import ua.edu.fkzi.bank.exception.BankException;
import ua.edu.fkzi.bank.factory.AccountCreationRequest;
import ua.edu.fkzi.bank.memento.SnapshotManager;
import ua.edu.fkzi.bank.model.AccountType;
import ua.edu.fkzi.bank.model.Administrator;
import ua.edu.fkzi.bank.model.BankAccount;
import ua.edu.fkzi.bank.model.Customer;
import ua.edu.fkzi.bank.model.Transaction;
import ua.edu.fkzi.bank.repository.BankRepository;
import ua.edu.fkzi.bank.service.BankService;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public final class ConsoleUI {
    private static final Path SNAPSHOT_FILE = Path.of("bank-system.snapshot");
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Scanner scanner;
    private final BankRepository repository;
    private final BankService bankService;
    private final SnapshotManager snapshotManager;
    private final StatisticsCollector statisticsCollector;

    public ConsoleUI(
            Scanner scanner,
            BankRepository repository,
            BankService bankService,
            SnapshotManager snapshotManager,
            StatisticsCollector statisticsCollector
    ) {
        this.scanner = scanner;
        this.repository = repository;
        this.bankService = bankService;
        this.snapshotManager = snapshotManager;
        this.statisticsCollector = statisticsCollector;
    }

    public void run() {
        seedDemoData();
        boolean running = true;
        while (running) {
            printMainMenu();
            try {
                switch (readInt("Оберіть пункт: ")) {
                    case 1 -> administratorMode();
                    case 2 -> customerMode();
                    case 3 -> System.out.println(statisticsCollector.buildReport());
                    case 4 -> {
                        snapshotManager.saveToFile(SNAPSHOT_FILE);
                        System.out.println("Стан збережено у " + SNAPSHOT_FILE);
                    }
                    case 5 -> {
                        snapshotManager.loadFromFile(SNAPSHOT_FILE);
                        System.out.println("Стан завантажено з " + SNAPSHOT_FILE);
                    }
                    case 6 -> {
                        snapshotManager.restoreLast();
                        System.out.println("Останній знімок відновлено");
                    }
                    case 0 -> running = false;
                    default -> System.out.println("Невідомий пункт меню");
                }
            } catch (BankException | IllegalArgumentException exception) {
                System.out.println("Помилка: " + exception.getMessage());
            }
        }
        System.out.println("Роботу програми завершено.");
    }

    private void printMainMenu() {
        System.out.println("\n=== ЕЛЕКТРОННА БАНКІВСЬКА СИСТЕМА ===");
        System.out.println("1. Режим адміністратора");
        System.out.println("2. Режим клієнта");
        System.out.println("3. Статистика подій");
        System.out.println("4. Зберегти стан у файл");
        System.out.println("5. Завантажити стан з файлу");
        System.out.println("6. Відновити останній знімок");
        System.out.println("0. Вихід");
    }

    private void administratorMode() {
        List<Administrator> administrators = repository.getAllUsers().stream()
                .filter(Administrator.class::isInstance)
                .map(Administrator.class::cast)
                .toList();
        administrators.forEach(System.out::println);
        String administratorId = readText("ID адміністратора: ");

        boolean active = true;
        while (active) {
            System.out.println("\n--- МЕНЮ АДМІНІСТРАТОРА ---");
            System.out.println("1. Переглянути клієнтів");
            System.out.println("2. Зареєструвати клієнта");
            System.out.println("3. Відкрити рахунок");
            System.out.println("4. Переглянути всі рахунки");
            System.out.println("5. Заблокувати рахунок");
            System.out.println("6. Розблокувати рахунок");
            System.out.println("7. Нарахувати місячні відсотки");
            System.out.println("0. Назад");
            try {
                switch (readInt("Оберіть пункт: ")) {
                    case 1 -> repository.getAllCustomers().forEach(System.out::println);
                    case 2 -> createCustomer();
                    case 3 -> openAccount();
                    case 4 -> repository.getAllAccounts().forEach(System.out::println);
                    case 5 -> bankService.blockAccount(
                            administratorId, readText("ID рахунку: ")
                    );
                    case 6 -> bankService.unblockAccount(
                            administratorId, readText("ID рахунку: ")
                    );
                    case 7 -> System.out.println(
                            "Оброблено рахунків: "
                                    + bankService.accrueMonthlyInterest(administratorId)
                    );
                    case 0 -> active = false;
                    default -> System.out.println("Невідомий пункт меню");
                }
            } catch (BankException | IllegalArgumentException exception) {
                System.out.println("Помилка: " + exception.getMessage());
            }
        }
    }

    private void customerMode() {
        repository.getAllCustomers().forEach(System.out::println);
        String customerId = readText("ID клієнта: ");
        repository.findCustomer(customerId);

        boolean active = true;
        while (active) {
            System.out.println("\n--- МЕНЮ КЛІЄНТА ---");
            System.out.println("1. Переглянути власні рахунки");
            System.out.println("2. Поповнити рахунок");
            System.out.println("3. Зняти кошти");
            System.out.println("4. Здійснити переказ");
            System.out.println("5. Переглянути історію операцій");
            System.out.println("6. Сформувати виписку");
            System.out.println("0. Назад");
            try {
                switch (readInt("Оберіть пункт: ")) {
                    case 1 -> printCustomerAccounts(customerId);
                    case 2 -> customerDeposit(customerId);
                    case 3 -> customerWithdraw(customerId);
                    case 4 -> customerTransfer(customerId);
                    case 5 -> printHistory(customerId);
                    case 6 -> printStatement(customerId);
                    case 0 -> active = false;
                    default -> System.out.println("Невідомий пункт меню");
                }
            } catch (BankException | IllegalArgumentException exception) {
                System.out.println("Помилка: " + exception.getMessage());
            }
        }
    }

    private void createCustomer() {
        Customer customer = bankService.createCustomer(
                readText("ПІБ: "), readText("Електронна пошта: ")
        );
        System.out.println("Створено клієнта: " + customer);
    }

    private void openAccount() {
        repository.getAllCustomers().forEach(System.out::println);
        String ownerId = readText("ID клієнта: ");
        AccountType type = readAccountType();
        BigDecimal initialBalance = readMoney("Початковий баланс: ");
        BigDecimal interestRate = BigDecimal.ZERO;
        BigDecimal limit = BigDecimal.ZERO;

        if (type == AccountType.DEPOSIT) {
            interestRate = readMoney("Річна відсоткова ставка (%): ");
        } else if (type == AccountType.CREDIT) {
            limit = readMoney("Кредитний ліміт: ");
            interestRate = readMoney("Річна кредитна ставка (%): ");
        } else {
            limit = readMoney("Добовий ліміт переказів: ");
        }

        BankAccount account = bankService.openAccount(new AccountCreationRequest(
                type, ownerId, "UAH", initialBalance, interestRate, limit
        ));
        System.out.println("Відкрито рахунок: " + account);
    }

    private AccountType readAccountType() {
        System.out.println("1. Депозитний");
        System.out.println("2. Кредитний");
        System.out.println("3. Картковий");
        return switch (readInt("Тип рахунку: ")) {
            case 1 -> AccountType.DEPOSIT;
            case 2 -> AccountType.CREDIT;
            case 3 -> AccountType.CARD;
            default -> throw new IllegalArgumentException("Некоректний тип рахунку");
        };
    }

    private void printCustomerAccounts(String customerId) {
        List<BankAccount> accounts = repository.getAccountsByOwner(customerId);
        if (accounts.isEmpty()) {
            System.out.println("У клієнта немає рахунків");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    private void customerDeposit(String customerId) {
        String accountId = readText("ID рахунку: ");
        bankService.requireAccountOwner(customerId, accountId);
        bankService.deposit(accountId, readMoney("Сума: "));
        System.out.println("Рахунок успішно поповнено");
    }

    private void customerWithdraw(String customerId) {
        String accountId = readText("ID рахунку: ");
        bankService.requireAccountOwner(customerId, accountId);
        bankService.withdraw(accountId, readMoney("Сума: "));
        System.out.println("Кошти успішно знято");
    }

    private void customerTransfer(String customerId) {
        String sourceId = readText("ID рахунку-відправника: ");
        bankService.requireAccountOwner(customerId, sourceId);
        String targetId = readText("ID рахунку-отримувача: ");
        bankService.transfer(sourceId, targetId, readMoney("Сума: "));
        System.out.println("Переказ виконано");
    }

    private void printHistory(String customerId) {
        String accountId = readText("ID рахунку: ");
        bankService.requireAccountOwner(customerId, accountId);
        List<Transaction> transactions = repository.getTransactionsForAccount(accountId);
        if (transactions.isEmpty()) {
            System.out.println("Історія операцій порожня");
        } else {
            transactions.forEach(transaction ->
                    System.out.println(transaction.toStatementLine()));
        }
    }

    private void printStatement(String customerId) {
        String accountId = readText("ID рахунку: ");
        bankService.requireAccountOwner(customerId, accountId);
        LocalDate from = readDate("Початкова дата (дд.мм.рррр): ");
        LocalDate to = readDate("Кінцева дата (дд.мм.рррр): ");
        System.out.println(bankService.buildStatement(accountId, from, to));
    }

    private void seedDemoData() {
        if (!repository.isEmpty()) {
            return;
        }
        Administrator admin = bankService.createAdministrator(
                "Системний адміністратор", "admin@bank.ua"
        );
        Customer first = bankService.createCustomer(
                "Іван Петренко", "ivan.petrenko@example.com"
        );
        Customer second = bankService.createCustomer(
                "Марія Коваль", "maria.koval@example.com"
        );

        BankAccount deposit = bankService.openAccount(new AccountCreationRequest(
                AccountType.DEPOSIT, first.getId(), "UAH",
                new BigDecimal("25000"), new BigDecimal("12"), BigDecimal.ZERO
        ));
        BankAccount card = bankService.openAccount(new AccountCreationRequest(
                AccountType.CARD, first.getId(), "UAH",
                new BigDecimal("8000"), BigDecimal.ZERO,
                new BigDecimal("20000")
        ));
        BankAccount credit = bankService.openAccount(new AccountCreationRequest(
                AccountType.CREDIT, second.getId(), "UAH",
                BigDecimal.ZERO, new BigDecimal("36"),
                new BigDecimal("30000")
        ));
        bankService.transfer(card.getId(), credit.getId(), new BigDecimal("500"));

        System.out.println("\nДемонстраційні дані створено:");
        System.out.println("Адміністратор: " + admin.getId());
        System.out.println("Клієнт 1: " + first.getId() + " | рахунки: "
                + deposit.getId() + ", " + card.getId());
        System.out.println("Клієнт 2: " + second.getId() + " | рахунок: "
                + credit.getId());
    }

    private int readInt(String prompt) {
        while (true) {
            String value = readText(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Введіть ціле число");
            }
        }
    }

    private BigDecimal readMoney(String prompt) {
        while (true) {
            String value = readText(prompt).replace(',', '.');
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException exception) {
                System.out.println("Введіть коректне число");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            String value = readText(prompt);
            try {
                return LocalDate.parse(value, DATE_FORMATTER);
            } catch (DateTimeParseException exception) {
                System.out.println("Використовуйте формат дд.мм.рррр");
            }
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
