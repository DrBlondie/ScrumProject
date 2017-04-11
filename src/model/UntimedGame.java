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
            return performMove(col, row);

        }
    }
}
