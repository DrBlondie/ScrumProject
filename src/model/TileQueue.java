package model;

import java.util.ArrayList;
import java.util.Observable;

import main.Main;


public class TileQueue extends Observable{

    private static final int MAX_SIZE = 5;
    private ArrayList<Integer> numberQueue = new ArrayList<>();
    private int placedTileCount;

    public TileQueue() {

        for (int i = 0; i < MAX_SIZE; i++) {

            numberQueue.add((int) (Math.random() * 10));
        }
        placedTileCount = 0;
    }

    public void updateGame() {
        setChanged();
        notifyObservers(numberQueue);
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

}