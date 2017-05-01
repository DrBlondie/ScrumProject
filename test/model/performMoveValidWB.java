package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class performMoveValidWB {
    private Tile[][] board;
    private Game game;
    private int row, column;
    private boolean result;

    @Before
    public void setup(){
        game = UntimedGame.getInstance();
        board = game.getBoard();
    }

    @Test
    public void testPerformMoveFor_PathInvalidRowOrColumn() {
        row = 5;
        column = -1;

        game.performMove(column, row);
        Assert.assertEquals(0,game.getScore());
        game.restartGame();
    }

    @Test
    public void testPerformMoveFor_PathTileIsNotModulo() {
        row = 0;
        column = 0;
        board[column + 1][row].setNumber(0);
        board[column + 1][row].setOccupied(true);
        board[column + 1][row + 1].setNumber(1);
        board[column + 1][row + 1].setOccupied(true);
        board[column][row + 1].setNumber(0);
        board[column][row + 1].setOccupied(true);
        TileQueue queue = game.getQueue();
        int next = queue.getNext();

        while(next == 1) {
            game.performMove(8,8);
            board[8][8].setNumber(0);
            board[8][8].setOccupied(false);
            next = queue.getNext();
        }
        game.performMove(row, column);
        result = board[column][row].isOccupied();
        Assert.assertEquals("Initial Tile tested: ",true, result);
        Assert.assertEquals(0, game.getScore());
        game.restartGame();
    }

    @Test
    public void testPerformMoveFor_PathRemoveLessThan3Tiles(){
        row = 0;
        column = 0;

        board[column + 1][row + 1].setNumber(1);
        TileQueue queue = game.getQueue();
        int next = queue.getNext();

        while(next != 1) {
            game.performMove(8,8);
            board[8][8].setNumber(0);
            board[8][8].setOccupied(false);
            next = queue.getNext();
        }

        game.performMove(row, column);
        result = board[column][row].isOccupied();
        Assert.assertEquals("Initial Tile tested: ",false, result);
        result = board[column + 1][row + 1].isOccupied();
        Assert.assertEquals(false, result);
        Assert.assertEquals(0, game.getScore());
        game.restartGame();
    }

    @Test
    public void testPerformMoveFor_PathRemove3OrMoreTiles(){
        row = 0;
        column = 0;
        board[column + 1][row].setNumber(0);
        board[column + 1][row].setOccupied(true);
        board[column + 1][row + 1].setNumber(1);
        board[column + 1][row + 1].setOccupied(true);
        board[column][row + 1].setNumber(0);
        board[column][row + 1].setOccupied(true);
        TileQueue queue = game.getQueue();
        int next = queue.getNext();

        while(next != 1) {
            game.performMove(8,8);
            board[8][8].setNumber(0);
            board[8][8].setOccupied(false);
            next = queue.getNext();
        }

        game.performMove(row, column);
        result = board[column][row].isOccupied();
        Assert.assertEquals("Initial Tile tested: ",false, result);
        result = board[column + 1][row].isOccupied();
        Assert.assertEquals(false, result);
        result = board[column + 1][row + 1].isOccupied();
        Assert.assertEquals(false, result);
        result = board[column][row + 1].isOccupied();
        Assert.assertEquals(false, result);
        Assert.assertEquals(30, game.getScore());
        game.restartGame();
    }
}