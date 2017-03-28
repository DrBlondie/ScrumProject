package model;

import main.Main;
import main.Tile;

import java.util.Observable;


public class Board extends Observable {

    public static int NUMBER_OF_MOVES = 0;
    public int score = 0;
    private final int NUMBER_OF_ROWS = 9; //<-- Made these constants -Ben
    private final int NUMBER_OF_COLUMNS = 9; //<-- ""
    private Tile[][] board;

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

    public void startGame() {
        setChanged();
        notifyObservers();
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void performMove(int row, int column) {

        if (NUMBER_OF_MOVES >= Main.MAX_MOVES) {
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
                score += removeCornerTiles(row, column) * 10;


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
        String isCorner = isCornerSpace(row, column);
        switch (isCorner) {
            case "TOP_LEFT":
                sum += board[0][1].getNumber();
                sum += board[1][1].getNumber();
                sum += board[1][0].getNumber();
                return sum % 10;
            case "BOTTOM_RIGHT":
                sum += board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 2].getNumber();
                sum += board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 2].getNumber();
                sum += board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 1].getNumber();
                return sum % 10;
            case "BOTTOM_LEFT":
                sum += board[NUMBER_OF_ROWS - 2][0].getNumber();
                sum += board[NUMBER_OF_ROWS - 2][1].getNumber();
                sum += board[NUMBER_OF_ROWS - 1][1].getNumber();
                return sum % 10;
            case "TOP_RIGHT":
                sum += board[0][NUMBER_OF_COLUMNS - 1].getNumber();
                sum += board[1][NUMBER_OF_COLUMNS - 2].getNumber();
                sum += board[1][NUMBER_OF_COLUMNS - 1].getNumber();
                return sum % 10;
            default:
                break;
        }
        return sum;
    }


    public int removeCornerTiles(int row, int column) {
        board[row][column].emptyTile();
        int removed = 0;
        String isCorner = isCornerSpace(row, column);
        switch (isCorner) {
            case "TOP_LEFT":
                if (board[0][1].isOccupied()) {
                    board[0][1].emptyTile();
                    removed++;
                }
                if (board[1][0].isOccupied()) {
                    board[1][0].emptyTile();
                    removed++;
                }
                if (board[1][1].isOccupied()) {
                    board[1][1].emptyTile();
                    removed++;
                }
                break;
            case "BOTTOM_RIGHT":
                if (board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 2].isOccupied()) {
                    board[NUMBER_OF_ROWS - 1][NUMBER_OF_COLUMNS - 2].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 2].isOccupied()) {
                    board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 2].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 1].isOccupied()) {
                    board[NUMBER_OF_ROWS - 2][NUMBER_OF_COLUMNS - 1].emptyTile();
                    removed++;
                }
                break;
            case "BOTTOM_LEFT":
                if (board[NUMBER_OF_ROWS - 2][0].isOccupied()) {
                    board[NUMBER_OF_ROWS - 2][0].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_ROWS - 2][1].isOccupied()) {
                    board[NUMBER_OF_ROWS - 2][1].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_ROWS - 1][1].isOccupied()) {
                    board[NUMBER_OF_ROWS - 1][1].emptyTile();
                    removed++;
                }
                break;
            case "TOP_RIGHT":
                if (board[0][NUMBER_OF_COLUMNS - 1].isOccupied()) {
                    board[0][NUMBER_OF_COLUMNS - 1].emptyTile();
                    removed++;
                }
                if (board[1][NUMBER_OF_COLUMNS - 2].isOccupied()) {
                    board[1][NUMBER_OF_COLUMNS - 2].emptyTile();
                    removed++;
                }
                if (board[1][NUMBER_OF_COLUMNS - 1].isOccupied()) {
                    board[1][NUMBER_OF_COLUMNS - 1].emptyTile();
                    removed++;
                }
                break;
            default:
                break;
        }
        if (removed >= 3) {
            return removed;
        } else {
            return 0;
        }

    }

    private boolean isModulo(int row, int column, int surroundingTileSummation) {
        return board[row][column].getNumber() == surroundingTileSummation;
    }

    public int getScore() {
        return score;
    }
}





