public class Administrator {
    private String firstName;
    private String lastName;
    private String email;

    public Administrator() {}

    public Administrator(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void addUser() {}
    public void removeUser() {}
    public void checkPayments() {}
    public void monitorSystem() {}

    public String toString() {
        return "Administrator : \n" +
                "Name : " + firstName + "\n" +
                "Lastname : " + lastName + "\n" +
                "E-mail : " + email + "\n";
    }
}
