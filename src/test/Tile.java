package test;


public class Tile{

    private int number;
    private boolean isOccupied;

    Tile() {

        isOccupied = true;
        number = getRandomNumber();
    }

    private int getRandomNumber() {
        return (int) (Math.random() * 10);
    }

    void emptyTile() {
        number = 0;
        isOccupied = false;
    }

    public int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    void setOccupied(boolean value) {
        isOccupied = value;
    }


}
