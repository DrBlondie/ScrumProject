package model;

import java.awt.Point;
import java.util.Observable;


public abstract class Game extends Observable {

    private static final int NUMBER_OF_ROWS = 9;
    private static final int NUMBER_OF_COLUMNS = 9;
    private int score;
    private Tile[][] board;
    private TileQueue currentQueue;
    private int removeTileLeft;
    private int hintsRemaining = 3;

    Game(TileQueue queue) {
        currentQueue = queue;
        board = new Tile[NUMBER_OF_COLUMNS][NUMBER_OF_ROWS];
        restartGame();
    }

    void restartGame() {
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            for (int j = 0; j < NUMBER_OF_ROWS; j++) {
                board[i][j] = new Tile();
                if (i == 0 || i == NUMBER_OF_COLUMNS - 1 || j == 0 || j == NUMBER_OF_ROWS - 1) {
                    board[i][j].emptyTile();
                }
            }
        }
        score = 0;
        hintsRemaining = 3;
        removeTileLeft = 1;
    }


    public Point getHint() {
        Point hintPoint = new Point(-1, -1);
        if (hintsRemaining < 1) {
            return hintPoint;
        }
        int tilesRemoved = -1;
        for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
            for (int row = 0; row < NUMBER_OF_ROWS; row++) {
                if (board[col][row].isOccupied()) {
                    continue;
                }
                if ((getSurroundingValues(col, row) % 10) == currentQueue.getNext()) {
                    int tempRemoved = 0;
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            if (!(col + x < 0 || col + x > 8 || row + y < 0 || row + y > 8) && board[col + x][row + y].isOccupied()) {
                                tempRemoved++;
                            }
                        }
                    }
                    if (tempRemoved > tilesRemoved) {
                        tilesRemoved = tempRemoved;
                        hintPoint.setLocation(col, row);
                    }
                }
            }
        }
        return hintPoint;
    }

    public int getRemoveTileLeft() {
        return removeTileLeft;
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
            if (removeTileLeft == 1) {
                int removeNum = board[col][row].getNumber();
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {

                        if (board[x][y].getNumber() == removeNum) {
                            board[x][y].emptyTile();
                        }
                    }
                }
                removeTileLeft--;
            }
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

    protected int getSurroundingValues(int col, int row) {
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

    public TileQueue getQueue() {
        return currentQueue;
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

    public abstract void newGame();


}





