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

    public static Board board = new Board();
    public static TileQueue queue;
    public static int maxMoves =50;
    public static void main(String[] args){
        startGame();
    }

    /***
     * Starts the game
     */
    public static void startGame(){
        queue = TileQueue.getTileQueue();
        BoardGUI gameBoardGUI = new BoardGUI();
        gameBoardGUI.addObserver(board);
        gameBoardGUI.addObserver(queue);
        queue.startGame();
        board.startGame();
        gameBoardGUI.setVisible(true);

    }

    /***
     * Creates a JTextField that is used to hold information for each Tile
     * @return The created JTextField
     */
    public static JTextField getNewTextField(){

        Border b = BorderFactory.createLineBorder(Color.black);
        JTextField t = new JTextField();
        t.setEditable(false);
        t.setPreferredSize(new Dimension(50, 50));
        t.setBorder(b);
        t.setHorizontalAlignment(JTextField.CENTER);
        t.setFont(new Font("SansSerif", Font.TRUETYPE_FONT, 16));
        return t;
    }

}
