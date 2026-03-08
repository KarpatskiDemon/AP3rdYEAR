package edu.domain.core;

public abstract class LearningUnit {
    protected String lesson;
    protected String materials;
    protected String test;

    protected LearningUnit() {
    }

    public LearningUnit(String lesson, String materials, String test) {
        this.lesson = lesson;
        this.materials = materials;
        this.test = test;
    }

    public abstract void process();

    public void evaluate() {
        System.out.println(getClass().getSimpleName() + ".evaluate");
    }

    void prepare() {
        System.out.println("core.prepare:" + lesson);
    }

    public String getLesson() {
        return lesson;
    }

    public String getMaterials() {
        return materials;
    }

    public String getTest() {
        return test;
    }

    public String toString() {
        return getClass().getSimpleName() + " : \n" +
                "Lesson : " + lesson + "\n" +
                "Materials : " + materials + "\n" +
                "Test : " + test + "\n";
    }
}