package ua.edu.fkzi.bank.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class EventPublisher {
    private final List<EventObserver> observers = new ArrayList<>();

    public void subscribe(EventObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(EventObserver observer) {
        observers.remove(observer);
    }

    public void publish(
            EventType type,
            String message,
            Map<String, String> details
    ) {
        BankEvent event = new BankEvent(type, message, details);
        List.copyOf(observers).forEach(observer -> observer.onEvent(event));
    }
}
