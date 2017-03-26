import javax.swing.*;

public class Tile{

    private JTextField textField;
    private int number;

    public Tile(JTextField _field) {
        textField = _field;
        number = getTileValue();
        textField.setText(number + "");
    }

    public JTextField getTextField(){
        return textField;
    }

    public int getTileValue() {
        return (int) (Math.random() * 10);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.textField.setText(number + "");
    }

    public void emptyTile() {
        number = 0;
        textField.setText("");

    }
}
