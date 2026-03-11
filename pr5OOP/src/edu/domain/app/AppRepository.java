package edu.domain.app;

import edu.domain.core.*;
import java.util.*;

public class AppRepository {
    public static final List<Person> persons = new ArrayList<>();
    public static final List<LearningUnit> units = new ArrayList<>();
    public static final List<Payment> payments = new ArrayList<>();

    public static void add(Person p) { persons.add(p); }
    public static void add(LearningUnit u) { units.add(u); }
    public static void add(Payment p) { payments.add(p); }

    public static void summary() {
        System.out.println("AppRepository.summary");
        System.out.println("persons=" + persons.size());
        System.out.println("units=" + units.size());
        System.out.println("payments=" + payments.size());
        System.out.println("Person.total=" + Person.total());
        System.out.println("LearningUnit.total=" + LearningUnit.total());
        System.out.println("Payment.total=" + Payment.total());
    }

    public static class Snapshots {
        public static List<String> personEmails() {
            List<String> r = new ArrayList<>();
            for (Person p : persons) r.add(p.getEmail());
            return r;
        }
    }
}