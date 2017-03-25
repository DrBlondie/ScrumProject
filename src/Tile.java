
public class Tile {

    private int number;

    public Tile() {
        number = getTileValue();
    }

    public int getTileValue() {
        return (int) (Math.random() * 10);
    }

    public int getNumber() {
        return value;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
