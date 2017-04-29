package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by levin on 4/28/17.
 */
public class getSurroundingValuesInvalid {
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_COLUMNS = 9;
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
    public void testGetSurroundingInvalidValuesFor_LeastColRow() {
        expected=-1;
        result = game.getSurroundingValues(-1,-1);

        Assert.assertEquals(expected,result);


    }
    @Test
    public void testGetSurroundingInvalidValuesFor_MaxColRow() {
        expected=-1;
        result = game.getSurroundingValues(9,9);

        Assert.assertEquals(expected,result);


    }

    @Test
    public void testGetSurroundingInvalidValuesFor_LeastColMaxCol() {
        expected=-1;
        result = game.getSurroundingValues(-1,9);

        Assert.assertEquals(expected,result);


    }
    @Test
    public void testGetSurroundingInvalidValuesFor_MaxColLeastRow() {
        expected=-1;
        result = game.getSurroundingValues(9,-1);

        Assert.assertEquals(expected,result);


    }
}