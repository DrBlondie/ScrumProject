package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Created by levin on 4/28/17.
 */
public class getSurroundingValuesValid {
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_COLUMNS = 9;
    private int expected;
    private int result;
    private Game game;
    private Tile[][] board;


    @BeforeClass
    public static void setupBeforeClass(){
        Game game = UntimedGame.getInstance();
        Tile[][] board = game.getBoard();


    }

    @Test
    public void testGetSurroundingValuesFor_TopLeftCorner() {
        expected = 0;
        expected += board[0][1].getNumber();
        expected += board[1][1].getNumber();
        expected += board[1][0].getNumber();
        result = game.getSurroundingValues(0,0);

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testGetSurroundingValuesFor_TopRightCorner() {
        expected = 0;
        expected += board[NUMBER_OF_COLUMNS - 2][0].getNumber();
        expected += board[NUMBER_OF_COLUMNS - 2][1].getNumber();
        expected += board[NUMBER_OF_COLUMNS - 1][1].getNumber();
        result= game.getSurroundingValues(NUMBER_OF_COLUMNS - 1 ,0);

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testGetSurroundingValuesFor_BottomLeftCorner() {
        expected = 0;
        expected += board[0][NUMBER_OF_ROWS - 2].getNumber();
        expected += board[1][NUMBER_OF_ROWS - 2].getNumber();
        expected += board[1][NUMBER_OF_ROWS - 1].getNumber();
        result= game.getSurroundingValues(0 ,NUMBER_OF_ROWS - 1);

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testGetSurroundingValuesFor_BottomRightCorner() {
        expected = 0;
        expected += board[NUMBER_OF_COLUMNS - 1][NUMBER_OF_ROWS - 2].getNumber();
        expected += board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 2].getNumber();
        expected += board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 1].getNumber();
        result= game.getSurroundingValues(NUMBER_OF_COLUMNS - 1 ,NUMBER_OF_ROWS - 1);

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testGetSurroundingValuesFor_LeftBorder(){
        expected = 0;
        int row = 5;
        int col = 0;
        expected += board[row - 1][col].getNumber();
        expected += board[row + 1][col].getNumber();
        expected += board[row - 1][col + 1].getNumber();
        expected += board[row + 1][col + 1].getNumber();
        expected += board[row][col + 1].getNumber();
        result = game.getSurroundingValues(0, 5);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetSurroundingValuesFor_RightBorder(){
        expected = 0;
        int row = 5;
        int col = NUMBER_OF_COLUMNS - 1;
        expected += board[row - 1][col].getNumber();
        expected += board[row + 1][col].getNumber();
        expected += board[row - 1][col - 1].getNumber();
        expected += board[row + 1][col - 1].getNumber();
        expected += board[row][col - 1].getNumber();
        result = game.getSurroundingValues(NUMBER_OF_COLUMNS - 1, 5);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetSurroundingValuesFor_TopBorder(){
        expected = 0;
        int row = 0;
        int col = 5;
        expected += board[row][col - 1].getNumber();
        expected += board[row][col + 1].getNumber();
        expected += board[row + 1][col - 1].getNumber();
        expected += board[row + 1][col].getNumber();
        expected += board[row + 1][col + 1].getNumber();
        result = game.getSurroundingValues(5, 0);

        Assert.assertEquals(expected, result);
    }


    @Test
    public void testGetSurroundingValuesFor_BottomBorder(){
        expected = 0;
        int row = NUMBER_OF_ROWS - 1;
        int col = 5;
        expected += board[row][col - 1].getNumber();
        expected += board[row][col + 1].getNumber();
        expected += board[row - 1][col - 1].getNumber();
        expected += board[row - 1][col].getNumber();
        expected += board[row - 1][col + 1].getNumber();
        result = game.getSurroundingValues(0, NUMBER_OF_ROWS - 1);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetSurroundingValuesFor_Interior(){
        expected = 0;
        int row = 5;
        int col = 5;
        expected += board[row - 1][col - 1].getNumber();
        expected += board[row - 1][col].getNumber();
        expected += board[row - 1][col + 1].getNumber();
        expected += board[row][col - 1].getNumber();
        expected += board[row][col + 1].getNumber();
        expected += board[row + 1][col - 1].getNumber();
        expected += board[row + 1][col].getNumber();
        expected += board[row + 1][col + 1].getNumber();

        result = game.getSurroundingValues(5, 5);

    }

}