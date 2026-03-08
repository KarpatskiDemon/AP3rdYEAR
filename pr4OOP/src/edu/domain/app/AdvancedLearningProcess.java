package edu.domain.app;

public class AdvancedLearningProcess extends LearningProcess {
    public AdvancedLearningProcess() {}

    public AdvancedLearningProcess(String lesson, String materials, String test) {
        super(lesson, materials, test);
    }

    public void process() { System.out.println("AdvancedLearningProcess.process"); }
}
