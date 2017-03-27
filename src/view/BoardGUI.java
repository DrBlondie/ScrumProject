package view;

import main.Main;
import main.Tile;
import model.Board;
import model.TileQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;


public class BoardGUI extends JFrame implements Observer {
    protected TimerThread timerThread;
    private JPanel playField = new JPanel();
    private JPanel queueBox = new JPanel();
    private JTextField[][] gameBoard = new JTextField[9][9];
    private JTextField[] queue = new JTextField[5];
    protected static JLabel movesLabel = new JLabel("");

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

        JPanel header = new JPanel();
        header.setLayout(new GridLayout(1, 3));

        header.add(movesLabel);
        JLabel label = new JLabel("Sum Fun");

        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel timer = new JLabel();
        timer.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(label);
        header.add(timer);
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
        timerThread = new TimerThread(timer);
        timerThread.start();


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
                gameBoard[x][y].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Main.board.performMove(boardPosition.x, boardPosition.y);
                        //GUI.movesLabel.setText("Number of moves left "+ (Main.maxMoves- NUMBER_OF_MOVES));*
                    }
                });

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
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass().getSimpleName().equals("TileQueue")) {
            Tile[] _queue = ((TileQueue) o).getQueue();
            for (int i = 0; i < queue.length; i++) {
                queue[i].setText(_queue[i].getNumber() + "");
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
            movesLabel.setText("Number of moves left: " + (Main.maxMoves - Board.NUMBER_OF_MOVES));
        }
    }


    public class TimerThread extends Thread {

        protected boolean isRunning;

        protected JLabel timeLabel;
        private Date endTime;

        protected SimpleDateFormat dateFormat =
                new SimpleDateFormat("M/d/YY");
        protected SimpleDateFormat timeFormat =
                new SimpleDateFormat("h:mm:ss");

        public TimerThread(JLabel timeLabel) {
            this.timeLabel = timeLabel;
            endTime = Calendar.getInstance().getTime();
            endTime.setTime(endTime.getTime() + 180000);
            this.isRunning = true;
        }

        @Override

        public void run() {
            while (isRunning) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Calendar currentCalendar = Calendar.getInstance();
                        Date currentTime = currentCalendar.getTime();
                        long time = endTime.getTime() - currentTime.getTime();
                        int timeLeft = (int) time / 1000;
                        String t;
                        if (timeLeft >= 60) {
                            t = (timeLeft / 60) + ":" + String.format("%02d", (timeLeft % 60));
                        } else if (timeLeft < 60 && timeLeft > 0) {
                            t = String.format("%02d", timeLeft);
                        } else {
                            t = "00";
                        }
                        timeLabel.setText(t + "");
                    }
                });

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            }
        }

        public void setRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

    }
}