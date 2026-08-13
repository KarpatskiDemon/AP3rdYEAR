package ua.edu.fkzi.bank.memento;

import ua.edu.fkzi.bank.event.EventPublisher;
import ua.edu.fkzi.bank.event.EventType;
import ua.edu.fkzi.bank.exception.BankException;
import ua.edu.fkzi.bank.repository.BankRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public final class SnapshotManager {
    private final BankRepository repository;
    private final EventPublisher eventPublisher;
    private final Deque<BankSystemMemento> history = new ArrayDeque<>();

    public SnapshotManager(
            BankRepository repository,
            EventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public BankSystemMemento createSnapshot() {
        BankSystemMemento memento = BankSystemMemento.capture(
                repository.exportState()
        );
        history.push(memento);
        eventPublisher.publish(
                EventType.STATE_SAVED,
                "Створено знімок стану системи",
                Map.of("createdAt", memento.getCreatedAt().toString())
        );
        return memento;
    }

    public void restoreLast() {
        if (history.isEmpty()) {
            throw new BankException("Відсутній знімок для відновлення");
        }
        BankSystemMemento memento = history.peek();
        repository.importState(memento.restore());
        eventPublisher.publish(
                EventType.STATE_RESTORED,
                "Відновлено останній знімок стану",
                Map.of("createdAt", memento.getCreatedAt().toString())
        );
    }

    public void saveToFile(Path path) {
        BankSystemMemento memento = createSnapshot();
        try (ObjectOutputStream output = new ObjectOutputStream(
                Files.newOutputStream(path))) {
            output.writeObject(memento);
        } catch (IOException exception) {
            throw new BankException("Не вдалося зберегти файл: "
                    + exception.getMessage());
        }
    }

    public void loadFromFile(Path path) {
        if (!Files.exists(path)) {
            throw new BankException("Файл знімка не знайдено: " + path);
        }
        try (ObjectInputStream input = new ObjectInputStream(
                Files.newInputStream(path))) {
            BankSystemMemento memento = (BankSystemMemento) input.readObject();
            history.push(memento);
            repository.importState(memento.restore());
            eventPublisher.publish(
                    EventType.STATE_RESTORED,
                    "Стан системи завантажено з файлу",
                    Map.of("path", path.toAbsolutePath().toString())
            );
        } catch (IOException | ClassNotFoundException exception) {
            throw new BankException("Не вдалося завантажити файл: "
                    + exception.getMessage());
        }
    }
}
