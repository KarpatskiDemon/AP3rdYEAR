package ua.edu.fkzi.bank.exception;

public class AccountBlockedException extends BankException {
    public AccountBlockedException(String accountId) {
        super("Рахунок " + accountId + " заблоковано");
    }
}
