package model;


public class Tile{

    private int number;
    private boolean isOccupied;

    public Tile() {
        isOccupied = true;
        number = getRandomNumber();
    }

    private int getRandomNumber() {
        return (int) (Math.random() * 10);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean value) {
        isOccupied = value;
    }

    public void emptyTile() {
        number = 0;
        isOccupied = false;
    }
}
