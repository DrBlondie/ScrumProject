package main;

import model.Board;
import model.TileQueue;
import view.BoardGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Alex New on 3/25/2017.
 */
public class Main {

    public static final int MAX_MOVES = 50;
    public static Board gameBoard = new Board();
    public static TileQueue queue;

    public static void main(String[] args){
        startGame();
    }

    /***
     * Starts the game
     */
    public static void startGame(){
        queue = TileQueue.getTileQueue();
        BoardGUI gameBoardGUI = new BoardGUI();
        gameBoardGUI.addObserver(gameBoard);
        gameBoardGUI.addObserver(queue);
        queue.startGame();
        gameBoard.startGame();
        gameBoardGUI.setVisible(true);

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
