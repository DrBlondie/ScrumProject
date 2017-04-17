package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import model.ScoreBoard;

class ScoreBoardView extends JFrame {
    private ScoreBoard scoreBoard;
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 250;
    private static final int MAX_SIZE = 10;

    ScoreBoardView(ScoreBoard scores) {
        scoreBoard = scores;
        display();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void display() {
        setTitle("Top Ten Most Points");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        String[] columnNames = {"Score", "Name", "Date"};

        String[][] rowData = new String[MAX_SIZE][columnNames.length];
        for (int index = 0; index < MAX_SIZE; index++) {
            rowData[index] = scoreBoard.getPlayerScore(index);
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
}

