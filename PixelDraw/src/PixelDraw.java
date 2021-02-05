import javax.swing.JFrame;
import java.awt.Dimension;

public class PixelDraw extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thiago's PixelDraw App");
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PixelPanel());
        frame.setSize(new Dimension(600, 400));
        frame.setVisible(true);
    }
}
