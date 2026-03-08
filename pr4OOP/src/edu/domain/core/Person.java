package edu.domain.core;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected String email;

    protected Person() {}

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName; this.lastName = lastName; this.email = email;
    }

    public abstract void perform();

    public void communicate() { System.out.println(getClass().getSimpleName() + ".communicate"); }

    void logInternal(String msg) { System.out.println("core:" + msg); }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }

    public String toString() {
        return getClass().getSimpleName() + " : \n" +
                "FirstName : " + firstName + "\n" +
                "LastName : " + lastName + "\n" +
                "E-mail : " + email + "\n";
    }
}