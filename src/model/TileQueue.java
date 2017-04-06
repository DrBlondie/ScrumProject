package model;

import java.util.ArrayList;
import java.util.Observable;

import main.Main;


public class TileQueue extends Observable{

    private static final int MAX_SIZE = 5;
    private static TileQueue gameQueue;
    private static ArrayList<Integer> numberQueue = new ArrayList<>();
    private int placedTileCount;

    private TileQueue() {

        for (int i = 0; i < MAX_SIZE; i++) {

            numberQueue.add((int) (Math.random() * 10));
        }
        placedTileCount = 0;
    }

    public void startGame() {
        setChanged();
        notifyObservers();
    }

    private int dequeue() {

        if (numberQueue.size() > 0) {
            placedTileCount++;
            int nextTile = numberQueue.get(0);
            numberQueue.remove(0);
            return nextTile;

        }


        throw new Error("There are not sufficient Tiles in the queue");

    }

    private void enqueue() {
        numberQueue.add((int) (Math.random() * 10));
    }

    int placeTile() {

        if (placedTileCount < Main.MAX_MOVES - MAX_SIZE) {
            enqueue();
        }

        int value = dequeue();
        setChanged();
        notifyObservers();
        return value;
    }

    public static TileQueue getTileQueue() {
        if (gameQueue == null) {
            gameQueue = new TileQueue();
        }
        return gameQueue;
    }


    public ArrayList<Integer> getQueue() {
        return numberQueue;
    }

}