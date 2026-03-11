package edu.domain.app;

public class AdvancedLearningProcess extends LearningProcess {
    public AdvancedLearningProcess() {}

    public AdvancedLearningProcess(String lesson, String materials, String test) {
        super(lesson, materials, test);
    }

    @Override
    public void process() { System.out.println("AdvancedLearningProcess.process"); }

    @Override
    public void evaluate() { System.out.println("AdvancedLearningProcess.evaluate"); }

    @Override
    public String describe() { return "Advanced: " + getLesson(); }
}
