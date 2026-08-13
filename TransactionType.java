package ua.edu.fkzi.bank.event;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class StatisticsCollector implements EventObserver {
    private final Map<EventType, Integer> eventCounts =
            new EnumMap<>(EventType.class);
    private final Map<String, Integer> accountInteractions =
            new LinkedHashMap<>();

    @Override
    public void onEvent(BankEvent event) {
        eventCounts.merge(event.getType(), 1, Integer::sum);
        String accountId = event.getDetails().get("accountId");
        if (accountId != null) {
            accountInteractions.merge(accountId, 1, Integer::sum);
        }
        String sourceAccountId = event.getDetails().get("sourceAccountId");
        String targetAccountId = event.getDetails().get("targetAccountId");
        if (sourceAccountId != null) {
            accountInteractions.merge(sourceAccountId, 1, Integer::sum);
        }
        if (targetAccountId != null) {
            accountInteractions.merge(targetAccountId, 1, Integer::sum);
        }
    }

    public Map<EventType, Integer> getEventCounts() {
        return Map.copyOf(eventCounts);
    }

    public int getTotalEventCount() {
        return eventCounts.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String buildReport() {
        String events = eventCounts.entrySet().stream()
                .map(entry -> String.format("%-24s : %d", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
        String accounts = accountInteractions.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> entry.getKey() + " : " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
        return "СТАТИСТИКА ПОДІЙ\n"
                + (events.isBlank() ? "Подій ще немає" : events)
                + "\n\nНайактивніші рахунки\n"
                + (accounts.isBlank() ? "Даних ще немає" : accounts)
                + "\n\nЗагальна кількість подій: " + getTotalEventCount();
    }
}
