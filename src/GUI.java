import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GUI extends JFrame {
    protected TimerThread timerThread;
    private JPanel playField = new JPanel();
    private JPanel queueBox = new JPanel();


    public GUI() {
        setTitle("Sum Fun");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildPlayField();
        buildQueueBox();

        JPanel header = new JPanel();
        JLabel label = new JLabel("Sum Fun");

        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(label, BorderLayout.CENTER);
        JLabel timer = new JLabel();
        header.add(timer, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());
        boardPanel.add(playField);
        boardPanel.add(queueBox);
        add(boardPanel,BorderLayout.CENTER);
        timerThread = new TimerThread(timer);
        timerThread.start();
    }


    public void buildPlayField() {
        playField.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 50;
        c.insets = new Insets(0, 0, 0, 0);
        Tile[][] tiles = Main.board.getBoard();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x ++) {

                c.gridx = x * 90;
                c.gridy = y;
                playField.add(tiles[x][y].getTextField(), c);
            }
        }
    }

    public void buildQueueBox(){

        queueBox.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 50;
        c.insets = new Insets(0, 0, 0, 0);
        ArrayList<Tile> queue = TileQueue.getTileQueue().getQueue();
        for (int i = 0; i < queue.size(); i ++) {
            Tile tile = queue.get(i);
            c.gridy = i;
            queueBox.add(tile.getTextField(), c);
        }
    }


    public class TimerThread extends Thread {

        protected boolean isRunning;

        protected JLabel timeLabel;
        private Date endTime;

        protected SimpleDateFormat dateFormat =
                new SimpleDateFormat("M/d/YY");
        protected SimpleDateFormat timeFormat =
                new SimpleDateFormat("h:mm:ss");

        public TimerThread(JLabel timeLabel) {
            this.timeLabel = timeLabel;
            endTime = Calendar.getInstance().getTime();
            endTime.setTime(endTime.getTime() + 180000);
            this.isRunning = true;
        }

        @Override
        public void run() {
            while (isRunning) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Calendar currentCalendar = Calendar.getInstance();
                        Date currentTime = currentCalendar.getTime();
                        long time = endTime.getTime() - currentTime.getTime();
                        int timeLeft = (int)time / 1000;
                        String t = (timeLeft/60) + ":" + (timeLeft%60);
                        timeLabel.setText(t + "");
                    }
                });

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            }
        }

        public void setRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

    }
}