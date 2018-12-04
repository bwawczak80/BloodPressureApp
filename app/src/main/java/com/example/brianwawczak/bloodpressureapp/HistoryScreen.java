package com.example.brianwawczak.bloodpressureapp;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class HistoryScreen extends AppCompatActivity {

    TextView logDisplay;
    Button goHome;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);
        logDisplay = findViewById(R.id.idHistoryText);
        goHome = findViewById(R.id.idHomeButton);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        readFile();

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

    }

    public void readFile() {

        try {
            FileInputStream fileInputStream = openFileInput("bloodPressureLog.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();

            String lineItem;
            while ((lineItem = bufferedReader.readLine()) !=null) {
                stringBuffer.append(lineItem).append("\n");
            }

            logDisplay.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goHome(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }






}
//TODO

// add display of user data
