package view;

import main.Main;
import model.Tile;
import model.Board;
import model.TileQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class BoardGUI extends JFrame implements Observer {
    private JLabel movesLabel = new JLabel("");
    private JLabel scoreTime = new JLabel("");
    private JPanel playField = new JPanel();
    private JPanel queueBox = new JPanel();
    private JTextField[][] gameBoard = new JTextField[9][9];
    private JTextField[] queue = new JTextField[5];
    private Font gameFont = new Font("SansSerif", Font.BOLD, 20);

    public BoardGUI() {

        setTitle("Sum Fun");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildPlayField();
        buildQueueBox();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;

        JMenuBar gameMenu = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenu scores = new JMenu("Scores");
        JMenuItem startGame = new JMenuItem("Start New Game");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenuItem viewScores = new JMenuItem("Leader Scoreboard");
        game.add(startGame);
        game.add(exit);
        scores.add(viewScores);
        gameMenu.add(game);
        gameMenu.add(scores);
        setJMenuBar(gameMenu);

        JPanel header = new JPanel();
        header.setLayout(new GridLayout(1, 3));

        header.add(movesLabel);
        JLabel label = new JLabel("Sum Fun");

        label.setFont(gameFont);
        scoreTime.setFont(gameFont);
        movesLabel.setFont(gameFont);
        header.add(label);
        header.add(scoreTime);
        add(header, BorderLayout.PAGE_START);
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 50);
        boardPanel.add(playField, c);
        c.gridx = 1;
        boardPanel.add(queueBox, c);
        add(boardPanel, BorderLayout.CENTER);
    }

    public void addObserver(Observable model) {
        model.addObserver(this);
    }

    public void buildPlayField() {
        playField.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 50;
        c.insets = new Insets(0, 0, 0, 0);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                final Point boardPosition = new Point(x, y);
                gameBoard[x][y] = Main.getNewTextField();
                gameBoard[x][y].addMouseListener(new MouseClick(boardPosition));

                c.gridx = x * 90;
                c.gridy = y;
                playField.add(gameBoard[x][y], c);
            }
        }
    }


    public void buildQueueBox() {

        queueBox.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 50;
        c.insets = new Insets(0, 0, 0, 0);
        for (int i = 0; i < queue.length; i++) {
            queue[i] = Main.getNewTextField();
            c.gridy = i;
            queueBox.add(queue[i], c);
        }
        queue[0].setBackground(Color.YELLOW);
    }


    @Override
    public void update(Observable o, Object arg) {

        if (o.getClass().getSimpleName().equals("TileQueue")) {
            ArrayList<Integer> _queue = ((TileQueue) o).getQueue();
            int i;

                //added redundant check for proper size
                for (i = 0; i < _queue.size()&&i<queue.length; i++) {
                    queue[i].setText(_queue.get(i) + "");

                }
                for(;i<queue.length;i++){
                    queue[i].setText("");
                }

        } else if (o.getClass().getSimpleName().equals("Board")) {
            Tile[][] _gameBoard = ((Board) o).getBoard();
            for (int y = 0; y < _gameBoard.length; y++) {
                for (int x = 0; x < _gameBoard[y].length; x++) {
                    if (_gameBoard[x][y].isOccupied()) {
                        gameBoard[x][y].setText(_gameBoard[x][y].getNumber() + "");
                    } else {
                        gameBoard[x][y].setText("");
                    }
                }


            }
            movesLabel.setText("Number of moves left: " + (Main.MAX_MOVES - Board.getMoves()));
            scoreTime.setText("Score: " + ((Board) o).getScore());
        }

    }

}

class MouseClick extends MouseAdapter{
    private Point boardPosition;
    public MouseClick(Point position){
        boardPosition = position;
    }
    public void mouseClicked(MouseEvent e) {
        Main.gameBoard.performMove(boardPosition.x, boardPosition.y);
        //GUI.movesLabel.setText("Number of moves left "+ (Main.maxMoves- NUMBER_OF_MOVES));*
    }
}