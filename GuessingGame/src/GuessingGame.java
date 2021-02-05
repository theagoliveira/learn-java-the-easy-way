import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuessingGame extends JFrame {
    private JTextField txtGuess;
    private JLabel lblOutput;
    private JButton btnPlayAgain;
    private JButton btnGuess;
    private int theNumber;
    private int numberOfTries;
    private int triesLimit;

    public void checkGuess() {
        String guessText = txtGuess.getText();
        String message = "";
        String triesWordWrong = "tries";
        String triesWordCorrect = "tries";
        Boolean end = false;
        try {
            int guess = Integer.parseInt(guessText);
            numberOfTries = numberOfTries + 1;
            triesLimit = triesLimit - 1;
            if (triesLimit == 1)
                triesWordWrong = "try";
            if (numberOfTries == 1)
                triesWordCorrect = "try";
            if (triesLimit > 0) {
                if (guess < theNumber) {
                    message = guess + " is too low. Try again. You have " + triesLimit + " " + triesWordWrong
                            + " remaining.";
                } else if (guess > theNumber) {
                    message = guess + " is too high. Try again. You have " + triesLimit + " " + triesWordWrong
                            + " remaining.";
                } else {
                    message = guess + " is correct. You win after " + numberOfTries + " " + triesWordCorrect + "!";
                    end = true;
                }
            } else {
                if (guess != theNumber) {
                    message = "You lost! The correct number was " + theNumber + ".";
                    end = true;
                } else {
                    message = guess + " is correct. You win after " + numberOfTries + " " + triesWordCorrect + "!";
                    end = true;
                }
            }
        } catch (Exception e) {
            message = "Enter a whole number between 1 and 100.";
        } finally {
            lblOutput.setText(message);
            if (Boolean.TRUE.equals(end)) {
                btnPlayAgain.setVisible(true);
                txtGuess.setEnabled(false);
                btnGuess.setVisible(false);
                end = false;
            } else {
                txtGuess.requestFocus();
                txtGuess.selectAll();
            }
        }
    }

    public void newGame() {
        theNumber = (int) (Math.random() * 100 + 1);
        numberOfTries = 0;
        triesLimit = 7;
        btnPlayAgain.setVisible(false);
        btnGuess.setVisible(true);
        lblOutput.setText("Enter a number above and click Guess!");
        txtGuess.setText("");
        txtGuess.setEnabled(true);
        txtGuess.requestFocus();
    }

    public GuessingGame() {
        getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Thiago's Hi-Lo Guessing Game");
        getContentPane().setLayout(null);

        JLabel lblThiagosHiloGuessing = new JLabel("Thiago's Hi-Lo Guessing Game");
        lblThiagosHiloGuessing.setFont(new Font("Arial", Font.BOLD, 15));
        lblThiagosHiloGuessing.setHorizontalAlignment(SwingConstants.CENTER);
        lblThiagosHiloGuessing.setBounds(12, 38, 418, 18);
        getContentPane().add(lblThiagosHiloGuessing);

        JLabel lblGuessANumber = new JLabel("Guess a number between 1 and 100:");
        lblGuessANumber.setHorizontalAlignment(SwingConstants.RIGHT);
        lblGuessANumber.setFont(new Font("Arial", Font.PLAIN, 12));
        lblGuessANumber.setBounds(90, 94, 204, 15);
        getContentPane().add(lblGuessANumber);

        txtGuess = new JTextField();
        txtGuess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        txtGuess.setFont(new Font("Arial", Font.PLAIN, 12));
        txtGuess.setBounds(296, 92, 55, 19);
        getContentPane().add(txtGuess);
        txtGuess.setColumns(10);

        btnGuess = new JButton("Guess!");
        btnGuess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        btnGuess.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuess.setBounds(162, 147, 117, 25);
        getContentPane().add(btnGuess);

        lblOutput = new JLabel("Enter a number above and click Guess!");
        lblOutput.setFont(new Font("Arial", Font.PLAIN, 12));
        lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
        lblOutput.setBounds(12, 210, 418, 15);
        getContentPane().add(lblOutput);

        btnPlayAgain = new JButton("Play Again");
        btnPlayAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        btnPlayAgain.setFont(new Font("Arial", Font.BOLD, 12));
        btnPlayAgain.setBounds(162, 147, 117, 25);
        getContentPane().add(btnPlayAgain);
        btnPlayAgain.setVisible(false);
    }

    public static void main(String[] args) {
        GuessingGame theGame = new GuessingGame();
        theGame.newGame();
        theGame.setSize(new Dimension(450, 300));
        theGame.setVisible(true);
    }
}
