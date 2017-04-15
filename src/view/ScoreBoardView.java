package view;

import java.awt.BorderLayout;
import model.ScoreBoard;
import javax.swing.*;

class ScoreBoardView extends JFrame
{
    private ScoreBoard scoreBoard;

    ScoreBoardView()
    {
        scoreBoard = new ScoreBoard();
        display();

        final int WINDOW_WIDTH = 350;
        final int WINDOW_HEIGHT = 250;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void display()
    {
        setTitle("Top Ten Most Points");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        final int MAX_SIZE = 10;
        final String[] COLUMN_NAMES = {"Score", "Name", "Date"};

        String[][] rowData = new String[MAX_SIZE][COLUMN_NAMES.length];
        for(int index = 0; index < MAX_SIZE; index++)
        {
            rowData[index] = scoreBoard.getPlayerScore(index);
        }

        JTable scoreTable = new JTable(rowData, COLUMN_NAMES);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane, BorderLayout.CENTER);
    }
}

