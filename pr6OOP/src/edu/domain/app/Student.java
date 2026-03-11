package edu.domain.app;

import edu.domain.core.Person;

public class Student extends Person {
    public Student() {}

    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void attendLesson() { System.out.println("Student.attendLesson"); }
    public void doHomework() { System.out.println("Student.doHomework"); }
    public void payForCourse() { System.out.println("Student.payForCourse"); }

    @Override
    public void perform() { System.out.println("Student.perform"); }

    @Override
    public void communicate() { System.out.println("Student.communicate"); }

    @Override
    public String roleInfo() { return "Student role"; }

    void markAttendance() { System.out.println("Student.markAttendance"); }
    void assignCurator(Teacher t) { System.out.println("Student.assignCurator"); }

    public String toString() { return super.toString(); }
}