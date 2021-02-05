package com.example.guessinggameapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private TextView lblOutput;
    private TextView lblRange;
    private int theNumber;
    private int numberOfTries;
    private int triesLimit;
    private int range = 100;

    public void checkGuess() {
        String guessText = txtGuess.getText().toString();
        String message = "";
        int guess = range/2;
        numberOfTries++;
        triesLimit--;
        try {
            guess = Integer.parseInt(guessText);
            if (triesLimit > 0) {
                if (guess < theNumber) {
                    message = guess + " is too low. Try again.\nYou have " + triesLimit + " tries.";
                } else if (guess > theNumber) {
                    message = guess + " is too high. Try again.\nYou have " + triesLimit + " tries.";
                } else {
                    message = guess + " is correct. You win after " + numberOfTries + " tries!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    int gamesWon = sharedPreferences.getInt("gamesWon", 0) + 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("gamesWon", gamesWon);
                    editor.apply();
                    newGame();
                }
            } else {
                if (guess == theNumber) {
                    message = guess + " is correct. You win after " + numberOfTries + " tries!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    int gamesWon = sharedPreferences.getInt("gamesWon", 0) + 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("gamesWon", gamesWon);
                    editor.apply();
                    newGame();
                } else {
                    message = "You lose! The correct number was " + theNumber + ".";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    int gamesLost = sharedPreferences.getInt("gamesLost", 0) + 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("gamesLost", gamesLost);
                    editor.apply();
                    newGame();
                }
            }
        } catch (Exception e) {
            message = "Enter a whole number between 1 and " + range + ".";
        } finally {
            lblOutput.setText(message);
            txtGuess.setText("" + guess);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    public void newGame() {
        theNumber = (int) (Math.random() * range + 1);
        lblRange.setText("Enter a number between 1 and " + range + ".");
        txtGuess.setText("" + range/2);
        txtGuess.requestFocus();
        txtGuess.selectAll();
        numberOfTries = 0;
        triesLimit = (int) (Math.log(range) / Math.log(2) + 1);
        lblOutput.setText("Enter a number, then click Guess!\nYou have " + triesLimit + " tries.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGuess = (EditText) findViewById(R.id.txtGuess);
        Button btnGuess = (Button) findViewById(R.id.btnGuess);
        lblOutput = (TextView) findViewById(R.id.lblOutput);
        lblRange = (TextView) findViewById(R.id.lblRange);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        range = sharedPreferences.getInt("range", 100);
        newGame();
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return true;
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the Range:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                range = 10;
                                storeRange(10);
                                newGame();
                                break;
                            case 1:
                                range = 100;
                                storeRange(100);
                                newGame();
                                break;
                            case 2:
                                range = 1000;
                                storeRange(1000);
                                newGame();
                                break;
                        }
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = sharedPreferences.getInt("gamesWon", 0);
                int gamesLost = sharedPreferences.getInt("gamesLost", 0);
                int gamesTotal = gamesWon + gamesLost;
                int percent;
                if (gamesTotal != 0) {
                    percent = 100 * gamesWon / gamesTotal;
                } else {
                    percent = 0;
                }
                AlertDialog statsDialog = new AlertDialog.Builder(MainActivity.this).create();
                statsDialog.setTitle("Guessing Game Stats");
                statsDialog.setMessage("You have won " + gamesWon + " out of " + gamesTotal + " games, " + percent + "%! Way to go!");
                statsDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                statsDialog.show();
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c)2021 Thiago Cavalcante.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void storeRange(int newRange) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }
}