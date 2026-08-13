package ua.edu.fkzi.bank.memento;

import ua.edu.fkzi.bank.exception.BankException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

public final class BankSystemMemento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final LocalDateTime createdAt;
    private final byte[] serializedState;

    private BankSystemMemento(byte[] serializedState) {
        this.createdAt = LocalDateTime.now();
        this.serializedState = serializedState.clone();
    }

    public static BankSystemMemento capture(BankState state) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream output = new ObjectOutputStream(bytes)) {
            output.writeObject(state);
            output.flush();
            return new BankSystemMemento(bytes.toByteArray());
        } catch (IOException exception) {
            throw new BankException("Не вдалося створити знімок стану: "
                    + exception.getMessage());
        }
    }

    public BankState restore() {
        try (ByteArrayInputStream bytes = new ByteArrayInputStream(serializedState);
             ObjectInputStream input = new ObjectInputStream(bytes)) {
            return (BankState) input.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new BankException("Не вдалося відновити знімок стану: "
                    + exception.getMessage());
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
