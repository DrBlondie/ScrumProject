package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import model.Board;
import model.TileQueue;
import view.BoardView;


public class Main {

    public static final int MAX_MOVES = 50;
    public static Board gameBoard = new Board();

    public static void main(String[] args){
        TileQueue queue = TileQueue.getTileQueue();
        BoardView gameBoardView = new BoardView();
        gameBoardView.addObserver(gameBoard);
        gameBoardView.addObserver(queue);
        queue.startGame();
        gameBoard.startGame();
        gameBoardView.setVisible(true);
    }


    /***
     * Creates a JTextField that is used to hold information for each Tile
     * @return The created JTextField
     */
    public static JTextField getNewTextField(){

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(50, 50));
        textField.setBorder(gameBorder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("SansSerif", Font.TRUETYPE_FONT, 16));
        return textField;
    }

}
