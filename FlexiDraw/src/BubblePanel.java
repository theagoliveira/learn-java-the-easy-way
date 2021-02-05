import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BubblePanel extends JPanel {
    Random rand = new Random();
    ArrayList<Bubble> bubbleList;
    int size = 25;
    Timer timer;
    int delay = 33;
    JSlider slider;

    public BubblePanel() {
        timer = new Timer(delay, new BubbleListener());
        bubbleList = new ArrayList<Bubble>();
        setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        add(panel);

        JButton btnPause = new JButton("Pause");
        btnPause.setFont(new Font("Arial", Font.BOLD, 10));
        btnPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if (btn.getText().equals("Pause")) {
                    timer.stop();
                    btn.setText("Start");
                } else {
                    timer.start();
                    btn.setText("Pause");
                }
            }
        });

        JLabel lblNewLabel = new JLabel("Animation Speed:");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 10));
        panel.add(lblNewLabel);

        slider = new JSlider();
        slider.setValue(30);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int speed = slider.getValue() + 1;
                int delay = 1000 / speed;
                timer.setDelay(delay);
            }
        });
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(5);
        slider.setMaximum(120);
        slider.setMajorTickSpacing(30);
        slider.setFont(new Font("Arial", Font.BOLD, 10));
        panel.add(slider);
        panel.add(btnPause);

        JButton btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial", Font.BOLD, 10));
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bubbleList = new ArrayList<Bubble>();
                repaint();
            }
        });
        panel.add(btnClear);
        addMouseListener(new BubbleListener());
        addMouseMotionListener(new BubbleListener());
        addMouseWheelListener(new BubbleListener());
        timer.start();
    }

    @Override
    public void paintComponent(Graphics canvas) {
        super.paintComponent(canvas);
        for (Bubble b : bubbleList) {
            b.draw(canvas);
        }
    }

    private class Bubble {
        private int x;
        private int y;
        private int size;
        private Color color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 5;

        public Bubble(int newX, int newY, int newSize) {
            x = newX;
            y = newY;
            size = newSize;
            color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256));
            xspeed = yspeed = 2;
        }

        public void draw(Graphics canvas) {
            canvas.setColor(color);
            canvas.fillOval(x - size / 2, y - size / 2, size, size);
        }

        public void update() {
            x += xspeed;
            y += yspeed;
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;
        }
    }

    private class BubbleListener extends MouseAdapter implements ActionListener {
        @Override
        public void mousePressed(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            size -= e.getUnitsToScroll();
            if (size < 3)
                size = 3;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Bubble b : bubbleList) {
                b.update();
            }
            repaint();
        }
    }
}
