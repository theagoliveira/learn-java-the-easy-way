import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

public class PixelPanel extends JPanel {
    Random rand = new Random();
    ArrayList<Bubble> bubbleList;
    int size = 25;
    int xoff = 8;
    int yoff = 34;
    int goff = 100;

    public PixelPanel() {
        bubbleList = new ArrayList<Bubble>();
        setBackground(Color.BLACK);
        addMouseListener(new BubbleListener());
        addMouseMotionListener(new BubbleListener());
        addMouseWheelListener(new BubbleListener());
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

        public Bubble(int newX, int newY, int newSize) {
            x = (newX / newSize) * newSize + newSize / 2;
            y = (newY / newSize) * newSize + newSize / 2;
            size = newSize;
            color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }

        public void draw(Graphics canvas) {
            canvas.setColor(color);
            canvas.fillRect(x - size / 2, y - size / 2, size, size);
        }
    }

    private class BubbleListener extends MouseAdapter {
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
    }
}
