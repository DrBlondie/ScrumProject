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
        notifyObservers();
    }

    public boolean isOccupied(int col, int row) {
        return board[col][row].isOccupied();
    }


    boolean performMove(int col, int row) {

        int surroundingTileSummation;
        if (board[col][row].isOccupied()) {
            return false;
        }
        if (!isCornerSpace(col, row).equals("FALSE")) {
            surroundingTileSummation = calculateCornerPoints(col, row);
            board[col][row].setNumber(currentQueue.placeTile());
            board[col][row].setOccupied(true);
            if (isModulo(col, row, surroundingTileSummation)) {
                board[col][row].emptyTile();
                score += removeCornerTiles(col, row);
            }
        } else if (!isBorderSpace(col, row).equals("FALSE")) {
            surroundingTileSummation = calculateBorderPoints(col, row);
            board[col][row].setNumber(currentQueue.placeTile());
            board[col][row].setOccupied(true);
            if (isModulo(col, row, surroundingTileSummation)) {
                board[col][row].emptyTile();
                score += removeBorderTiles(col, row);
            }

        }else{
            surroundingTileSummation = calculateInteriorPoints(col, row);
            board[col][row].setNumber(currentQueue.placeTile());
            board[col][row].setOccupied(true);
            if(isModulo(col, row, surroundingTileSummation)){
                board[col][row].emptyTile();
                score += removeInteriorTiles(col, row);
            }
        }
        setChanged();
        notifyObservers();
        return true;
    }

    private String isCornerSpace(int col, int row) {
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

    private String isBorderSpace(int col, int row) {
        if (col == 0) {
            return "LEFT_BORDER";
        }
        if (col == NUMBER_OF_COLUMNS - 1) {
            return "RIGHT_BORDER";
        }
        if (row == 0) {
            return "TOP_BORDER";
        }
        if (row == NUMBER_OF_ROWS - 1) {
            return "BOTTOM_BORDER";
        }
        return "FALSE";

    }

    private int calculateBorderPoints(int row, int col) {
        int sum = 0;
        String isBorderSpace = isBorderSpace(col, row);
        switch (isBorderSpace) {
            default:
                break;
            case "TOP_BORDER":
                sum += board[row][col - 1].getNumber();
                sum += board[row][col + 1].getNumber();
                sum += board[row + 1][col - 1].getNumber();
                sum += board[row + 1][col].getNumber();
                sum += board[row + 1][col + 1].getNumber();
                return sum;
            case "BOTTOM_BORDER":
                sum += board[row][col - 1].getNumber();
                sum += board[row][col + 1].getNumber();
                sum += board[row - 1][col - 1].getNumber();
                sum += board[row - 1][col].getNumber();
                sum += board[row - 1][col + 1].getNumber();
                return sum;
            case "LEFT_BORDER":
                sum += board[row - 1][col].getNumber();
                sum += board[row + 1][col].getNumber();
                sum += board[row - 1][col + 1].getNumber();
                sum += board[row + 1][col + 1].getNumber();
                sum += board[row][col + 1].getNumber();
                return sum;
            case "RIGHT_BORDER":
                sum += board[row - 1][col].getNumber();
                sum += board[row + 1][col].getNumber();
                sum += board[row - 1][col - 1].getNumber();
                sum += board[row + 1][col - 1].getNumber();
                sum += board[row][col - 1].getNumber();
        }
        return sum;

    }

    private int calculateCornerPoints(int col, int row) {
        int sum = 0;
        switch (isCornerSpace(col, row)) {
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

    private int calculateInteriorPoints(int row, int col){
        int sum = 0;

        sum += board[row - 1][col - 1].getNumber();
        sum += board[row - 1][col].getNumber();
        sum += board[row - 1][col + 1].getNumber();
        sum += board[row][col - 1].getNumber();
        sum += board[row][col + 1].getNumber();
        sum += board[row + 1][col - 1].getNumber();
        sum += board[row + 1][col].getNumber();
        sum += board[row + 1][col + 1].getNumber();

        return sum;
    }

    private int removeInteriorTiles(int row, int col){
        int removed = 0;
        if (board[row - 1][col - 1].isOccupied()) {
            board[row - 1][col - 1].emptyTile();
            removed++;
        }
        if (board[row - 1][col].isOccupied()) {
            board[row - 1][col].emptyTile();
            removed++;
        }
        if (board[row - 1][col + 1].isOccupied()) {
            board[row - 1][col + 1].emptyTile();
            removed++;
        }
        if (board[row][col - 1].isOccupied()) {
            board[row][col - 1].emptyTile();
            removed++;
        }
        if (board[row][col + 1].isOccupied()) {
            board[row][col + 1].emptyTile();
            removed++;
        }
        if (board[row + 1][col - 1].isOccupied()) {
            board[row + 1][col - 1].emptyTile();
            removed++;
        }
        if (board[row + 1][col].isOccupied()) {
            board[row + 1][col].emptyTile();
            removed++;
        }
        if (board[row + 1][col + 1].isOccupied()) {
            board[row + 1][col + 1].emptyTile();
            removed++;
        }

        if(removed >= 3){
            return removed;
        }else{
            return 0;
        }
    }

    private int removeBorderTiles(int row, int col) {
        int removed = 0;
        switch (isBorderSpace(col, row)) {
            default:
                break;

            case "TOP_BORDER":
                if (board[row][col - 1].isOccupied()) {
                    board[row][col - 1].emptyTile();
                    removed++;
                }
                if (board[row][col + 1].isOccupied()) {
                    board[row][col + 1].emptyTile();
                    removed++;
                }
                if (board[row + 1][col - 1].isOccupied()) {
                    board[row + 1][col - 1].emptyTile();
                    removed++;
                }
                if (board[row + 1][col].isOccupied()) {
                    board[row + 1][col].emptyTile();
                    removed++;
                }
                if (board[row + 1][col + 1].isOccupied()) {
                    board[row + 1][col + 1].emptyTile();
                    removed++;
                }
                break;
            case "BOTTOM_BORDER":
                if (board[row][col - 1].isOccupied()) {
                    board[row][col - 1].emptyTile();
                    removed++;
                }
                if (board[row][col + 1].isOccupied()) {
                    board[row][col + 1].emptyTile();
                    removed++;
                }
                if (board[row - 1][col - 1].isOccupied()) {
                    board[row - 1][col - 1].emptyTile();
                    removed++;
                }
                if (board[row - 1][col].isOccupied()) {
                    board[row - 1][col].emptyTile();
                    removed++;
                }
                if (board[row - 1][col + 1].isOccupied()) {
                    board[row - 1][col + 1].emptyTile();
                    removed++;
                }
                break;
            case "LEFT_BORDER":
                if (board[row - 1][col].isOccupied()) {
                    board[row - 1][col].emptyTile();
                    removed++;
                }
                if (board[row + 1][col].isOccupied()) {
                    board[row + 1][col].emptyTile();
                    removed++;
                }
                if (board[row][col + 1].isOccupied()) {
                    board[row][col + 1].emptyTile();
                    removed++;
                }
                if (board[row - 1][col + 1].isOccupied()) {
                    board[row - 1][col + 1].emptyTile();
                    removed++;
                }
                if (board[row + 1][col + 1].isOccupied()) {
                    board[row + 1][col + 1].emptyTile();
                    removed++;
                }
                break;
            case "RIGHT_BORDER":
                if (board[row - 1][col].isOccupied()) {
                    board[row - 1][col].emptyTile();
                    removed++;
                }
                if (board[row + 1][col].isOccupied()) {
                    board[row + 1][col].emptyTile();
                    removed++;
                }
                if (board[row][col - 1].isOccupied()) {
                    board[row][col - 1].emptyTile();
                    removed++;
                }
                if (board[row - 1][col - 1].isOccupied()) {
                    board[row - 1][col - 1].emptyTile();
                    removed++;
                }
                if (board[row + 1][col - 1].isOccupied()) {
                    board[row + 1][col - 1].emptyTile();
                    removed++;
                }
                break;
        }

        if(removed >= 3){
            return removed;
        } else {
            return 0;
        }
    }

    private int removeCornerTiles(int col, int row) {
        int removed = 0;
        switch (isCornerSpace(row, col)) {
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

    private boolean isModulo(int row, int col, int surroundingTileSummation) {
        return board[row][col].getNumber() == (surroundingTileSummation % 10);
    }

    public int getScore() {
        return score;
    }

    public Tile[][] getBoard() {
        return board;
    }


    public abstract String getGameValue();

    public abstract boolean checkMove(int col, int row);
}





