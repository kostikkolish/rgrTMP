package models;

public class TestQuestion {

    private final int id;
    private final int number;
    private final String text;
    private final String[] options;
    private final int answerNumber;

    public TestQuestion(int id, int number, String text, String[] options, int answerNumber) {
        this.id = id;
        this.number = number;
        this.text = text;
        this.options = options;
        this.answerNumber = answerNumber;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }
}
