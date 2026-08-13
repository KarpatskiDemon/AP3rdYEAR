package ua.edu.fkzi.bank.model;

import ua.edu.fkzi.bank.util.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final String id;
    private final String correlationId;
    private final LocalDateTime occurredAt;
    private final TransactionType type;
    private final String accountId;
    private final String relatedAccountId;
    private final BigDecimal amount;
    private final BigDecimal balanceAfter;
    private final String description;

    public Transaction(
            String id,
            String correlationId,
            TransactionType type,
            String accountId,
            String relatedAccountId,
            BigDecimal amount,
            BigDecimal balanceAfter,
            String description
    ) {
        this.id = Objects.requireNonNull(id);
        this.correlationId = Objects.requireNonNull(correlationId);
        this.occurredAt = LocalDateTime.now();
        this.type = Objects.requireNonNull(type);
        this.accountId = Objects.requireNonNull(accountId);
        this.relatedAccountId = relatedAccountId;
        this.amount = Money.requirePositive(amount);
        this.balanceAfter = Money.normalize(balanceAfter);
        this.description = description == null ? "" : description.trim();
    }

    public String getId() {
        return id;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public TransactionType getType() {
        return type;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getRelatedAccountId() {
        return relatedAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public String toStatementLine() {
        String related = relatedAccountId == null ? "-" : relatedAccountId;
        return String.format(
                "%s | %-15s | %10s | пов'язаний: %-15s | баланс: %10s | %s",
                occurredAt.format(FORMATTER), type, amount, related,
                balanceAfter, description
        );
    }
}
