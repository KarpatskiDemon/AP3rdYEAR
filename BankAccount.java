package ua.edu.fkzi.bank.event;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class BankEvent {
    private final EventType type;
    private final LocalDateTime occurredAt;
    private final String message;
    private final Map<String, String> details;

    public BankEvent(
            EventType type,
            String message,
            Map<String, String> details
    ) {
        this.type = Objects.requireNonNull(type);
        this.occurredAt = LocalDateTime.now();
        this.message = Objects.requireNonNull(message);
        this.details = Collections.unmodifiableMap(
                new LinkedHashMap<>(details == null ? Map.of() : details)
        );
    }

    public EventType getType() {
        return type;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getDetails() {
        return details;
    }
}
