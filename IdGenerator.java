package ua.edu.fkzi.bank;

import ua.edu.fkzi.bank.event.AuditObserver;
import ua.edu.fkzi.bank.event.EventPublisher;
import ua.edu.fkzi.bank.event.StatisticsCollector;
import ua.edu.fkzi.bank.factory.AccountFactoryRegistry;
import ua.edu.fkzi.bank.memento.SnapshotManager;
import ua.edu.fkzi.bank.repository.BankRepository;
import ua.edu.fkzi.bank.service.BankService;
import ua.edu.fkzi.bank.ui.ConsoleUI;

import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        BankRepository repository = BankRepository.getInstance();
        EventPublisher eventPublisher = new EventPublisher();
        StatisticsCollector statisticsCollector = new StatisticsCollector();
        eventPublisher.subscribe(statisticsCollector);
        eventPublisher.subscribe(new AuditObserver());

        BankService bankService = new BankService(
                repository,
                new AccountFactoryRegistry(),
                eventPublisher
        );
        SnapshotManager snapshotManager = new SnapshotManager(
                repository,
                eventPublisher
        );

        try (Scanner scanner = new Scanner(System.in)) {
            new ConsoleUI(
                    scanner,
                    repository,
                    bankService,
                    snapshotManager,
                    statisticsCollector
            ).run();
        }
    }
}
