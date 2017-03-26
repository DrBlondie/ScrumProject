import java.util.ArrayList;

public class TileQueue {

    private static final int MAX_SIZE = 5;
    private static TileQueue gameQueue;
    private static ArrayList<Integer> numberQueue = new ArrayList<>();
    private static Tile[] queue = new Tile[MAX_SIZE];
    private int head;
    private int tail;
    private int placedTiles;

    private TileQueue() {

        for(int i = 0; i < MAX_SIZE; i++){
            queue[i] = new Tile(Main.getNewTextField());
            numberQueue.add(queue[i].getNumber());
        }

        head = 0;
        tail = MAX_SIZE - 1;
        placedTiles = 0;

    }

    public static void updateQueue(){
        for(int i = 0; i < MAX_SIZE; i++){
            queue[i].setNumber(numberQueue.get(i));
        }
    }

    public static TileQueue getTileQueue() {
        if (gameQueue == null) {
            gameQueue = new TileQueue();
        }
        return gameQueue;
    }

    private int dequeue(){

        if(numberQueue.size() > 0){
            placedTiles++;
            int nextTile = numberQueue.get(head);
            numberQueue.remove(head);
            updateQueue();
            return nextTile;
        }
        throw new Error("There are not sufficient Tiles in the queue");

    }

    private void enqueue(){
        numberQueue.add((int) (Math.random() * 10));
    }


    public Tile[] getQueue() {
        return queue;
    }

    public int placeTile(){
        enqueue();
        return dequeue();
    }

    public int getPlacedTiles() {
        return placedTiles;
    }


}