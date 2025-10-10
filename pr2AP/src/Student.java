public class Student {
    private String firstName;
    private String lastName;
    private String email;

    public Student() {}

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void attendLesson() {}
    public void doHomework() {}
    public void payForCourse() {}

    public String toString() {
        return "Student : \n" +
                "FirstName : " + firstName + "\n" +
                "LastName : " + lastName + "\n" +
                "E-mail : " + email + "\n";
    }
}
