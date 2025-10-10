public class LearningProcess {
    private String lesson;
    private String materials;
    private String test;

    public LearningProcess() {}

    public LearningProcess(String lesson, String materials, String test) {
        this.lesson = lesson;
        this.materials = materials;
        this.test = test;
    }

    public void evaluateKnowledge() {}
    public void teachStudent() {}
    public void checkHomework() {}
    public void communicate() {}

    public String toString() {
        return "LearningProcess : \n" +
                "Lesson : " + lesson + "\n" +
                "Materials : " + materials + "\n" +
                "Test : " + test + "\n";
    }
}
