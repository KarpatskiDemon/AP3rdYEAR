package edu.domain.app;

import edu.domain.core.Person;

public class Student extends Person {
    Student() {}

    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void attendLesson() { System.out.println("Student.attendLesson"); }
    public void doHomework() { System.out.println("Student.doHomework"); }
    public void payForCourse() { System.out.println("Student.payForCourse"); }

    public void perform() { System.out.println("Student.perform"); }
    public void communicate() { System.out.println("Student.communicate"); }

    void markAttendance() { System.out.println("Student.markAttendance"); }
    void assignCurator(Teacher t) { System.out.println("Student.assignCurator"); }

    public String toString() { return super.toString(); }
}
