package edu.domain.app;

import edu.domain.core.*;

public class ObjectFactory {
    public static Teacher teacher(String f, String l, String e, String ed, String sp) {
        Teacher t = new Teacher(f, l, e, ed, sp); AppRepository.add(t); return t;
    }
    public static Administrator administrator(String f, String l, String e) {
        Administrator a = new Administrator(f, l, e); AppRepository.add(a); return a;
    }
    public static Student student(String f, String l, String e) {
        Student s = new Student(f, l, e); AppRepository.add(s); return s;
    }
    public static LearningProcess learningProcess(String lesson, String materials, String test) {
        LearningProcess lp = new LearningProcess(lesson, materials, test); AppRepository.add(lp); return lp;
    }
    public static AdvancedLearningProcess advancedLearningProcess(String lesson, String materials, String test) {
        AdvancedLearningProcess ap = new AdvancedLearningProcess(lesson, materials, test); AppRepository.add(ap); return ap;
    }
    public static OnlinePayment onlinePayment(double amount, String status, String date, String pid) {
        OnlinePayment p = new OnlinePayment(amount, status, date, pid); AppRepository.add(p); return p;
    }
    public static CashPayment cashPayment(double amount, String status, String date, String pid) {
        CashPayment p = new CashPayment(amount, status, date, pid); AppRepository.add(p); return p;
    }
}