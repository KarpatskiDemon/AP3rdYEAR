package edu.domain.core;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected String email;

    private static int count = 0;

    protected Person() { count++; }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        count++;
    }

    public static int total() { return count; }

    public abstract void perform();
    public abstract void communicate();
    public abstract String roleInfo();

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