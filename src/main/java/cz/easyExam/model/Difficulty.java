package cz.easyExam.model;

public enum Difficulty {
    HARD("DIFFICULTY_HARD"), MEDIUM("DIFFICULTY_MEDIUM"), EASY("DIFFICULTY_EASY");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
/*

    public Difficulty stringToDifficulty(String string){
        switch (string) {
            case "easy":
                return Difficulty.EASY;
            case "medium":
                return Difficulty.MEDIUM;
            case "hard":
                return Difficulty.HARD;
            default:
                return Difficulty.MEDIUM;
        }
    }
 */
}
