import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class MadLibsGUI extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = -8971099821278920495L;
    private static final String ARIAL_FONT = "Arial";
    private JTextField txtAdjective;
    private JTextField txtVerb;
    private JTextField txtColor;
    private JTextField txtNoun;
    private JTextArea textArea;

    public void display() {
        String color = txtColor.getText();
        String verb = txtVerb.getText();
        String adjective = txtAdjective.getText();
        String noun = txtNoun.getText();

        if (color.isEmpty() || verb.isEmpty() || adjective.isEmpty() || noun.isEmpty()) {
            textArea.setText("Fill every text field before pressing the button.");
        } else {
            textArea.setText("The " + color + " dragon " + verb + " at the " + adjective + " " + noun
                    + ".\nAnd everyone lived happily ever after.\nThe end.");
        }
    }

    public void begin() {
        textArea.setText("");
    }

    public MadLibsGUI() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        setTitle("Thiago's Wacky MadLibs App");
        getContentPane().setLayout(null);

        JLabel lblThiagosWackyMadlibs = new JLabel("Thiago's Wacky MadLibs App");
        lblThiagosWackyMadlibs.setHorizontalAlignment(SwingConstants.CENTER);
        lblThiagosWackyMadlibs.setFont(new Font(ARIAL_FONT, Font.BOLD, 15));
        lblThiagosWackyMadlibs.setBounds(12, 23, 418, 15);
        getContentPane().add(lblThiagosWackyMadlibs);

        JLabel lblEnterAndAdjective = new JLabel("Enter and Adjective:");
        lblEnterAndAdjective.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterAndAdjective.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        lblEnterAndAdjective.setBounds(31, 61, 127, 15);
        getContentPane().add(lblEnterAndAdjective);

        JLabel lblEnterAVerb = new JLabel("Enter a Verb Ending in -ed:");
        lblEnterAVerb.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterAVerb.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        lblEnterAVerb.setBounds(12, 99, 146, 15);
        getContentPane().add(lblEnterAVerb);

        JLabel lblEnterAColor = new JLabel("Enter a Color:");
        lblEnterAColor.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterAColor.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        lblEnterAColor.setBounds(247, 61, 83, 15);
        getContentPane().add(lblEnterAColor);

        JLabel lblEnterANoun = new JLabel("Enter a Noun:");
        lblEnterANoun.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterANoun.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        lblEnterANoun.setBounds(242, 99, 88, 15);
        getContentPane().add(lblEnterANoun);

        txtAdjective = new JTextField();
        txtAdjective.addActionListener(e -> display());
        txtAdjective.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        txtAdjective.setBounds(162, 59, 73, 19);
        getContentPane().add(txtAdjective);
        txtAdjective.setColumns(10);

        txtVerb = new JTextField();
        txtVerb.addActionListener(e -> display());
        txtVerb.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        txtVerb.setBounds(162, 97, 73, 19);
        getContentPane().add(txtVerb);
        txtVerb.setColumns(10);

        txtColor = new JTextField();
        txtColor.addActionListener(e -> display());
        txtColor.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        txtColor.setBounds(334, 59, 73, 19);
        getContentPane().add(txtColor);
        txtColor.setColumns(10);

        txtNoun = new JTextField();
        txtNoun.addActionListener(e -> display());
        txtNoun.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        txtNoun.setBounds(334, 97, 73, 19);
        getContentPane().add(txtNoun);
        txtNoun.setColumns(10);

        JButton btnPressHereTo = new JButton("Press Here to View Your MadLibs Creation!");
        btnPressHereTo.addActionListener(e -> display());
        btnPressHereTo.setFont(new Font(ARIAL_FONT, Font.BOLD, 12));
        btnPressHereTo.setBounds(82, 137, 277, 25);
        getContentPane().add(btnPressHereTo);

        textArea = new JTextArea();
        textArea.setBounds(12, 185, 418, 69);
        getContentPane().add(textArea);
    }

    public static void main(String[] args) {
        MadLibsGUI madLibs = new MadLibsGUI();
        madLibs.begin();
        madLibs.setSize(new Dimension(450, 300));
        madLibs.setVisible(true);
    }
}
