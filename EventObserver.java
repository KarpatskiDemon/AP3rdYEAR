package ua.edu.fkzi.bank.exception;

import java.math.BigDecimal;

public class TransferLimitExceededException extends BankException {
    public TransferLimitExceededException(String accountId, BigDecimal limit) {
        super("Перевищено добовий ліміт переказів для рахунку "
                + accountId + ": " + limit);
    }
}
