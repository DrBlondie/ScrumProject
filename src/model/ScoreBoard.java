package model;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreBoard {
    private ArrayList<PlayerScore> playerScores;
    private DateTimeFormatter dtf;

    private static final int SCORE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final String SCORE_FILE = "scores.txt";

    private static final int MAX_SIZE = 10;

    public ScoreBoard() {
        playerScores = new ArrayList<>();
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        populateScoreBoard();
        sortScores();
        ensureCapacity();
        updateScoreFile();
    }

    public boolean isHighScore(int score) {
        for (PlayerScore highScores : playerScores) {
            if (score > highScores.getScore()) {
                return true;
            }
        }
        return false;
    }

    public void updateScores(String name, int score) {
        addScore(score + "," + name + "," + LocalDate.now());
        sortScores();
        ensureCapacity();
        updateScoreFile();
    }

    private void populateScoreBoard() {
        try {
            File scoreFileToScan = new File(SCORE_FILE);
            Scanner scanScoreFile = new Scanner(scoreFileToScan);

            while (scanScoreFile.hasNextLine()) {
                String currentLine = scanScoreFile.nextLine();
                addScore(currentLine);
            }
            scanScoreFile.close();
        } catch (Exception ex) {
            System.out.println("File not found!");
        }
    }

    private void addScore(String scoreInformation) {
        String[] scoreInfo = scoreInformation.split(",");
        try {
            int score = Integer.parseInt(scoreInfo[SCORE_INDEX]);
            String name = scoreInfo[NAME_INDEX];
            LocalDate date = LocalDate.parse(scoreInfo[DATE_INDEX]);

            PlayerScore playerScore = new PlayerScore(score, name, date);
            playerScores.add(playerScore);
        } catch (Exception ex) {
            System.out.println("Invalid score information detected");
        }
    }

    private void sortScores() {
        playerScores.sort((playerScore1, playerScore2) -> {
            if (playerScore2.getScore() == playerScore1.getScore()) {
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
            return playerScore2.getScore() - playerScore1.getScore();
        });
    }

    public String[] getPlayerScore(int position) {
        String[] scoreArray = new String[3];
        if (position >= playerScores.size()) {
            scoreArray[SCORE_INDEX] = "";
            scoreArray[NAME_INDEX] = "";
            scoreArray[DATE_INDEX] = "";
            return scoreArray;
        }
        PlayerScore currentPlayerScore = playerScores.get(position);
        int score = currentPlayerScore.getScore();
        String name = currentPlayerScore.getName();
        LocalDate date = currentPlayerScore.getDate();

        scoreArray[SCORE_INDEX] = "" + score;
        scoreArray[NAME_INDEX] = name;
        scoreArray[DATE_INDEX] = dtf.format(date);

        return scoreArray;
    }

    private void ensureCapacity() {
        if (playerScores.size() > MAX_SIZE) {
            playerScores.subList(MAX_SIZE, playerScores.size()).clear();
        }
    }

    private void updateScoreFile() {
        try {
            PrintWriter writer = new PrintWriter(SCORE_FILE);
            for (PlayerScore playerScore : playerScores) {
                writer.println(playerScore.getScore() + "," + playerScore.getName() + "," + dtf.format(playerScore.getDate()));
            }

            writer.close();
        } catch (Exception ex) {
            System.out.println("File not found!");
        }
    }
}
