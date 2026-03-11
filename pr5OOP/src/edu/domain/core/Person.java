package edu.domain.core;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected String email;

    private static int count = 0;

    protected Person() { count++; }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName; this.lastName = lastName; this.email = email; count++;
    }

    public static int total() { return count; }

    public static class NameInfo {
        public final String first;
        public final String last;
        public NameInfo(String first, String last) { this.first = first; this.last = last; }
        public String toString() { return first + " " + last; }
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