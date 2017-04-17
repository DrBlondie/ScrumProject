package model;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ScoreBoard {
    private ArrayList<Integer> scores;
    private ArrayList<String> names;
    private ArrayList<LocalDate> dates;
    private DateTimeFormatter dtf;

    private static final int SCORE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final String SCORE_FILE = "scores.txt";

    private static final int MAX_SIZE = 10;

    public ScoreBoard() {
        scores = new ArrayList<>();
        names = new ArrayList<>();
        dates = new ArrayList<>();
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        populateScoreBoard();
        sortDates();
        sortScores();
        ensureCapacity();
        updateScoreFile();
    }

    public boolean isHighScore(int score){
        for (int highScores: scores) {
            if(score > highScores){
                return true;
            }
        }
        return false;
    }

    public void updateScores(String name, int score){
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
            scores.add(Integer.parseInt(scoreInfo[SCORE_INDEX]));
            names.add(scoreInfo[NAME_INDEX]);
            dates.add(LocalDate.parse(scoreInfo[DATE_INDEX]));
        } catch (Exception ex) {
            System.out.println("Invalid score information detected");
        }
    }

    private void sortDates(){
        ArrayList<LocalDate> sortedDates = new ArrayList<>();
        ArrayList<Integer> tempScores = new ArrayList<>();
        ArrayList<String> tempNames = new ArrayList<>();
        ArrayList<LocalDate> tempDates = new ArrayList<>();
        sortedDates.addAll(dates);
        Collections.sort(sortedDates);
        Collections.reverse(sortedDates);

        for(LocalDate sortedDate : sortedDates){
            int prevDateLocation = dates.indexOf(sortedDate);
            int sortedDateLocation = sortedDates.indexOf(sortedDate);

            int score = scores.get(prevDateLocation);
            String name = names.get(prevDateLocation);

            scores.remove(prevDateLocation);
            tempScores.add(sortedDateLocation, score);
            names.remove(prevDateLocation);
            tempNames.add(sortedDateLocation, name);
            dates.remove(prevDateLocation);
            tempDates.add(sortedDateLocation, sortedDate);
        }
        scores = tempScores;
        names = tempNames;
        dates = tempDates;
    }

    private void sortScores() {
        ArrayList<Integer> sortedScores = new ArrayList<>();
        ArrayList<Integer> tempScores = new ArrayList<>();
        ArrayList<String> tempNames = new ArrayList<>();
        ArrayList<LocalDate> tempDates = new ArrayList<>();
        sortedScores.addAll(scores);
        Collections.sort(sortedScores);
        Collections.reverse(sortedScores);

        for (int sortedScore : sortedScores) {
            int prevScorePosition = scores.indexOf(sortedScore);
            int sortedScorePosition = sortedScores.indexOf(sortedScore);

            String name = names.get(prevScorePosition);
            LocalDate date = dates.get(prevScorePosition);

            scores.remove(prevScorePosition);
            tempScores.add(sortedScorePosition, sortedScore);
            names.remove(prevScorePosition);
            tempNames.add(sortedScorePosition, name);
            dates.remove(prevScorePosition);
            tempDates.add(sortedScorePosition, date);
        }
        scores = tempScores;
        names = tempNames;
        dates = tempDates;
    }

    public String[] getPlayerScore(int position) {
        String[] scoreArray = new String[3];
        if (position >= scores.size()) {
            scoreArray[SCORE_INDEX] = "";
            scoreArray[NAME_INDEX] = "";
            scoreArray[DATE_INDEX] = "";
            return scoreArray;
        }
        scoreArray[SCORE_INDEX] = scores.get(position).toString();
        scoreArray[NAME_INDEX] = names.get(position);
        scoreArray[DATE_INDEX] = dtf.format(dates.get(position));

        return scoreArray;
    }

    private void ensureCapacity() {
        if (scores.size() > MAX_SIZE || names.size() > MAX_SIZE || dates.size() > MAX_SIZE) {
            scores.subList(MAX_SIZE, scores.size()).clear();
            names.subList(MAX_SIZE, names.size()).clear();
            dates.subList(MAX_SIZE, dates.size()).clear();
        }
    }

    private void updateScoreFile() {
        try {
            PrintWriter writer = new PrintWriter(SCORE_FILE);
            for (int index = 0; index < scores.size(); index++) {
                writer.println(scores.get(index).toString() + "," + names.get(index) + "," + dtf.format(dates.get(index)));
            }

            writer.close();
        } catch (Exception ex) {
            System.out.println("File not found!");
        }
    }
}
