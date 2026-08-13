package ua.edu.fkzi.bank.model;

import ua.edu.fkzi.bank.exception.AccountBlockedException;
import ua.edu.fkzi.bank.exception.BankException;
import ua.edu.fkzi.bank.exception.InsufficientFundsException;
import ua.edu.fkzi.bank.util.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String ownerId;
    private final String currency;
    private final LocalDateTime createdAt;
    private BigDecimal balance;
    private AccountStatus status;

    protected BankAccount(
            String id,
            String ownerId,
            String currency,
            BigDecimal initialBalance
    ) {
        this.id = Objects.requireNonNull(id);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.currency = Objects.requireNonNull(currency).toUpperCase();
        this.balance = Money.requireNonNegative(initialBalance, "Початковий баланс");
        this.status = AccountStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public final void deposit(BigDecimal amount) {
        ensureActive();
        adjustBalance(Money.requirePositive(amount));
    }

    public final void withdraw(BigDecimal amount) {
        ensureActive();
        BigDecimal normalized = Money.requirePositive(amount);
        if (!canDebit(normalized)) {
            throw new InsufficientFundsException(id, normalized);
        }
        adjustBalance(normalized.negate());
    }

    protected abstract boolean canDebit(BigDecimal amount);

    public abstract AccountType getType();

    public abstract BigDecimal applyMonthlyInterest();

    public abstract String getConditions();

    protected final void adjustBalance(BigDecimal delta) {
        balance = Money.normalize(balance.add(delta));
    }

    public final void block() {
        if (status == AccountStatus.CLOSED) {
            throw new BankException("Закритий рахунок неможливо заблокувати");
        }
        status = AccountStatus.BLOCKED;
    }

    public final void unblock() {
        if (status == AccountStatus.CLOSED) {
            throw new BankException("Закритий рахунок неможливо розблокувати");
        }
        status = AccountStatus.ACTIVE;
    }

    public final void ensureActive() {
        if (status != AccountStatus.ACTIVE) {
            throw new AccountBlockedException(id);
        }
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BankAccount other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " | " + getType() + " | баланс: " + balance + " "
                + currency + " | " + status + " | " + getConditions();
    }
}
