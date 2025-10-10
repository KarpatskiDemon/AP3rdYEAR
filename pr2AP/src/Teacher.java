public class Teacher {
    private String firstName;
    private String lastName;
    private String email;
    private String education;
    private String specialization;

    public Teacher() {}

    public Teacher(String firstName, String lastName, String email, String education, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.education = education;
        this.specialization = specialization;
    }

    public void createLesson() {}
    public void developMaterials() {}
    public void conductOnlineLesson() {}
    public void evaluateStudents() {}
    public void communicateWithStudents() {}

    public String toString() {
        return "Teacher : \n" +
                "FirstName : " + firstName + "\n" +
                "LastName : " + lastName + "\n" +
                "E-mail : " + email + "\n" +
                "Education : " + education + "\n" +
                "Specialization : " + specialization + "\n";
    }
}
