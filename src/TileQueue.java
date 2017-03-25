import java.util.*;
import Tile;

public class TileQueue{

    private static ArrayList<Integer> queue = new ArrayList<>();
    private final int MAX_SIZE = 5;
    private int head;
    private int tail;
    private int placedTiles;

    public TileQueue(){

        for(int i = 0; i < MAX_SIZE; i++){
            queue.add(new Tile());
        }

        head = 0;
        tail = MAX_SIZE - 1;
        placedTiles = 0;

    }

    private Tile dequeue(){

        if(size > 0){
            placedTiles++;
            Tile nextTile = queue.get(head);
            nextTile.remove(head);
            return nextTile;
        }
        throw new Error("There are not sufficent Tiles in the queue");

    }

    private void enqueue(){
        queue.add(new Tile());
    }

    public void getQueue(){
        return queue;
    }

    public Tile placeTile(){
        enqueue();
        return dequeue();
    }


}