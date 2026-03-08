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

    public void assignCuratorTo(Student student) { student.assignCurator(this); }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public String getEducation() { return education; }

    public String getSpecialization() { return specialization; }

    protected void setSpecialization(String specialization) { this.specialization = specialization; }

    void updateEducation(String education) { this.education = education; }

    public String toString() {
        return "Teacher : \n" +
                "FirstName : " + firstName + "\n" +
                "LastName : " + lastName + "\n" +
                "E-mail : " + email + "\n" +
                "Education : " + education + "\n" +
                "Specialization : " + specialization + "\n";
    }
}
