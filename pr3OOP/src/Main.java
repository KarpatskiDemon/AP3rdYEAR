public class Main {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("Irina", "Bograch", "irina.bograch@tuke", "Mgr", "EnglishLanguage");
        Administrator admin = new Administrator("Igor", "Shkolniy", "igor.shkolniy@tuke");
        Student student = new Student("Maria", "Rudenko", "maria.rudenko@student.tuke");
        LearningProcess process = new LearningProcess("English", "PDF materials", "Test №1");
        Payment payment = new Payment(150.00, "Paid", "2018-07-07", "PAY001");

        admin.verifyPayment(payment);

        System.out.println(teacher);
        System.out.println(admin);
        System.out.println(student);
        System.out.println(process);
        System.out.println(payment);
    }
}