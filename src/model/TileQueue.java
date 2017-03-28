package model;

import main.Main;
import main.Tile;
import model.Board;
import java.util.ArrayList;
import java.util.Observable;

public class TileQueue extends Observable{

    private static final int MAX_SIZE = 5;
    private static TileQueue gameQueue;
    private static ArrayList<Integer> numberQueue = new ArrayList<>();

    private int head;
    private int placedTiles;

    private TileQueue() {

        for (int i = 0; i < 5; i++) {
            numberQueue.add((int) (Math.random() * 10));
        }
        head = 0;
        placedTiles = 0;
    }

    public static TileQueue getTileQueue() {
        if (gameQueue == null) {
            gameQueue = new TileQueue();
        }
        return gameQueue;
    }

    public void startGame() {
        setChanged();
        notifyObservers();
    }

    private int dequeue() {
        if (numberQueue.size() > 0) {
            placedTiles++;
            int nextTile = numberQueue.get(head);
            numberQueue.remove(head);
            return nextTile;
        }
        throw new Error("There are not sufficient Tiles in the queue");
    }

    private void enqueue() {
        numberQueue.add((int) (Math.random() * 10));
    }


    public ArrayList<Integer> getQueue() {
        return numberQueue;
    }

    public int placeTile() {
        if(placedTiles<Main.MAX_MOVES-5) {
            enqueue();
        }
        int dequeued = dequeue();
        setChanged();
        notifyObservers();
        return dequeued;
    }
}