package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by levin on 4/28/17.
 */
public class getSurroundingValuesInvalid {

    private int expected;
    private int result;
    private Game game;



    @Before
    public void setup(){
        game = UntimedGame.getInstance();
        expected=-1;
    }

    @Test
    public void testGetSurroundingInvalidValuesFor_LeastColRow() {

        result = game.getSurroundingValues(-1,-1);

        Assert.assertEquals(expected,result);


    }
    @Test
    public void testGetSurroundingInvalidValuesFor_MaxColRow() {

        result = game.getSurroundingValues(9,9);

        Assert.assertEquals(expected,result);


    }

    @Test
    public void testGetSurroundingInvalidValuesFor_LeastColMaxCol() {

        result = game.getSurroundingValues(-1,9);

        Assert.assertEquals(expected,result);


    }
    @Test
    public void testGetSurroundingInvalidValuesFor_MaxColLeastRow() {
        result = game.getSurroundingValues(9,-1);

        Assert.assertEquals(expected,result);


    }
}