import javax.swing.*;

public class Tile{

    private JTextField textField;
    private int number;
    private boolean isOccupied;

    public Tile(JTextField _field) {
        textField = _field;
        isOccupied = true;
        number = getRandomNumber();
        textField.setText(number + "");
    }

    public JTextField getTextField(){
        return textField;
    }

    public int getRandomNumber() {
        return (int) (Math.random() * 10);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.textField.setText(number + "");
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean value) {
        isOccupied = value;
    }

    public void emptyTile() {
        number = 0;
        textField.setText("");
        isOccupied = false;
    }
}
