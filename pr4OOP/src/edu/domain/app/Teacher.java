package edu.domain.app;

import edu.domain.core.Person;

public class Teacher extends Person {
    private String education;
    private String specialization;

    public Teacher() {}

    public Teacher(String firstName, String lastName, String email, String education, String specialization) {
        super(firstName, lastName, email);
        this.education = education; this.specialization = specialization;
    }

    public void createLesson() { System.out.println("Teacher.createLesson"); }
    public void developMaterials() { System.out.println("Teacher.developMaterials"); }
    public void conductOnlineLesson() { System.out.println("Teacher.conductOnlineLesson"); }
    public void evaluateStudents() { System.out.println("Teacher.evaluateStudents"); }
    public void communicateWithStudents() { System.out.println("Teacher.communicateWithStudents"); }

    public void assignCuratorTo(Student student) { student.assignCurator(this); }

    public void perform() { System.out.println("Teacher.perform"); }
    public void communicate() { System.out.println("Teacher.communicate"); }

    protected void setSpecialization(String specialization) { this.specialization = specialization; }
    void updateEducation(String education) { this.education = education; }

    public String getEducation() { return education; }
    public String getSpecialization() { return specialization; }

    public String toString() {
        return "Teacher : \n" +
                "FirstName : " + firstName + "\n" +
                "LastName : " + lastName + "\n" +
                "E-mail : " + email + "\n" +
                "Education : " + education + "\n" +
                "Specialization : " + specialization + "\n";
    }
}