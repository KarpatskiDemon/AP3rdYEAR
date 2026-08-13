package ua.edu.fkzi.bank.memento;

import ua.edu.fkzi.bank.model.BankAccount;
import ua.edu.fkzi.bank.model.Transaction;
import ua.edu.fkzi.bank.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BankState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<String, User> users;
    private final Map<String, BankAccount> accounts;
    private final List<Transaction> transactions;
    private final Set<String> registeredEmails;

    public BankState(
            Map<String, User> users,
            Map<String, BankAccount> accounts,
            List<Transaction> transactions,
            Set<String> registeredEmails
    ) {
        this.users = new LinkedHashMap<>(users);
        this.accounts = new LinkedHashMap<>(accounts);
        this.transactions = new ArrayList<>(transactions);
        this.registeredEmails = new LinkedHashSet<>(registeredEmails);
    }

    public Map<String, User> getUsers() {
        return new LinkedHashMap<>(users);
    }

    public Map<String, BankAccount> getAccounts() {
        return new LinkedHashMap<>(accounts);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public Set<String> getRegisteredEmails() {
        return new LinkedHashSet<>(registeredEmails);
    }
}
