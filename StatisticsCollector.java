package ua.edu.fkzi.bank.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String accountId, BigDecimal amount) {
        super("Недостатньо коштів або кредитного ліміту на рахунку "
                + accountId + " для операції на суму " + amount);
    }
}
