import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Alex New on 3/25/2017.
 */
public class Main {

    public static Board board = new Board();

    public static void main(String[] args){
        startGame();
    }

    /***
     * Starts the game
     */
    public static void startGame(){
        GUI boardGUI = new GUI();
        boardGUI.setVisible(true);
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
