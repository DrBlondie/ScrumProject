package model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class getSurroundingValuesWB {


    private int expected;
    private int result;
    private static Game game;
    private static Tile[][] board;


    @Before
    public void setup(){
        game = UntimedGame.getInstance();
        board = game.getBoard();
    }

    @Test
    public void testGetSurroundingValuesWB_TopLeft() {
        expected = 3;
        board[1][0].setNumber(1);
        board[1][0].setOccupied(true);
        board[1][1].setNumber(1);
        board[0][1].setNumber(1);
        board[0][1].setOccupied(true);
        result =game.getSurroundingValues(0,0);
        Assert.assertEquals(expected,result);



    }
    @Test
    public void testGetSurroundingValuesWB_BottomRight() {
        expected = 3;
        board[7][7].setNumber(1);
        board[7][8].setNumber(1);
        board[7][8].setOccupied(true);
        board[8][7].setNumber(1);
        board[8][7].setOccupied(true);
        result =game.getSurroundingValues(8,8);
        Assert.assertEquals(expected,result);



    }
    @Test
    public void testGetSurroundingValuesWB_Occupied() {
        expected = -1;
        board[1][0].setNumber(1);
        board[1][0].setOccupied(false);
        board[1][1].setNumber(1);
        board[1][1].setOccupied(false);
        board[0][1].setNumber(1);
        board[0][1].setOccupied(false);
        board[0][0].setOccupied(true);
        result =game.getSurroundingValues(0,0);
        Assert.assertEquals(expected,result);



    }

    @Test
    public void testGetSurroundingValues_NotEmptyAround() {

        result =game.getSurroundingValues(4,4);
        expected=result;
        Assert.assertEquals(expected,result);


    }





}
