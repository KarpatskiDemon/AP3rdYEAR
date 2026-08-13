package ua.edu.fkzi.bank.repository;

import ua.edu.fkzi.bank.exception.EntityNotFoundException;
import ua.edu.fkzi.bank.exception.ValidationException;
import ua.edu.fkzi.bank.memento.BankState;
import ua.edu.fkzi.bank.model.BankAccount;
import ua.edu.fkzi.bank.model.Customer;
import ua.edu.fkzi.bank.model.Transaction;
import ua.edu.fkzi.bank.model.TransactionType;
import ua.edu.fkzi.bank.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BankRepository {
    private static final BankRepository INSTANCE = new BankRepository();

    private final Map<String, User> users = new LinkedHashMap<>();
    private final Map<String, BankAccount> accounts = new LinkedHashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final Set<String> registeredEmails = new LinkedHashSet<>();

    private BankRepository() {
    }

    public static BankRepository getInstance() {
        return INSTANCE;
    }

    public synchronized void addUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Користувач з таким ID вже існує");
        }
        if (!registeredEmails.add(user.getEmail())) {
            throw new ValidationException("Електронна пошта вже використовується");
        }
        users.put(user.getId(), user);
    }

    public synchronized void addAccount(BankAccount account) {
        if (accounts.containsKey(account.getId())) {
            throw new ValidationException("Рахунок з таким ID вже існує");
        }
        User owner = findUser(account.getOwnerId());
        if (!(owner instanceof Customer customer)) {
            throw new ValidationException("Рахунок може належати лише клієнту");
        }
        accounts.put(account.getId(), account);
        customer.addAccount(account.getId());
    }

    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public synchronized User findUser(String id) {
        User user = users.get(id);
        if (user == null) {
            throw new EntityNotFoundException("Користувача", id);
        }
        return user;
    }

    public synchronized Customer findCustomer(String id) {
        User user = findUser(id);
        if (!(user instanceof Customer customer)) {
            throw new ValidationException("Користувач " + id + " не є клієнтом");
        }
        return customer;
    }

    public synchronized BankAccount findAccount(String id) {
        BankAccount account = accounts.get(id);
        if (account == null) {
            throw new EntityNotFoundException("Рахунок", id);
        }
        return account;
    }

    public synchronized List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    public synchronized List<Customer> getAllCustomers() {
        return users.values().stream()
                .filter(Customer.class::isInstance)
                .map(Customer.class::cast)
                .toList();
    }

    public synchronized List<BankAccount> getAllAccounts() {
        return List.copyOf(accounts.values());
    }

    public synchronized List<BankAccount> getAccountsByOwner(String ownerId) {
        return accounts.values().stream()
                .filter(account -> account.getOwnerId().equals(ownerId))
                .toList();
    }

    public synchronized List<Transaction> getTransactionsForAccount(String accountId) {
        return transactions.stream()
                .filter(transaction -> transaction.getAccountId().equals(accountId))
                .sorted(Comparator.comparing(Transaction::getOccurredAt))
                .toList();
    }

    public synchronized List<Transaction> getAllTransactions() {
        return List.copyOf(transactions);
    }

    public synchronized BigDecimal getTransferOutAmountForDate(
            String accountId,
            LocalDate date
    ) {
        return transactions.stream()
                .filter(transaction -> transaction.getAccountId().equals(accountId))
                .filter(transaction -> transaction.getType() == TransactionType.TRANSFER_OUT)
                .filter(transaction -> transaction.getOccurredAt().toLocalDate().equals(date))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public synchronized boolean isEmpty() {
        return users.isEmpty() && accounts.isEmpty();
    }

    public synchronized BankState exportState() {
        return new BankState(users, accounts, transactions, registeredEmails);
    }

    public synchronized void importState(BankState state) {
        users.clear();
        accounts.clear();
        transactions.clear();
        registeredEmails.clear();
        users.putAll(state.getUsers());
        accounts.putAll(state.getAccounts());
        transactions.addAll(state.getTransactions());
        registeredEmails.addAll(state.getRegisteredEmails());
    }
}
