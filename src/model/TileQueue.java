package model;

import java.util.ArrayList;
import java.util.Observable;

import main.Main;


public class TileQueue extends Observable{

    private static final int MAX_SIZE = 5;
    private ArrayList<Integer> numberQueue = new ArrayList<>();
    private int placedTileCount;
    private int rerollLeft;

    public TileQueue() {
        rerollLeft=1;
        for (int i = 0; i < MAX_SIZE; i++) {
            numberQueue.add((int) (Math.random() * 10));
        }

        placedTileCount = 0;

    }
    public void rerollQueue(){
        if(rerollLeft>0) {
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
        setChanged();
        notifyObservers(numberQueue);
        placedTileCount = 0;
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
        notifyObservers(numberQueue);
        return value;
    }

}