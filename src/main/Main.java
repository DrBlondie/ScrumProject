package main;

import view.BoardView;


public class Main {

    public static final int MAX_MOVES = 50;
    public static final int MAX_TIME = 300;

    public static void main(String[] args) {
        BoardView gameBoardView = new BoardView();
        gameBoardView.newGame(false);
        gameBoardView.setVisible(true);
    }
}
