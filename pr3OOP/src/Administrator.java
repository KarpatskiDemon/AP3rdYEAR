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

    public void verifyPayment(Payment payment) { payment.markAccepted(); }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    protected void updateEmail(String email) { this.email = email; }

    public String toString() {
        return "Administrator : \n" +
                "Name : " + firstName + "\n" +
                "Lastname : " + lastName + "\n" +
                "E-mail : " + email + "\n";
    }
}