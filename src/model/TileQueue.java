package model;

import java.util.ArrayList;
import java.util.Observable;

import main.Main;


public class TileQueue extends Observable {

    private static final int MAX_SIZE = 5;
    private ArrayList<Integer> numberQueue = new ArrayList<>();
    private int placedTileCount;
    private int rerollLeft;
    private boolean isTimed = false;

    TileQueue(boolean timed) {

        isTimed = timed;
        populateQueue();
        placedTileCount = 0;

    }

    private void populateQueue(){
        for (int i = 0; i < MAX_SIZE; i++) {
            numberQueue.add((int) (Math.random() * 10));
        }

    }

    public void rerollQueue() {
        if (rerollLeft > 0) {
            for (int i = 0; i < MAX_SIZE; i++) {
                numberQueue.remove(0);
                numberQueue.add((int) (Math.random() * 10));
            }
        }
        rerollLeft--;
        setChanged();
        notifyObservers(numberQueue);
    }

    public void updateGame() {
        placedTileCount = 0;
        rerollLeft = 1;
        numberQueue.clear();
        populateQueue();
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

        if (isTimed || placedTileCount < Main.MAX_MOVES - MAX_SIZE) {
            enqueue();
        }

        int value = dequeue();
        setChanged();
        notifyObservers(numberQueue);
        return value;
    }

    int getNext() {
        return numberQueue.get(0);
    }

    int getRemainingElements() { return numberQueue.size(); }
}