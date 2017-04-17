package model;

import main.Main;

public class UntimedGame extends Game {
    private int numberOfMoves = 0;

    public UntimedGame(TileQueue queue) {
        super(queue);
    }

    @Override
    public String getGameValue() {

        return "Moves left: " + (Main.MAX_MOVES - numberOfMoves);
    }

    @Override
    public boolean checkMove(int col, int row) {
        if (numberOfMoves >= Main.MAX_MOVES) {
            return false;
        } else {
            numberOfMoves++;
            boolean move = performMove(col,row);
            if(!move){
                numberOfMoves--;
            }
            setChanged();
            notifyObservers(this.getBoard());
            return move;

        }
    }

    @Override
    public boolean gameOver() {
        return numberOfMoves >= Main.MAX_MOVES;
    }
}
