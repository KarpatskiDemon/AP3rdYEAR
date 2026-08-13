package ua.edu.fkzi.bank.event;

import java.time.format.DateTimeFormatter;

public final class AuditObserver implements EventObserver {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void onEvent(BankEvent event) {
        System.out.printf(
                "[AUDIT %s] %-20s %s%n",
                event.getOccurredAt().format(FORMATTER),
                event.getType(),
                event.getMessage()
        );
    }
}
