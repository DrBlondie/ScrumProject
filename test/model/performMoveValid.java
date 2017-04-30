package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class performMoveValid {
    private Tile[][] board;
    private Game game;
    private int row, column;

    @Before
    public void setup(){
        game = UntimedGame.getInstance();
        board = game.getBoard();
    }

    @Test
    public void testPerformMoveFor_PlaceInEmptyTile() {
        row = 0;
        column = 0;

        board[column + 1][row].setNumber(0);
        board[column + 1][row].setOccupied(true);
        board[column + 1][row + 1].setNumber(0);
        board[column][row + 1].setNumber(1);
        board[column][row + 1].setOccupied(true);
        TileQueue queue = game.getQueue();
        int next = queue.getNext();

        boolean expected = !(next == 1);
        game.performMove(row, column);
        boolean result = board[column][row].isOccupied();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPerformMoveFor_PlaceInFilledTile() {
        row = 4;
        column = 4;

        int expected = board[column][row].getNumber();
        game.performMove(column, row);
        int result = board[column][row].getNumber();

        Assert.assertEquals(expected, result);
    }
}