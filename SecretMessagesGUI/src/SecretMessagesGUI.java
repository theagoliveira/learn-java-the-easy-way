import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SecretMessagesGUI extends JFrame {
    private JTextField txtKey;
    private JTextArea txtIn;
    private JTextArea txtOut;
    private JSlider slider;
    private JButton btnMoveUp;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;

    public String encode(String message, int keyVal) {
        StringBuilder bld = new StringBuilder();

        char key = (char) keyVal;
        for (int i = message.length() - 1; i >= 0; i--) {
            char input = message.charAt(i);
            if (input >= 'A' && input <= 'Z') {
                input += key;
                if (input > 'Z')
                    input -= 26;
                if (input < 'A')
                    input += 26;
            } else if (input >= 'a' && input <= 'z') {
                input += key;
                if (input > 'z')
                    input -= 26;
                if (input < 'a')
                    input += 26;
            } else if (input >= '0' && input <= '9') {
                input += (keyVal % 10);
                if (input > '9')
                    input -= 10;
                if (input < '0')
                    input += 10;
            }
            bld.append(input);
        }

        return bld.toString();
    }

    public SecretMessagesGUI() {
        getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
        getContentPane().setBackground(new Color(250, 128, 114));
        setTitle("Thiago's Secret Message App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 12, 568, 140);
        getContentPane().add(scrollPane);

        txtIn = new JTextArea();
        scrollPane.setViewportView(txtIn);
        txtIn.setWrapStyleWord(true);
        txtIn.setFont(new Font("Arial", Font.PLAIN, 18));
        txtIn.setLineWrap(true);

        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(12, 214, 568, 140);
        getContentPane().add(scrollPane_1);

        txtOut = new JTextArea();
        scrollPane_1.setViewportView(txtOut);
        txtOut.setWrapStyleWord(true);
        txtOut.setFont(new Font("Arial", Font.PLAIN, 18));
        txtOut.setLineWrap(true);

        JLabel lblKey = new JLabel("Key:");
        lblKey.setFont(new Font("Arial", Font.BOLD, 12));
        lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
        lblKey.setBounds(248, 173, 32, 20);
        getContentPane().add(lblKey);

        txtKey = new JTextField();
        txtKey.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int key = Integer.parseInt(txtKey.getText()) % 26;
                    slider.setValue(key);
                } catch (Exception ex) {
                }
            }
        });
        txtKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String output;

                try {
                    String message = txtIn.getText();
                    int key = Integer.parseInt(txtKey.getText()) % 26;
                    output = encode(message, key);
                    txtOut.setText(output);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a whole number value for the encryption key.");
                    txtKey.requestFocus();
                    txtKey.selectAll();
                }
            }
        });
        txtKey.setFont(new Font("Arial", Font.PLAIN, 12));
        txtKey.setHorizontalAlignment(SwingConstants.CENTER);
        txtKey.setText("15");
        txtKey.setBounds(288, 173, 32, 20);
        getContentPane().add(txtKey);
        txtKey.setColumns(10);

        JButton btnEncode = new JButton("Encode/Decode");
        btnEncode.setBackground(new Color(233, 150, 122));
        btnEncode.setFont(new Font("Arial", Font.BOLD, 12));
        btnEncode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String message = txtIn.getText();
                    int key = Integer.parseInt(txtKey.getText()) % 26;
                    String output = encode(message, key);
                    txtOut.setText(output);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a whole number value for the encryption key.");
                    txtKey.requestFocus();
                    txtKey.selectAll();
                }
            }
        });
        btnEncode.setBounds(341, 170, 127, 25);
        getContentPane().add(btnEncode);

        slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                txtKey.setText("" + slider.getValue());
                String message = txtIn.getText();
                int key = slider.getValue();
                String output = encode(message, key);
                txtOut.setText(output);
            }
        });
        slider.setValue(15);
        slider.setSnapToTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMinimum(-25);
        slider.setMaximum(25);
        slider.setBackground(new Color(250, 128, 114));
        slider.setFont(new Font("Arial", Font.BOLD, 12));
        slider.setBounds(12, 164, 218, 38);
        getContentPane().add(slider);

        btnMoveUp = new JButton("Move Up");
        btnMoveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtIn.setText(txtOut.getText());
                slider.setValue(-slider.getValue());
            }
        });
        btnMoveUp.setFont(new Font("Arial", Font.BOLD, 12));
        btnMoveUp.setBackground(new Color(233, 150, 122));
        btnMoveUp.setBounds(480, 170, 100, 25);
        getContentPane().add(btnMoveUp);
    }

    public static void main(String[] args) {
        SecretMessagesGUI theApp = new SecretMessagesGUI();
        theApp.setSize(new java.awt.Dimension(600, 400));
        theApp.setVisible(true);
    }
}
