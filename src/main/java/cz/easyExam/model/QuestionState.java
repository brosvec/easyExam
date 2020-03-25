package cz.easyExam.model;

public enum QuestionState {
    SKIPPED("STATE_SKIPPED"), KNEW("STATE_KNEW"), NOTKNOW("STATE_DIDNT_KNOW");

    private final String name;

    QuestionState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
