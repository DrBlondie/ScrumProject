package model;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ScoreBoard
{
    private ArrayList<Integer> scores;
    private ArrayList<String> names;
    private ArrayList<LocalDate> dates;
    private DateTimeFormatter dtf;

    private final int SCORE_INDEX = 0;
    private final int NAME_INDEX = 1;
    private final int DATE_INDEX = 2;
    private final String SCORE_FILE = "scores.txt";

    public ScoreBoard()
    {
        scores = new ArrayList<>();
        names = new ArrayList<>();
        dates = new ArrayList<>();
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        populateScoreBoard();
        sortScores();
        ensureCapacity();
        updateScoreFile();
    }

    private void populateScoreBoard()
    {
        try
        {
            File scoreFileToScan = new File(SCORE_FILE);
            Scanner scanScoreFile = new Scanner(scoreFileToScan);

            while(scanScoreFile.hasNextLine())
            {
                String currentLine = scanScoreFile.nextLine();
                addScore(currentLine);
            }
            scanScoreFile.close();
        }
        catch(Exception ex)
        {
            System.out.println("File not found!");
        }
    }

    private void addScore(String scoreInformation)
    {
        String[] scoreInfo = scoreInformation.split(",");
        try
        {
            scores.add(Integer.parseInt(scoreInfo[SCORE_INDEX]));
            names.add(scoreInfo[NAME_INDEX]);
            dates.add(LocalDate.parse(scoreInfo[DATE_INDEX]));
        }
        catch(Exception ex)
        {
            System.out.println("Invalid score information detected");
        }
    }

    private void sortScores()
    {
        ArrayList<Integer> sortedScores = new ArrayList<>();
        ArrayList<Integer> tempScores = new ArrayList<>();
        ArrayList<String> tempNames = new ArrayList<>();
        ArrayList<LocalDate> tempDates = new ArrayList<>();

        for(int score : scores) sortedScores.add(score);
        Collections.sort(sortedScores);
        Collections.reverse(sortedScores);

        for(int sortedScore : sortedScores)
        {
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

    public String[] getPlayerScore(int position)
    {
        String[] scoreArray = new String[3];
        if(position >= scores.size())
        {
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

    private void ensureCapacity()
    {
        final int MAX_SIZE = 10;
        if(scores.size() > MAX_SIZE || names.size() > MAX_SIZE || dates.size() > MAX_SIZE)
        {
            scores.subList(MAX_SIZE, scores.size()).clear();
            names.subList(MAX_SIZE, names.size()).clear();
            dates.subList(MAX_SIZE, dates.size()).clear();
        }
    }

    private void updateScoreFile()
    {
        try
        {
            PrintWriter pW = new PrintWriter(SCORE_FILE);
            for(int index = 0; index < scores.size(); index++)
            {
                pW.println(scores.get(index).toString() + "," + names.get(index) + "," + dtf.format(dates.get(index)));
            }

            pW.close();
        }
        catch (Exception ex)
        {
            System.out.println("File not found!");
        }
    }
}
