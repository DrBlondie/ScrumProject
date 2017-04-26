package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import model.Game;
import model.ScoreBoard;
import model.Tile;
import model.TileQueue;
import model.TimedGame;
import model.UntimedGame;


public class BoardView extends JFrame implements Observer {
    private JLabel gameLabel = new JLabel("");
    private JLabel scoreTime = new JLabel("");
    private JLabel removeSimilarTile = new JLabel("Remove Similar Tile: 1");
    private JPanel playField = new JPanel();
    private JPanel queueBox = new JPanel();
    private JTextField[][] gameBoard = new JTextField[9][9];
    private JTextField[] queue = new JTextField[5];
    private Color defaultColor = new Color(230, 230, 230);
    private Game currentBoard = null;
    private JButton rerollButton = new JButton("Reroll: 1");
    private TileQueue currentQueue;
    private ScoreBoard scores;
    private boolean gameOver = false;

    public BoardView() {
        setBackground(defaultColor);
        setTitle("Sum Fun");
        setSize(600, 600);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buildPlayField();
        buildQueueBox();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        scores = new ScoreBoard();
        JMenuBar gameMenu = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        JMenuItem timed = new JMenuItem("New Timed Game");
        timed.addActionListener(e -> newGame(true));
        JMenuItem untimed = new JMenuItem("New Untimed Game");
        untimed.addActionListener(e -> newGame(false));
        JMenuItem scoreBoardMenu = new JMenuItem("Top 10 Most Points");
        scoreBoardMenu.addActionListener(e -> new ScoreBoardView(scores));

        game.add(untimed);
        game.add(timed);
        game.add(scoreBoardMenu);
        game.add(exit);
        gameMenu.add(game);
        setJMenuBar(gameMenu);
        JPanel header = new JPanel();
        header.setBackground(defaultColor);
        header.setLayout(new GridLayout(2, 4));
        scoreTime.setFont(new Font("SansSerif", Font.BOLD, 16));
        gameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        //Empty JLabels for formatting using GridLayout
        header.add(removeSimilarTile);
        header.add(new JLabel());
        header.add(new JLabel());
        header.add(new JLabel());
        header.add(new JLabel());
        header.add(scoreTime);
        header.add(gameLabel);
        header.add(new JLabel());
        add(header, BorderLayout.NORTH);
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 33);
        boardPanel.add(playField, c);
        c.gridx = 1;
        boardPanel.add(queueBox);
        boardPanel.setBackground(defaultColor);
        add(boardPanel, BorderLayout.CENTER);
        setResizable(false);
    }

    public void newGame(Boolean isTimed) {
        gameOver = false;
        rerollButton.setText("Reroll: 1");
        removeSimilarTile.setText("Remove Similar Tile: 1");

        if (isTimed) {
            currentBoard = TimedGame.getInstance();
        } else {
            currentBoard = UntimedGame.getInstance();
        }
        currentBoard.newGame();
        currentQueue = currentBoard.getQueue();
        currentQueue.setRerollLeft(1);
        currentBoard.setRemoveTileLeft(1);
        currentBoard.addObserver(this);
        currentQueue.addObserver(this);
        currentBoard.updateGame();
        currentQueue.updateGame();
    }

    private void buildPlayField() {
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
                gameBoard[x][y] = getNewTextField();
                gameBoard[x][y].setBackground(defaultColor);

                gameBoard[x][y].addMouseListener(new MouseClick(boardPosition));
                c.gridx = x * 90;
                c.gridy = y;
                playField.add(gameBoard[x][y], c);
            }
        }
    }


    private void buildQueueBox() {

        queueBox.setLayout(new GridBagLayout());
        queueBox.setBackground(defaultColor);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 50;
        c.insets = new Insets(0, 0, 0, 0);
        queueBox.add(rerollButton, c);
        rerollButton.addMouseListener(new ButtonClick());
        for (int i = 0; i < queue.length; i++) {
            queue[i] = getNewTextField();
            queue[i].setBackground(defaultColor);
            c.gridy = i + 1;
            queueBox.add(queue[i], c);
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg == null) {
            return;
        }
        switch (arg.getClass().getSimpleName()) {
            case "String":
                gameLabel.setText(currentBoard.getGameValue());
                break;
            case "ArrayList":
                ArrayList numberQueue = (ArrayList) arg;
                int i;

                for (i = 0; i < numberQueue.size() && i < queue.length; i++) {
                    queue[i].setText(numberQueue.get(i).toString());

                }
                for (; i < queue.length; i++) {
                    queue[i].setText("");
                }
                break;
            case "Tile[][]":
                Tile[][] gameBoard = (Tile[][]) arg;
                for (int y = 0; y < gameBoard.length; y++) {
                    for (int x = 0; x < gameBoard[y].length; x++) {
                        if (gameBoard[x][y].isOccupied()) {
                            this.gameBoard[x][y].setText(gameBoard[x][y].getNumber() + "");
                        } else {
                            this.gameBoard[x][y].setText("");
                        }
                    }
                }
                gameLabel.setText(currentBoard.getGameValue());
                scoreTime.setText("Score: " + currentBoard.getScore());
                break;
            default:
                break;
        }

    }


    /***
     * Creates a JTextField that is used to hold information for each Tile
     * @return The created JTextField
     */
    private JTextField getNewTextField() {

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(50, 50));
        textField.setBorder(gameBorder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return textField;
    }

    private class ButtonClick extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            currentQueue.rerollQueue();
            rerollButton.setText("Reroll: 0");

        }
    }

    private class MouseClick extends MouseAdapter {
        private Point boardPosition;

        MouseClick(Point position) {
            boardPosition = position;
        }

        public void mouseClicked(MouseEvent e) {
            if(gameOver){
                return;
            }
            if (currentBoard.gameWin()) {
                gameOver = true;
                if (scores.isHighScore(currentBoard.getScore())) {
                    String name = JOptionPane.showInputDialog("You Win! New High Score! Please enter your name:");
                    while(name == null) {
                        name = JOptionPane.showInputDialog("Please enter a valid name:");
                    }
                    scores.updateScores(name, currentBoard.getScore());
                } else {
                    JOptionPane.showMessageDialog(null, "You Win!");
                }
                return;
            }
            if(currentBoard.gameOver()){
                gameOver = true;
                JOptionPane.showMessageDialog(null, "You Lose. Please try again.");

                return;
            }
            if (currentBoard.checkMove(boardPosition.x, boardPosition.y)) {
                gameBoard[boardPosition.x][boardPosition.y].setBackground(defaultColor);

            }else{
                for(int x =0;x<9;x++){
                    for(int y=0;y<9;y++){
                        if(gameBoard[x][y].getText().equals(gameBoard[boardPosition.x][boardPosition.y].getText())){
                            gameBoard[x][y].setBackground(defaultColor);
                        }
                    }
                }
                removeSimilarTile.setText("Remove Similar Tile: 0");
            }




        }

        public void mouseExited(MouseEvent e) {
            for(int x =0;x<9;x++){
                for(int y=0;y<9;y++){
                    if(gameBoard[x][y].getText().equals(gameBoard[boardPosition.x][boardPosition.y].getText())){
                        gameBoard[x][y].setBackground(defaultColor);
                    }
                }
            }

        }

        public void mouseEntered(MouseEvent e) {
            if (!currentBoard.isOccupied(boardPosition.x, boardPosition.y) && !gameOver) {
                Color randomColor = new Color((int) ((Math.random() * 128) + 127), (int) ((Math.random() * 128) + 127), (int) ((Math.random() * 128) + 127));
                queue[0].setBackground(randomColor);
                gameBoard[boardPosition.x][boardPosition.y].setBackground(randomColor);
            }
           if(currentBoard.isOccupied(boardPosition.x, boardPosition.y) && !gameOver) {
               Color randomColor = new Color((int) ((Math.random() * 128) + 127), (int) ((Math.random() * 128) + 127), (int) ((Math.random() * 128) + 127));
               if (currentBoard.getRemoveTileLeft() == 1) {
                   for (int x = 0; x < 9; x++) {
                       for (int y = 0; y < 9; y++) {
                           if (gameBoard[x][y].getText().equals(gameBoard[boardPosition.x][boardPosition.y].getText())) {
                               gameBoard[x][y].setBackground(randomColor);
                           }
                       }
                   }

               }
           }
        }

    }
}