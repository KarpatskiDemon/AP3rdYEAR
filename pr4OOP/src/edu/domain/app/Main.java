package edu.domain.app;

import edu.domain.core.CoreInspector;
import edu.domain.core.Person;
import edu.domain.core.Payment;
import edu.domain.core.LearningUnit;

public class Main {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("Irina", "Bograch", "irina.bograch@tuke", "Mgr", "EnglishLanguage");
        Administrator admin = new Administrator("Igor", "Shkolniy", "igor.shkolniy@tuke");
        Student student = new Student("Maria", "Rudenko", "maria.rudenko@student.tuke");

        LearningProcess process = new LearningProcess("English", "PDF materials", "Test №1");
        AdvancedLearningProcess adv = new AdvancedLearningProcess("English Advanced", "Docs", "Test №2");

        OnlinePayment op = new OnlinePayment(150.00, "PENDING", "2018-07-07", "PAY001");
        CashPayment cp = new CashPayment(100.00, "PENDING", "2018-08-08", "PAY002");

        teacher.assignCuratorTo(student);
        teacher.conductOnlineLesson();
        student.attendLesson();
        student.doHomework();

        Person[] people = new Person[] { student, teacher, admin };
        for (Person p : people) { p.perform(); p.communicate(); }

        LearningUnit[] units = new LearningUnit[] { process, adv };
        for (LearningUnit u : units) { u.process(); u.evaluate(); }

        Payment[] pays = new Payment[] { op, cp };
        for (Payment p : pays) { p.acceptPayment(); p.checkStatus(); System.out.println(p.generateReceipt()); }

        CoreInspector.inspect(teacher, process, op);
    }
}