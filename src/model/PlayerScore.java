package model;

import java.time.LocalDate;

class PlayerScore {
    private int score;
    private String name;
    private LocalDate date;

    PlayerScore(int newScore, String newName, LocalDate newDate) {
        score = newScore;
        name = newName;
        date = newDate;
    }

    int getScore() {
        return score;
    }

    String getName() {
        return name;
    }

    LocalDate getDate() {
        return date;
    }
}
