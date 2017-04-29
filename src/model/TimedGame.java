package model;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import main.Main;


public class TimedGame extends Game {
    private int timeUsed = 0;
    private static TimedGame instance = null;

    public static Game getInstance() {
        if(instance == null) {
            instance = new TimedGame();
        }
        return instance;
    }
    private TimedGame() {
        super(new TileQueue(true));
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeUsed++;
                setChanged();
                notifyObservers(getGameValue());
            }
        }, Calendar.getInstance().getTime(), 1000L);
    }
    @Override
    public void newGame() {
        timeUsed = 0;
        restartGame();
    }

    @Override
    public String getGameValue() {
        String timeString;
        int timeLeft = Main.MAX_TIME - timeUsed;
        if (timeLeft >= 60) {
            timeString = (timeLeft / 60) + ":" + String.format("%02d", (timeLeft % 60));
        } else if (timeLeft < 60 && timeLeft > 0) {
            timeString = String.format("%02d", timeLeft);
        } else {
            timeString = "00";
        }
        return timeString;
    }

    @Override
    public boolean checkMove(int col, int row) {
        if (board[col][row].isOccupied()) {
            return false;
        }
        performMove(col, row);
        setChanged();
        notifyObservers(this.getBoard());
        return timeUsed < Main.MAX_TIME;
    }

    @Override
    public boolean gameOver() {
        return timeUsed >= Main.MAX_TIME;
    }

}

