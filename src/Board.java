import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board {

    private Tile[][] board;
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_COLUMNS = 9;


    public Board() {

        board = new Tile[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS ; j++) {
                JTextField t = Main.getNewTextField();
                t.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        t.setBackground(Color.red);
                    }
                });
                board[i][j] = new Tile(i,j,t);
                if(i == 0 || i == 8 || j == 0 || j == 8){
                    board[i][j].setNumber(-1);
                }
            }
        }


    }

    public Tile[][] getBoard(){
        return board;
    }

    public String performMove(Tile placedTile) {

        int score = 0;

        /**if (isCornerSpace( int row, int column)){
            score = calculateCornerPoints();
         }
        **/

        return "The value of the surrounding tiles modulo the queue tile value is:" + 0;
    }

    /*
     Tests space to see if it is in the corner and returns the corner position if true, otherwise returns false
     */
    public String isCornerSpace(int row, int column) {
        if (row == 0 && column == 0) {
            return "TOP_LEFT";
        }
        if (row == NUMBER_OF_ROWS - 1 && column == NUMBER_OF_COLUMNS - 1) {
            return "BOTTOM_RIGHT";
        }
        if (row == NUMBER_OF_ROWS - 1 && column == 0) {
            return "BOTTOM_LEFT";
        }
        if (row == 0 && column == NUMBER_OF_COLUMNS - 1) {
            return "TOP_RIGHT";
        }
        return "FALSE";
    }

    /*
    Uses the board indices to get number values to corner tiles
     */
    public int calculateCornerPoints() {
        int sum = 0;
        if (isCornerSpace(0,0).equals("TOP_LEFT")) {
            sum += board[0][1].getNumber();
            sum += board[1][1].getNumber();
            sum += board[1][0].getNumber();
            return sum % 10;
        }
        if (isCornerSpace(0,0).equals("BOTTOM_RIGHT")) {
            sum += board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 1].getNumber();
            sum += board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 2].getNumber();
            sum += board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 1].getNumber();
            return sum % 10;
        }
        if (isCornerSpace(0,0).equals("BOTTOM_LEFT")) {
            sum += board[NUMBER_OF_ROWS - 1][0].getNumber();
            sum += board[NUMBER_OF_ROWS - 2][0].getNumber();
            sum += board[NUMBER_OF_ROWS - 1][1].getNumber();
            return sum % 10;
        }
        if (isCornerSpace(0,0).equals("TOP_RIGHT")) {
            sum += board[0][NUMBER_OF_COLUMNS - 1].getNumber();
            sum += board[0][NUMBER_OF_COLUMNS - 2].getNumber();
            sum += board[1][NUMBER_OF_COLUMNS - 1].getNumber();
            return sum % 10;
        }
        return sum;
    }
}





