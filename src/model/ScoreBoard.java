package model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreBoard implements Observable {
    private ArrayList<PlayerScore> playerHighScores;
    private ArrayList<PlayerScore> playerTimedScores;
    private DateTimeFormatter dtf;


    private static final String SCORE_FILE = "scores.txt";

    private static final int MAX_SIZE = 10;

    public ScoreBoard() {
        playerHighScores = new ArrayList<>();
        playerTimedScores = new ArrayList<>();
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        populateScoreBoard();
        sortScores();
        ensureCapacity();
        updateScoreFile();
    }

    public boolean betterThanTop(int score, boolean isTimedScore) {
        if (isTimedScore) {
            for (PlayerScore highScores : playerHighScores) {
                if (score > highScores.getScore()) {
                    return true;
                }
            }
            return false;
        }
        for (PlayerScore highScores : playerTimedScores) {
            if (score < highScores.getScore()) {
                return true;
            }
        }
        return false;
    }

    public void updateScores(String name, int score, boolean isTimedScore) {
        addScore(score + "," + name + "," + LocalDate.now(), isTimedScore);
        sortScores();
        ensureCapacity();
        updateScoreFile();
    }

    private void populateScoreBoard() {
        try {
            //playerHighScores.add(new PlayerScore(0,"",LocalDate.parse("0001-01-01")));
            File scoreFileToScan = new File(SCORE_FILE);
            Scanner scanScoreFile = new Scanner(scoreFileToScan);
            boolean isTimedScore = false;

            while (scanScoreFile.hasNextLine()) {
                String currentLine = scanScoreFile.nextLine();
                if (currentLine.equals("High Score")) {
                    isTimedScore = false;
                    continue;
                } else if (currentLine.equals("Timed Score")) {
                    isTimedScore = true;
                    continue;
                }
                addScore(currentLine, isTimedScore);
            }
            scanScoreFile.close();
        } catch (Exception ex) {
            System.out.println("File not found!");
        }
    }

    private void addScore(String scoreInformation, boolean isTimedScore) {
        String[] scoreInfo = scoreInformation.split(",");
        try {
            final int SCORE_INDEX = 0;
            final int NAME_INDEX = 1;
            final int DATE_INDEX = 2;

            int score = Integer.parseInt(scoreInfo[SCORE_INDEX]);
            String name = scoreInfo[NAME_INDEX];
            LocalDate date = LocalDate.parse(scoreInfo[DATE_INDEX]);

            PlayerScore playerScore = new PlayerScore(score, name, date);
            if (isTimedScore) {
                playerTimedScores.add(playerScore);
                return;
            }
            playerHighScores.add(playerScore);

        } catch (Exception ex) {
            System.out.println("Invalid score information detected");
        }
    }

    private void sortScores() {
        playerHighScores.sort(this::sortHighScores);
        playerTimedScores.sort(this::sortTimedScores);
    }

    private int sortHighScores(PlayerScore playerScore1, PlayerScore playerScore2) {
        if (playerScore2.getScore() == playerScore1.getScore()) {
            return sortDates(playerScore1, playerScore2);
        }
        return playerScore2.getScore() - playerScore1.getScore();
    }

    private int sortTimedScores(PlayerScore playerScore1, PlayerScore playerScore2) {
        if (playerScore2.getScore() == playerScore1.getScore()) {
            return sortDates(playerScore1, playerScore2);
        }
        return playerScore1.getScore() - playerScore2.getScore();
    }

    private int sortDates(PlayerScore playerScore1, PlayerScore playerScore2) {
        LocalDate playerDate1 = playerScore1.getDate();
        LocalDate playerDate2 = playerScore2.getDate();

        int compareDates = playerDate1.getYear() - playerDate2.getYear();
        if (compareDates == 0) {
            compareDates = playerDate1.getMonthValue() - playerDate2.getMonthValue();
            if (compareDates == 0) {
                compareDates = playerDate1.getDayOfMonth() - playerDate2.getDayOfMonth();
            }
        }
        return compareDates;
    }

    public String[] getPlayerHighScore(int position, boolean isTimedGame) {
        if ((!isTimedGame && position >= playerHighScores.size()) ||
                (isTimedGame && position >= playerTimedScores.size())) {
            return new String[]{"", "", ""};
        }

        PlayerScore currentPlayerScore = playerHighScores.get(position);
        if (isTimedGame) {
            currentPlayerScore = playerTimedScores.get(position);
        }

        int score = currentPlayerScore.getScore();
        String name = currentPlayerScore.getName();
        LocalDate date = currentPlayerScore.getDate();

        return new String[]{"" + score, name, dtf.format(date)};
    }

    private void ensureCapacity() {
        if (playerHighScores.size() > MAX_SIZE) {
            playerHighScores.subList(MAX_SIZE, playerHighScores.size()).clear();
        }
        if (playerTimedScores.size() > MAX_SIZE) {
            playerTimedScores.subList(MAX_SIZE, playerTimedScores.size()).clear();
        }
    }

    private void updateScoreFile() {
        try {
            PrintWriter writer = new PrintWriter(SCORE_FILE);

            writer.println("High Score");
            for (PlayerScore playerScore : playerHighScores) {
                writeToFile(playerScore, writer);
            }

            writer.println("Timed Score");
            for (PlayerScore playerScore : playerTimedScores) {
                writeToFile(playerScore,writer);
            }

            writer.close();
        } catch (Exception ex) {
            System.out.println("File not found!");
        }
    }

    private void writeToFile(PlayerScore playerScore, PrintWriter writer) {
        writer.println(playerScore.getScore() + "," + playerScore.getName() + "," + dtf.format(playerScore.getDate()));
    }

    public void removeListener(InvalidationListener listener) {}
    public void addListener(InvalidationListener listener) {}
}
