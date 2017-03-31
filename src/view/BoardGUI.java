package view;

import main.Main;
import model.Board;
import model.Tile;
import model.TileQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class BoardGUI extends JFrame implements Observer {
    private JLabel movesLabel = new JLabel("");
    private JLabel scoreTime = new JLabel("");
    private JPanel playField = new JPanel();
    private JPanel queueBox = new JPanel();
    private JTextField[][] gameBoard = new JTextField[9][9];
    private JTextField[] queue = new JTextField[5];
    private Color defaultColor = new Color(230,230,230);

    public BoardGUI() {
        setBackground(defaultColor);
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
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        game.add(exit);
        gameMenu.add(game);
        setJMenuBar(gameMenu);

        JPanel header = new JPanel();
        header.setBackground(defaultColor);
        header.setLayout(new GridLayout(1, 3));

        header.add(movesLabel);
        JLabel label = new JLabel("Sum Fun");

        label.setFont(new Font("SansSerif", Font.BOLD, 32));
        scoreTime.setFont(new Font("SansSerif", Font.BOLD, 16));
        movesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
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
        boardPanel.setBackground(defaultColor);
        add(boardPanel, BorderLayout.CENTER);
        setResizable(false);

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
                gameBoard[x][y].setBackground(defaultColor);

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
            queue[i].setBackground(defaultColor);
            c.gridy = i;
            queueBox.add(queue[i], c);
        }
        queue[0].setBackground(Color.pink);
    }


    @Override
    public void update(Observable o, Object arg) {

        if (o.getClass().getSimpleName().equals("TileQueue")) {
            ArrayList<Integer> _queue = ((TileQueue) o).getQueue();
            int i;

            for (i = 0; i < _queue.size() && i < queue.length; i++) {
                queue[i].setText(_queue.get(i) + "");

            }
            for (; i < queue.length; i++) {
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
            scoreTime.setText("Score: " + Board.getScore());

        }

    }

    class MouseClick extends MouseAdapter {
        private Point boardPosition;

        public MouseClick(Point position) {
            boardPosition = position;
        }

        public void mouseClicked(MouseEvent e) {
            if(Main.gameBoard.performMove(boardPosition.x, boardPosition.y)){
                gameBoard[boardPosition.x][boardPosition.y].setBackground(defaultColor);
            }
        }
        public void mouseExited(MouseEvent e) {
            gameBoard[boardPosition.x][boardPosition.y].setBackground(defaultColor);
        }
        public void mouseEntered(MouseEvent e) {
            if (Main.gameBoard.isOccupied(boardPosition.x, boardPosition.y) == false) {
                gameBoard[boardPosition.x][boardPosition.y].setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            }
        }

    }
}