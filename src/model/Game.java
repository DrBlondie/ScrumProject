package model;

import java.awt.Point;
import java.util.Observable;


public abstract class Game extends Observable {

    private static final int NUMBER_OF_ROWS = 9;
    private static final int NUMBER_OF_COLUMNS = 9;
    private int score;
    Tile[][] board;
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
            return null;
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
        hintsRemaining--;
        return hintPoint;
    }

    public int getHintsRemaining(){
        return hintsRemaining;
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


    void performMove(int col, int row) {
        if(col < 0 || col > 8 || row < 0 || row > 8 || board[col][row].isOccupied() ){
            return;
        }
        int removed = 0;
        board[col][row].setNumber(currentQueue.placeTile());
        board[col][row].setOccupied(true);
        int surroundingTileSummation = getSurroundingValues(col, row);
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
        updateGame();
    }

    int getSurroundingValues(int col, int row) {
        int values = 0;
        boolean emptyAround = true;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (!(col + x < 0 || col + x > 8 || row + y < 0 || row + y > 8)) {
                    if ((x == 0 && y == 0) || !board[col + x][row + y].isOccupied()) {
                        continue;
                    }
                    emptyAround = false;
                    values += board[col + x][row + y].getNumber();
                }
            }
        }
        if(emptyAround){
            return -1;
        }
        return values;
    }

    private boolean isModulo(int col, int row, int surroundingTileSummation) {
        return board[col][row].getNumber() == (surroundingTileSummation % 10);
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

    public boolean removeCommonTiles(int col, int row) {
        if (removeTileLeft == 1) {
            int removeNum = board[col][row].getNumber();
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {

                    if (board[x][y].getNumber() == removeNum) {
                        board[x][y].emptyTile();
                    }
                }
            }
            updateGame();
            removeTileLeft--;
            return true;
        }
        return false;
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

    public void seedGame(){
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                board[i][j].emptyTile();
            }
        }
        board[4][4].setOccupied(true);
        board[4][4].setNumber(currentQueue.getNext());
        updateGame();
    }


}





