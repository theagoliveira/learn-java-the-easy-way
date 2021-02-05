package com.example.secretmessagesapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText txtIn;
    EditText txtKey;
    EditText txtOut;
    SeekBar slider;
    Button btnEncode;
    Button btnMoveUp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtIn = (EditText)findViewById(R.id.txtIn);
        txtKey = (EditText)findViewById(R.id.txtKey);
        txtOut = (EditText)findViewById(R.id.txtOut);
        slider = (SeekBar)findViewById(R.id.slider);
        btnEncode = (Button)findViewById(R.id.btnEncode);
        btnMoveUp = (Button)findViewById(R.id.btnMoveUp);

        Intent receivedIntent = getIntent();
        String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
        if (receivedText != null)
            txtIn.setText(receivedText);

        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtIn.getText().toString();
                int key = Integer.parseInt(txtKey.getText().toString()) % 26;
                String output = encode(message, key);
                txtOut.setText(output);
            }
        });

        // SOURCE: https://stackoverflow.com/questions/12436105/keyreleased-equivalence-in-android
        txtKey.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                TextWatcher tw = new TextWatcher() {
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    public void afterTextChanged(Editable s) {
                        try {
                            int key = Integer.parseInt(txtKey.getText().toString()) % 26;
                            slider.setProgress(key + 25);
                            txtKey.setSelection(txtKey.getText().length()); // SOURCE: https://stackoverflow.com/questions/6217378/place-cursor-at-the-end-of-text-in-edittext
                        } catch (Exception ignored) {
                        }
                    }
                };
                if(hasFocus){
                    ((EditText) v).addTextChangedListener(tw);
                }
                if(!hasFocus){
                    ((EditText) v).removeTextChangedListener(tw);
                }
            }
        });

        btnMoveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtOut.getText().toString();
                txtIn.setText(message);
                int current = slider.getProgress();
                slider.setProgress(25 + (25 - current));
            }
        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String message = txtIn.getText().toString();
                int key = slider.getProgress() - 25;
                String output = encode(message, key);
                txtOut.setText(output);
                txtKey.setText("" + key);
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch (SeekBar seekBar) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Secret Message " + DateFormat.getDateTimeInstance().format(new Date()));
                shareIntent.putExtra(Intent.EXTRA_TEXT, txtOut.getText().toString());
                try {
                    startActivity(Intent.createChooser(shareIntent, "Share message..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "Error: Couldn't share.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}