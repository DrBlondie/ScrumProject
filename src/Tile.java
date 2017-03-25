import javax.swing.*;

public class Tile{

    private JTextField textField;
    private int number;
    private int x;
    private int y;

    public Tile(int _x, int _y, JTextField _field) {
        x = _x;
        y = _y;
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
        if(number == -1){
            textField.setText("");
        }else{
            textField.setText(number + "");
        }
    }
}
