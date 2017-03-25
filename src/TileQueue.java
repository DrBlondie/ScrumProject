import java.util.ArrayList;

public class TileQueue {

    private static ArrayList<Tile> queue = new ArrayList<>();
    private final int MAX_SIZE = 5;
    private int head;
    private int tail;
    private int placedTiles;

    public TileQueue(){

        for(int i = 0; i < MAX_SIZE; i++){
            queue.add(new Tile(-1,-1,Main.getNewTextField()));
        }

        head = 0;
        tail = MAX_SIZE - 1;
        placedTiles = 0;

    }

    private Tile dequeue(){

        if(queue.size() > 0){
            placedTiles++;
            Tile nextTile = queue.get(head);
            queue.remove(head);
            return nextTile;
        }
        throw new Error("There are not sufficent Tiles in the queue");

    }

    private void enqueue(){
        queue.add(new Tile(-1,-1, Main.getNewTextField()));
    }

    public ArrayList<Tile> getQueue(){
        return queue;
    }

    public Tile placeTile(){
        enqueue();
        return dequeue();
    }


}