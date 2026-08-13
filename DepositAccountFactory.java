package ua.edu.fkzi.bank.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Customer extends User {
    private static final long serialVersionUID = 1L;

    private final Set<String> accountIds = new LinkedHashSet<>();

    public Customer(String id, String fullName, String email) {
        super(id, fullName, email);
    }

    public void addAccount(String accountId) {
        accountIds.add(accountId);
    }

    public Set<String> getAccountIds() {
        return Collections.unmodifiableSet(accountIds);
    }

    @Override
    public String getRole() {
        return "CLIENT";
    }
}
