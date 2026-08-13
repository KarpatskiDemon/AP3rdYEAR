package ua.edu.fkzi.bank.event;

public interface EventObserver {
    void onEvent(BankEvent event);
}
