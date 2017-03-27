package model;

import main.Tile;

import java.util.Observable;


public class Board extends Observable {

    public static int NUMBER_OF_MOVES = 0;
    private Tile[][] board;
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_COLUMNS = 9;

    public Board() {
        board = new Tile[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                board[i][j] = new Tile();
                if (i == 0 || i == NUMBER_OF_ROWS - 1 || j == 0 || j == NUMBER_OF_COLUMNS - 1) {
                    board[i][j].emptyTile();
                }
            }
        }
    }

    public void startGame(){
        setChanged();
        notifyObservers();
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void performMove(int row, int column) {

        if(NUMBER_OF_MOVES >= 50){
            return;
        } else {
            int surroundingTileSummation = 0;
            if (board[row][column].isOccupied()) {
                return;
            }
            if (isCornerSpace(row, column) != "FALSE") {
                surroundingTileSummation = calculateCornerPoints(row, column);
            }

            board[row][column].setNumber(TileQueue.getTileQueue().placeTile());
            board[row][column].setOccupied(true);

            if (isModulo(row, column, surroundingTileSummation)) {
                removeCornerTiles(row, column);
            }

            NUMBER_OF_MOVES++;
            setChanged();
            notifyObservers();
        }

    }

    /*
     Tests space to see if it is in the corner and returns the corner position if true, otherwise returns false
     */
    public String isCornerSpace(int row, int column) {
        if (row == 0 && column == 0) {
            return "TOP_LEFT";
        }
        if (row == NUMBER_OF_ROWS - 1 && column == NUMBER_OF_COLUMNS - 1) {
            return "BOTTOM_RIGHT";
        }
        if (row == NUMBER_OF_ROWS - 1 && column == 0) {
            return "BOTTOM_LEFT";
        }
        if (row == 0 && column == NUMBER_OF_COLUMNS - 1) {
            return "TOP_RIGHT";
        }
        return "FALSE";
    }

    /*
    Uses the board indices to get number values to corner tiles
     */
    public int calculateCornerPoints(int row, int column) {
        int sum = 0;
        if (isCornerSpace(row, column).equals("TOP_LEFT")) {
            sum += board[0][1].getNumber();
            sum += board[1][1].getNumber();
            sum += board[1][0].getNumber();
            return sum % 10;
        }
        if (isCornerSpace(row, column).equals("BOTTOM_RIGHT")) {
            sum += board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 2].getNumber();
            sum += board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 2].getNumber();
            sum += board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 1].getNumber();
            return sum % 10;
        }
        if (isCornerSpace(row, column).equals("BOTTOM_LEFT")) {
            sum += board[NUMBER_OF_ROWS - 2][0].getNumber();
            sum += board[NUMBER_OF_ROWS - 2][1].getNumber();
            sum += board[NUMBER_OF_ROWS - 1][1].getNumber();
            return sum % 10;
        }
        if (isCornerSpace(row, column).equals("TOP_RIGHT")) {
            sum += board[0][NUMBER_OF_COLUMNS - 1].getNumber();
            sum += board[1][NUMBER_OF_COLUMNS - 2].getNumber();
            sum += board[1][NUMBER_OF_COLUMNS - 1].getNumber();
            return sum % 10;
        }
        return sum;
    }


    public void removeCornerTiles(int row, int column) {
        board[row][column].emptyTile();

        if (isCornerSpace(row, column).equals("TOP_LEFT")) {
            board[0][1].emptyTile();
            board[1][0].emptyTile();
            board[1][1].emptyTile();
        }
        if (isCornerSpace(row, column).equals("BOTTOM_RIGHT")) {
            board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 2].emptyTile();
            board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 2].emptyTile();
            board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 1].emptyTile();
        }
        if (isCornerSpace(row, column).equals("BOTTOM_LEFT")) {
            board[NUMBER_OF_ROWS - 2][0].emptyTile();
            board[NUMBER_OF_ROWS - 2][1].emptyTile();
            board[NUMBER_OF_ROWS - 1][1].emptyTile();
        }
        if (isCornerSpace(row, column).equals("TOP_RIGHT")) {
            board[0][NUMBER_OF_COLUMNS - 1].emptyTile();
            board[1][NUMBER_OF_COLUMNS - 2].emptyTile();
            board[1][NUMBER_OF_COLUMNS - 1].emptyTile();
        }
    }

    private boolean isModulo(int row, int column, int surroundingTileSummation) {
        return board[row][column].getNumber() == surroundingTileSummation;
    }
}





