package edu.domain.app;

import edu.domain.core.LearningUnit;

public class LearningProcess extends LearningUnit {
    public LearningProcess() {}

    public LearningProcess(String lesson, String materials, String test) {
        super(lesson, materials, test);
    }

    public void evaluateKnowledge() { System.out.println("LearningProcess.evaluateKnowledge"); }
    public void teachStudent() { System.out.println("LearningProcess.teachStudent"); }
    public void checkHomework() { System.out.println("LearningProcess.checkHomework"); }
    public void communicate() { System.out.println("LearningProcess.communicate"); }

    public void process() { System.out.println("LearningProcess.process"); }

    void setLesson(String lesson) { this.lesson = lesson; }
    protected void setMaterials(String materials) { this.materials = materials; }
}