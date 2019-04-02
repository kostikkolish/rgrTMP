package models;

public class Test {

    private final int id;
    private final String name;
    private final String description;
    private final TestQuestion[] questions;

    public Test(int id, String name, String description, TestQuestion[] questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }
}
