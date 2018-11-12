package com.example.brianwawczak.bloodpressureapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    int systolic;
    int diastolic;
    String bpDisplay = "Your blood pressure is " + systolic + " / " + diastolic;
}
