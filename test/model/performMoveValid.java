package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class performMoveValid {
    private Tile[][] board;
    private Game game;
    private int row, column;
    private int expected, result;

    @Before
    public void setup(){
        game = UntimedGame.getInstance();
        board = game.getBoard();
    }

    @Test
    public void testPerformMoveFor_A() {
        row = 0;
        column = 0;

        board[column + 1][row].setNumber(1);
        board[column + 1][row + 1].setNumber(1);
        board[column][row + 1].setNumber(1);
        TileQueue queue = game.getQueue();
        int next = queue.getNext();

        if(next == 3){
            expected = 0;
            game.performMove(row, column);
            result = board[column][row].getNumber();
        }
        else{
            expected = next;
            game.performMove(row, column);
            result = board[column][row].getNumber();
        }

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPerformMoveFor_B() {
        row = 4;
        column = 4;

        expected = board[column][row].getNumber();
        game.performMove(column, row);
        result = board[column][row].getNumber();

        Assert.assertEquals(expected, result);
    }
}