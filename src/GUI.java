import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GUI extends JFrame
{
    protected TimerThread timerThread;
    private JPanel mainPanel = new JPanel();
    private Rectangle2D[][] boxes = new Rectangle2D[9][9];
    private ArrayList<Shape> guideLines = new ArrayList<>();

    public GUI(){
        setTitle("Sum Fun");
        add(mainPanel, BorderLayout.NORTH);
        setSize(765, 500);
        setMinimumSize(new Dimension(765, 350));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel gameBoard = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridheight = 1;

        c.gridx = 0;
        c.gridwidth = 2;
        setBoxes();
        setLines();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        for (Rectangle2D[] a : boxes) {
            for (Rectangle2D q : a) {
                g2.draw(q);
            }
        }
        for (Shape el : guideLines) {
            g2.draw(el);
        }

    }

    public void setBoxes() {
        for (int x = 0; x < 800; x += 100) {
            for (int i = 0; i < 800; i += 100) {
                boxes[x / 100][i / 100] = new Rectangle2D.Double(i, x, 100, 100);
            }
        }

    }
    public void setLines() {
        for (int i = 0; i < 800; i += 100) {
            guideLines.add(new Line2D.Double(i, 0, i, 800));
            guideLines.add(new Line2D.Double(0, i, 800, i));
        }
    }


    public class TimerThread extends Thread {

        protected boolean isRunning;

        protected JLabel dateLabel;
        protected JLabel timeLabel;

        protected SimpleDateFormat dateFormat =
                new SimpleDateFormat("M/d/YY");
        protected SimpleDateFormat timeFormat =
                new SimpleDateFormat("h:mm:ss");

        public TimerThread(JLabel dateLabel, JLabel timeLabel) {
            this.dateLabel = dateLabel;
            this.timeLabel = timeLabel;
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
                        dateLabel.setText(dateFormat.format(currentTime));
                        timeLabel.setText(timeFormat.format(currentTime));
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