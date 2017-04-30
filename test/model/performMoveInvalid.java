package model;

import org.junit.BeforeClass;
import org.junit.Test;

public class performMoveInvalid {
    private static Game game;
    private int row, column;

    @BeforeClass
    public static void setup(){
        game = UntimedGame.getInstance();
    }

    @Test
    public void testPerformMoveFor_InvalidRowAndColumn() {
        row = -1;
        column = 9;

        game.performMove(column, row);
    }

    @Test
    public void testPerformMoveFor_InvalidRow() {
        row = 8;
        column = 9;

        game.performMove(column, row);
    }

    @Test
    public void testPerformMoveFor_InvalidColumn() {
        row = -1;
        column = 8;

        game.performMove(column, row);
    }

}