package model;

import java.util.Observable;


public abstract class Game extends Observable {

    private static final int NUMBER_OF_ROWS = 9;
    private static final int NUMBER_OF_COLUMNS = 9;
    private int score;
    private Tile[][] board;
    private TileQueue currentQueue;


    Game(TileQueue queue) {
        currentQueue = queue;
        board = new Tile[NUMBER_OF_COLUMNS][NUMBER_OF_ROWS];
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            for (int j = 0; j < NUMBER_OF_ROWS; j++) {
                board[i][j] = new Tile();
                if (i == 0 || i == NUMBER_OF_COLUMNS - 1 || j == 0 || j == NUMBER_OF_ROWS - 1) {
                    board[i][j].emptyTile();
                }
            }
        }
        score = 0;
    }

    public void updateGame() {
        setChanged();
        notifyObservers(board);
    }

    public boolean isOccupied(int col, int row) {
        return board[col][row].isOccupied();
    }


    boolean performMove(int col, int row) {

        int surroundingTileSummation;
        if (board[col][row].isOccupied()) {
            return false;
        }
        int removed = 0;
        board[col][row].setNumber(currentQueue.placeTile());
        board[col][row].setOccupied(true);
        surroundingTileSummation = getSurroundingValues(col, row);
        if (isModulo(col, row, surroundingTileSummation)) {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (!(col + x < 0 || col + x > 8 || row + y < 0 || row + y > 8) && board[col + x][row + y].isOccupied()) {
                        removed++;
                        board[col + x][row + y].emptyTile();
                    }
                }
            }
            removed--;
            if (removed >= 3) {
                score += 10 * removed;
            }
        }
        return true;
    }

    private int getSurroundingValues(int col, int row) {
        int values = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (!(col + x < 0 || col + x > 8 || row + y < 0 || row + y > 8)) {
                    if ((x == 0 && y == 0) || !board[col + x][row + y].isOccupied()) {
                        continue;
                    }
                    values += board[col + x][row + y].getNumber();
                }
            }
        }
        return values;
    }

    private boolean isModulo(int row, int col, int surroundingTileSummation) {
        return board[row][col].getNumber() == (surroundingTileSummation % 10);
    }


    public boolean gameWin() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (isOccupied(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getScore() {
        return score;
    }

    Tile[][] getBoard() {
        return board;
    }


    public abstract String getGameValue();

    public abstract boolean checkMove(int col, int row);

    public abstract boolean gameOver();

}





