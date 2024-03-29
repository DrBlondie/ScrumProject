package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
    private JLabel removeSimilarTile = new JLabel("<html><style ='text-align: center'>Remove Similar<br>Tiles: 1</html>");
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
    private Timer hintTimer = new Timer();
    private Color hintColor = new Color(150, 0, 150);
    private TimerTask currentHintTask;
    private JButton hintButton = new JButton("Hints: 3");

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
        exit.addActionListener(new ItemClickListener("exit"));
        JMenuItem timed = new JMenuItem("New Timed Game");
        timed.addActionListener(new ItemClickListener("timed"));
        JMenuItem untimed = new JMenuItem("New Untimed Game");
        untimed.addActionListener(new ItemClickListener("untimed"));
        game.add(untimed);
        game.add(timed);
        game.add(exit);
        gameMenu.add(game);
        JMenu highscores = new JMenu("Highscores");

        JMenuItem scoreBoardMenu = new JMenuItem("Top 10 Most Points");
        scoreBoardMenu.addActionListener(new ItemClickListener("topPoints"));
        JMenuItem timeBoardMenu = new JMenuItem("Top 10 Fastest Times");
        timeBoardMenu.addActionListener(new ItemClickListener("topTime"));

        highscores.add(scoreBoardMenu);
        highscores.add(timeBoardMenu);
        gameMenu.add(highscores);
        setJMenuBar(gameMenu);
        JPanel header = new JPanel();
        header.setBackground(defaultColor);
        header.setLayout(new GridLayout(2, 4));
        scoreTime.setFont(new Font("SansSerif", Font.BOLD, 16));
        gameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        //Empty JLabels for formatting using GridLayout
        header.add(new JLabel());
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
        makeEngaging();
    }

    public void newGame(Boolean isTimed) {
        gameOver = false;
        rerollButton.setText("Reroll: 1");
        removeSimilarTile.setText("<html><body style ='text-align: center'>Remove Similar<br>Tiles: 1</html>");
        if (currentHintTask != null) {
            currentHintTask.cancel();
            currentHintTask = null;
        }

        if (isTimed) {
            currentBoard = TimedGame.getInstance();
        } else {
            currentBoard = UntimedGame.getInstance();
        }
        currentBoard.newGame();
        currentQueue = currentBoard.getQueue();
        currentBoard.addObserver(this);
        currentQueue.addObserver(this);
        currentBoard.updateGame();
        currentQueue.updateGame();

        hintButton.setText("Hints: " + currentBoard.getHintsRemaining());
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
                gameBoard[x][y].addMouseListener(new BoardClick(boardPosition));
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
        queueBox.add(hintButton, c);
        hintButton.addActionListener(new ItemClickListener("hint"));
        c.gridy++;
        c.gridheight = 2;
        queueBox.add(removeSimilarTile, c);
        c.gridheight = 1;
        c.gridy+= 2;
        queueBox.add(rerollButton, c);
        c.gridy++;
        rerollButton.addActionListener(new ItemClickListener("reroll"));
        for (int i = 0; i < queue.length; i++) {
            queue[i] = getNewTextField();
            queue[i].setBackground(defaultColor);
            c.gridy++;
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

    private void makeEngaging() {
        setupSound("/resources/marioMusic.wav");
        setupCursor("/resources/mushroomIcon.png");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image image = toolkit.getImage(getClass().getResource("/resources/marioMainIcon.png"));
            setIconImage(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Icon file not found!");
        }

    }

    private void setupCursor(String fileName) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image image = toolkit.getImage(getClass().getResource(fileName));
            Point hotspot = new Point(3, 3);
            Cursor cursor = toolkit.createCustomCursor(image, hotspot, "icon");
            setCursor(cursor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cursor file not found!");
        }
    }

    private void setupSound(String fileName) {
        URL temp = BoardView.class.getResource(fileName);
        try {
            Clip myClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(temp);
            myClip.open(ais);
            if(fileName.equals("/resources/marioMusic.wav")){
                myClip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                myClip.start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Audio file not found!");
        }
    }

    private Color getRandomMarioColor() {
        int randomNumber = (int) (Math.random() * 4);

        switch (randomNumber) {
            case 0:
                return new Color(242, 208, 49);
            case 1:
                return new Color(7, 201, 0);
            case 2:
                return new Color(1, 223, 225);
            default:
                return new Color(247, 61, 67);
        }
    }

    private void changeTheme() {
        int randomNumber = (int) (Math.random() * 4);
        switch (randomNumber) {
            case 0:
                setupCursor("/resources/mushroomIcon.png");
                setupSound("/resources/mushroom.wav");
                break;

            case 1:
                setupCursor("/resources/coinIcon.gif");
                setupSound("/resources/coin.wav");
                break;

            case 2:
                setupCursor("/resources/yoshiIcon.png");
                setupSound("/resources/yoshi.wav");
                break;

            case 3:
                setupCursor("/resources/pipeIcon.png");
                setupSound("/resources/pipe.wav");
                break;

            default:
                break;
        }
    }


    private class ItemClickListener implements ActionListener {
        private String clicked;

        ItemClickListener(String clicked) {
            this.clicked = clicked;
        }

        public void actionPerformed(ActionEvent e) {
            switch (clicked) {
                case "exit":
                    System.exit(1);
                    break;
                case "reroll":
                    currentQueue.rerollQueue();
                    rerollButton.setText("Reroll: 0");
                    break;
                case "hint":
                    if (currentHintTask != null) {
                        JOptionPane.showMessageDialog(null, "Hint already displayed.");
                        break;
                    }
                    Point hintPoint = currentBoard.getHint();
                    hintButton.setText("Hints: " + currentBoard.getHintsRemaining());
                    if(hintPoint == null){
                        JOptionPane.showMessageDialog(null, "No hints remaining.");
                        break;
                    }
                    if (hintPoint.x == -1 && hintPoint.y == -1) {
                        JOptionPane.showMessageDialog(null, "There is no place for you to place the next value.");
                        break;
                    }
                    currentHintTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (gameBoard[hintPoint.x][hintPoint.y].getBackground() == hintColor) {
                                gameBoard[hintPoint.x][hintPoint.y].setBackground(defaultColor);
                            } else {
                                gameBoard[hintPoint.x][hintPoint.y].setBackground(hintColor);
                            }
                        }
                    };

                    hintTimer.scheduleAtFixedRate(currentHintTask, Calendar.getInstance().getTime(), 250L);
                    break;
                case "timed":
                    newGame(true);
                    break;
                case "untimed":
                    newGame(false);
                    break;
                case "topPoints":
                    new ScoreBoardView(scores, false);
                    break;
                case "topTime":
                    new ScoreBoardView(scores, true);
                    break;
                default:
                    break;
            }
        }

    }

    private class BoardClick extends MouseAdapter {
        private Point boardPosition;

        BoardClick(Point position) {
            boardPosition = position;
        }

        public void mouseClicked(MouseEvent e) {
            if (gameOver) {
                return;
            }
            if (currentHintTask != null) {
                currentHintTask.cancel();
                currentHintTask = null;
            }
            if (currentBoard.isOccupied(boardPosition.x, boardPosition.y) && currentBoard.removeCommonTiles(boardPosition.x, boardPosition.y)) {
                setCommonColor(defaultColor);
                removeSimilarTile.setText("<html><body style ='text-align: center'>Remove Similar<br>Tiles: 0</html>");
            } else if (currentBoard.checkMove(boardPosition.x, boardPosition.y)) {
                changeTheme();
                gameBoard[boardPosition.x][boardPosition.y].setBackground(defaultColor);
            }
            if (currentBoard.gameWin()) {
                gameOver = true;
                boolean highScore = false;
                if (scores.betterThanTop(currentBoard.getScore(), false)) {
                    highScore = true;
                    updateHighSores(false, "most points", currentBoard.getScore());
                }
                if(currentBoard.getClass().getSimpleName().equals("TimedGame")){
                    int timeUsed = ((TimedGame)currentBoard).getTimeUsed();
                    if (scores.betterThanTop(timeUsed, true)) {
                        updateHighSores(true, "fastest times", timeUsed);
                        return;
                    }
                }
                if(!highScore){
                    JOptionPane.showMessageDialog(null, "You Win!");
                }

            } else if (currentBoard.gameOver()) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "You Lose. Please try again.");
            }
        }

        public void mouseExited(MouseEvent e) {
            setCommonColor(defaultColor);
        }

        public void mouseEntered(MouseEvent e) {
            if (gameOver) {
                return;
            }
            Color randomColor = getRandomMarioColor();
            if (!currentBoard.isOccupied(boardPosition.x, boardPosition.y)) {
                queue[0].setBackground(randomColor);
                gameBoard[boardPosition.x][boardPosition.y].setBackground(randomColor);
            } else if (currentBoard.getRemoveTileLeft() == 1) {
                setCommonColor(randomColor);
            }
        }

        private void setCommonColor(Color changeColor) {
            String text = gameBoard[boardPosition.x][boardPosition.y].getText();
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (gameBoard[x][y].getText().equals(text)) {
                        gameBoard[x][y].setBackground(changeColor);
                    }
                }
            }
        }

        private String getHighScoreWinner(String type) {
            String name = "";
            int dialogResult = JOptionPane.showConfirmDialog(null, "You Win! Would you like to add your name to the high score list of " + type + "?", "You WIN!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                name = JOptionPane.showInputDialog("You Win! New High Score! Please enter your name:");
            }
            return name;
        }

        private void updateHighSores(boolean isTimed, String type, int value) {
            String winner = getHighScoreWinner(type);
            if (!winner.equals("")) {
                scores.updateScores(winner, value, isTimed);
            }
        }
    }
}