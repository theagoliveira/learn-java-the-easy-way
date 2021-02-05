package com.example.madlibsapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText txtBigAnimal;
    private EditText txtPastTenseVerb;
    private EditText txtSmallContainer;
    private EditText txtFood;
    private EditText txtOutput;

    public void displayCreation() {
        String bigAnimal = txtBigAnimal.getText().toString();
        String pastTenseVerb = txtPastTenseVerb.getText().toString();
        String smallContainer = txtSmallContainer.getText().toString();
        String food = txtFood.getText().toString();

        txtOutput.setText("Once upon a time... \n There was a " + bigAnimal + " princess \n who " + pastTenseVerb + " in a " + food + " " + smallContainer + ".");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBigAnimal = (EditText) findViewById(R.id.txtBigAnimal);
        txtPastTenseVerb = (EditText) findViewById(R.id.txtPastTenseVerb);
        txtSmallContainer = (EditText) findViewById(R.id.txtSmallContainer);
        txtFood = (EditText) findViewById(R.id.txtFood);
        txtOutput = (EditText) findViewById(R.id.txtOutput);
        Button btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCreation();
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
