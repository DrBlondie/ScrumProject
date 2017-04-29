package test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by levin on 4/28/17.
 */
public class getSurroundingValuesValid {
    Tile[][] board;
    Game game ;
    int left,top,right,bottom,topLeft,topRight,bottomLeft,bottomRight;
    int expected,result;

    @Before
    public void setup(){
        game =UntimedGame.getInstance();
        board = game.getBoard();

    }

    @Test
    public void testGetSurreoundingValuesFor_A() {
        result= game.getSurroundingValues(0,0);
        right=board[1][0].getNumber();
        bottom=board[0][1].getNumber();
        bottomRight=board[1][1].getNumber();
        expected=right+bottom+bottomRight;

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testGetSurreoundingValuesFor_B() {
        result= game.getSurroundingValues(8,8);
        topLeft=board[7][7].getNumber();
        top = board[7][8].getNumber();
        left =board[8][7].getNumber();
        expected=topLeft+top+left;

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testGetSurreoundingValuesFor_C() {
        result= game.getSurroundingValues(4,4);
        topLeft=board[3][3].getNumber();
        top=board[3][4].getNumber();
        topRight=board[3][5].getNumber();
        left=board[4][3].getNumber();
        bottomLeft=board[5][3].getNumber();
        bottom=board[5][4].getNumber();
        bottomRight=board[5][5].getNumber();
        right =board[4][5].getNumber();
        expected=left+top+right+bottom+topLeft+topRight+bottomLeft+bottomRight;

        Assert.assertEquals(expected,result);

    }


}