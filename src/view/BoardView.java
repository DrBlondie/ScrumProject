package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import main.Main;
import model.Board;
import model.Tile;
import model.TileQueue;


public class BoardView extends JFrame implements Observer {
    private JLabel movesLabel = new JLabel("");
    private JLabel scoreTime = new JLabel("");
    private JPanel playField = new JPanel();
    private JPanel queueBox = new JPanel();
    private JTextField[][] gameBoard = new JTextField[9][9];
    private JTextField[] queue = new JTextField[5];
    private Color defaultColor = new Color(230, 230, 230);

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

        JMenuBar gameMenu = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        game.add(exit);
        gameMenu.add(game);
        setJMenuBar(gameMenu);

        JPanel header = new JPanel();
        header.setBackground(defaultColor);
        header.setLayout(new GridLayout(2, 4));


        scoreTime.setFont(new Font("SansSerif", Font.BOLD, 16));
        movesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));


        header.add(new JLabel());
        header.add(new JLabel());
        header.add(new JLabel());
        header.add(new JLabel());

        header.add(new JLabel());
        header.add(scoreTime);
        header.add(movesLabel);
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

    public void addObserver(Observable model) {
        model.addObserver(this);
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
                gameBoard[x][y] = Main.getNewTextField();
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
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 50;
        c.insets = new Insets(0, 0, 0, 0);
        for (int i = 0; i < queue.length; i++) {
            queue[i] = Main.getNewTextField();
            queue[i].setBackground(defaultColor);
            c.gridy = i;
            queueBox.add(queue[i], c);
        }
    }


    @Override
    public void update(Observable o, Object arg) {

        if (o.getClass().getSimpleName().equals("TileQueue")) {
            ArrayList<Integer> numberQueue = ((TileQueue) o).getQueue();
            int i;

            for (i = 0; i < numberQueue.size() && i < queue.length; i++) {
                queue[i].setText(numberQueue.get(i) + "");

            }
            for (; i < queue.length; i++) {
                queue[i].setText("");
            }

        } else if (o.getClass().getSimpleName().equals("Board")) {
            Tile[][] gameBoard = ((Board) o).getBoard();
            for (int y = 0; y < gameBoard.length; y++) {
                for (int x = 0; x < gameBoard[y].length; x++) {
                    if (gameBoard[x][y].isOccupied()) {
                        this.gameBoard[x][y].setText(gameBoard[x][y].getNumber() + "");
                    } else {
                        this.gameBoard[x][y].setText("");
                    }
                }
            }
            movesLabel.setText("Moves left: " + (Main.MAX_MOVES - Board.getMoves()));
            scoreTime.setText("Score: " + Board.getScore());

        }

    }

    private class MouseClick extends MouseAdapter {
        private Point boardPosition;

        MouseClick(Point position) {
            boardPosition = position;
        }

        public void mouseClicked(MouseEvent e) {
            if (Main.gameBoard.performMove(boardPosition.x, boardPosition.y)) {
                gameBoard[boardPosition.x][boardPosition.y].setBackground(defaultColor);
            }
        }

        public void mouseExited(MouseEvent e) {
            gameBoard[boardPosition.x][boardPosition.y].setBackground(defaultColor);
        }

        public void mouseEntered(MouseEvent e) {
            if (!Main.gameBoard.isOccupied(boardPosition.x, boardPosition.y)) {
                Color randomColor = new Color((int) ((Math.random() * 128) + 127), (int) ((Math.random() * 128) + 127), (int) ((Math.random() * 128) + 127));
                queue[0].setBackground(randomColor);
                gameBoard[boardPosition.x][boardPosition.y].setBackground(randomColor);
            }
        }

    }
}