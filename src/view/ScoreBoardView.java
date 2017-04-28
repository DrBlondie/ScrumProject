package view;

import java.awt.BorderLayout;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import test.ScoreBoard;

class ScoreBoardView extends JFrame implements Observer{
    private ScoreBoard scoreBoard;
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 250;
    private static final int MAX_SIZE = 10;

    ScoreBoardView(ScoreBoard scores, boolean isTimedScore) {
        scoreBoard = scores;
        display(isTimedScore);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void display(boolean isTimedScore) {

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        String[] columnNames = {"", "Name", "Date"};

        if(isTimedScore) {
            setTitle("Top Ten Least Time");
            columnNames[0] = "Time Taken";
        }
        else{
            setTitle("Top Ten Most Points");
            columnNames[0] = "Score";
        }

        String[][] rowData = new String[MAX_SIZE][columnNames.length];

        for (int index = 0; index < MAX_SIZE; index++) {
            rowData[index] = scoreBoard.getPlayerHighScore(index,isTimedScore);
        }

        JTable scoreTable = new JTable(rowData, columnNames);
        scoreTable.setModel(new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void update(java.util.Observable o, Object arg) {

    }
}

