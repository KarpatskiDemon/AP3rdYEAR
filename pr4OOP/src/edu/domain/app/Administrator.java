package edu.domain.app;

import edu.domain.core.Person;

public class Administrator extends Person {
    public Administrator() {}

    public Administrator(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void addUser() { System.out.println("Administrator.addUser"); }
    public void removeUser() { System.out.println("Administrator.removeUser"); }
    public void checkPayments() { System.out.println("Administrator.checkPayments"); }
    public void monitorSystem() { System.out.println("Administrator.monitorSystem"); }

    public void perform() { System.out.println("Administrator.perform"); }
    public void communicate() { System.out.println("Administrator.communicate"); }

    public String toString() { return super.toString().replace("Person", "Administrator"); }
}