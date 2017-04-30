package model;

import main.Main;

public class UntimedGame extends Game {
    private int numberOfMoves = 0;
    private static UntimedGame instance = null;

    public static Game getInstance() {
        if (instance == null) {
            instance = new UntimedGame();
        }
        return instance;
    }

    private UntimedGame() {
        super(new TileQueue(false));
    }

    @Override
    public void newGame() {
        numberOfMoves = 0;
        restartGame();
    }

    @Override
    public String getGameValue() {
        return "Moves left: " + (Main.MAX_MOVES - numberOfMoves);
    }

    @Override
    public boolean checkMove(int col, int row) {
        if (numberOfMoves >= Main.MAX_MOVES || board[col][row].isOccupied()) {
            return false;
        }
        performMove(col, row);
        numberOfMoves++;
        setChanged();
        notifyObservers(getGameValue());
        return true;

    }

    @Override
    public boolean gameOver() {
        return numberOfMoves >= Main.MAX_MOVES;
    }
}
