package edu.domain.core;

public class CoreInspector {
    public static void inspect(Person p, LearningUnit u, Payment pay) {
        p.logInternal("person");
        u.prepare();
        pay.coreAudit(pay.paymentId);
    }
}