package ua.edu.fkzi.bank.exception;

public class EntityNotFoundException extends BankException {
    public EntityNotFoundException(String entityName, String id) {
        super(entityName + " з ідентифікатором " + id + " не знайдено");
    }
}
