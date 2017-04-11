package model;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import main.Main;


public class TimedGame extends Game {
    private int timeUsed = 0;

    public TimedGame(TileQueue queue) {
        super(queue);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeUsed++;
                if (timeUsed > 300) {
                    t.cancel();
                }
                updateGame();
            }
        }, Calendar.getInstance().getTime(), 1000L);
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
        return timeUsed < Main.MAX_TIME && performMove(col, row);
    }
}
