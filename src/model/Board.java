package model;

import main.Main;
import main.Tile;

import java.util.Observable;


public class Board extends Observable {

    public static int NUMBER_OF_MOVES = 0;
    public int score = 0;
    private Tile[][] board;
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_COLUMNS = 9;

    public Board() {
        board = new Tile[NUMBER_OF_COLUMNS][NUMBER_OF_ROWS];
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            for (int j = 0; j < NUMBER_OF_ROWS; j++) {
                board[i][j] = new Tile();
                if (i == 0 || i == NUMBER_OF_COLUMNS - 1 || j == 0 || j == NUMBER_OF_ROWS - 1) {
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

    public void performMove(int col, int row) {

        if (NUMBER_OF_MOVES >= Main.MAX_MOVES) {
            return;
        } else {
            int surroundingTileSummation = 0;
            if (board[col][row].isOccupied()) {
                return;
            }
            if (!isCornerSpace(col, row).equals("FALSE")) {

                surroundingTileSummation = calculateCornerPoints(col, row);
            }

            board[col][row].setNumber(TileQueue.getTileQueue().placeTile());
            board[col][row].setOccupied(true);

            if (isModulo(col, row, surroundingTileSummation)) {
                if (board[col][row].getNumber() != 0 && isCornerSpace(col, row).equals("FALSE"))
                    board[col][row].emptyTile();
                score += removeCornerTiles(col, row) * 10;
            }

            NUMBER_OF_MOVES++;
            setChanged();
            notifyObservers();
        }

    }

    /*
     Tests space to see if it is in the corner and returns the corner position if true, otherwise returns false
     */
    public String isCornerSpace(int col, int row) {
        if (col == 0 && row == 0) {
            return "TOP_LEFT";
        }
        if (col == NUMBER_OF_COLUMNS - 1 && row == NUMBER_OF_ROWS - 1) {
            return "BOTTOM_RIGHT";
        }
        if (col == NUMBER_OF_COLUMNS - 1 && row == 0) {
            return "TOP_RIGHT";
        }
        if (col == 0 && row == NUMBER_OF_ROWS - 1) {
            return "BOTTOM_LEFT";
        }
        return "FALSE";
    }

    /*
    Uses the board indices to get number values to corner tiles
     */
    public int calculateCornerPoints(int col, int row) {
        int sum = 0;
        String isCorner = isCornerSpace(col, row);
        switch (isCorner) {
            case "TOP_LEFT":
                sum += board[0][1].getNumber();
                sum += board[1][1].getNumber();
                sum += board[1][0].getNumber();
                return sum % 10;
            case "BOTTOM_RIGHT":
                sum += board[NUMBER_OF_COLUMNS - 1][NUMBER_OF_ROWS - 2].getNumber();
                sum += board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 2].getNumber();
                sum += board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 1].getNumber();
                return sum % 10;
            case "BOTTOM_LEFT":
                sum += board[0][NUMBER_OF_ROWS - 2].getNumber();
                sum += board[1][NUMBER_OF_ROWS - 2].getNumber();
                sum += board[1][NUMBER_OF_ROWS - 1].getNumber();
                return sum % 10;
            case "TOP_RIGHT":
                sum += board[NUMBER_OF_COLUMNS - 2][0].getNumber();
                sum += board[NUMBER_OF_COLUMNS - 2][1].getNumber();
                sum += board[NUMBER_OF_COLUMNS - 1][1].getNumber();
                return sum % 10;
            default:
                break;
        }
        return sum;
    }


    public int removeCornerTiles(int row, int col) {
        int removed = 0;
        String isCorner = isCornerSpace(row, col);
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
                if (board[NUMBER_OF_COLUMNS - 1][NUMBER_OF_ROWS - 2].isOccupied()) {
                    board[NUMBER_OF_COLUMNS - 1][NUMBER_OF_ROWS - 2].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 2].isOccupied()) {
                    board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 2].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 1].isOccupied()) {
                    board[NUMBER_OF_COLUMNS - 2][NUMBER_OF_ROWS - 1].emptyTile();
                    removed++;
                }
                break;
            case "BOTTOM_LEFT":
                if (board[0][NUMBER_OF_ROWS - 2].isOccupied()) {
                    board[0][NUMBER_OF_ROWS - 2].emptyTile();
                    removed++;
                }
                if (board[1][NUMBER_OF_ROWS - 2].isOccupied()) {
                    board[1][NUMBER_OF_ROWS - 2].emptyTile();
                    removed++;
                }
                if (board[1][NUMBER_OF_ROWS - 1].isOccupied()) {
                    board[1][NUMBER_OF_ROWS - 1].emptyTile();
                    removed++;
                }
                break;
            case "TOP_RIGHT":
                if (board[NUMBER_OF_COLUMNS - 2][0].isOccupied()) {
                    board[NUMBER_OF_COLUMNS - 2][0].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_COLUMNS - 2][1].isOccupied()) {
                    board[NUMBER_OF_COLUMNS - 2][1].emptyTile();
                    removed++;
                }
                if (board[NUMBER_OF_COLUMNS - 1][1].isOccupied()) {
                    board[NUMBER_OF_COLUMNS - 1][1].emptyTile();
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

    private boolean isModulo(int col, int row, int surroundingTileSummation) {
        return board[col][row].getNumber() == surroundingTileSummation;
    }

    public int getScore() {
        return score;
    }
}





