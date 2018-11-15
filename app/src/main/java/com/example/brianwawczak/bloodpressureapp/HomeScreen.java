package com.example.brianwawczak.bloodpressureapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    double systolicDouble;
    double diastolicDouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final Button logBp = findViewById(R.id.idBtnLogBp);
        final Button viewHistory = findViewById(R.id.idBtnViewHistory);
        final Button calculateAverage = findViewById(R.id.idBtnAverage);
        final EditText systolic = findViewById(R.id.idUserBpInput1);
        final EditText diastolic = findViewById(R.id.idBpInput2);
        final TextView bpDisplay = findViewById(R.id.idBpDisplay);
        final TextView rangeDisplay = findViewById(R.id.idRangeDisplay);




        logBp.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String holdSystolic;
                String holdDiastolic;
                holdSystolic = systolic.getText().toString();
                holdDiastolic = diastolic.getText().toString();
                int bpWarningLevel = 0;

                try {
                    systolicDouble = Double.parseDouble(holdSystolic);
                } catch (NumberFormatException e) {
                    systolicDouble = 0;
                }
                try {
                    diastolicDouble = Double.parseDouble(holdDiastolic);
                } catch (NumberFormatException e) {
                    diastolicDouble = 0;
                }
                String displayString =  " " + systolicDouble + " / " + diastolicDouble;
                bpDisplay.setText(getString(R.string.bpLogDisplay).concat(displayString));

                if (systolicDouble < 120 && diastolicDouble < 80){
                    bpWarningLevel = 1;
                }else if (systolicDouble >= 120 && systolicDouble < 130 && diastolicDouble < 80){
                    bpWarningLevel = 2;
                }else if (systolicDouble >= 130 && systolicDouble < 140 && diastolicDouble < 89 || diastolicDouble >= 80 && diastolicDouble < 89){
                    bpWarningLevel = 3;
                } else if (systolicDouble >= 140 && systolicDouble < 180 && diastolicDouble < 120 || diastolicDouble >= 90 && diastolicDouble < 120) {
                    bpWarningLevel = 4;
                }else if (systolicDouble >= 180 || diastolicDouble >= 120){
                    bpWarningLevel = 5;
                }

                switch(bpWarningLevel){
                    case 1:
                        rangeDisplay.setText(getString(R.string.bpNormal)) ;
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage1));
                        break;
                    case 2:
                        rangeDisplay.setText(getString(R.string.bpElevated)) ;
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage2));
                        break;
                    case 3:
                        rangeDisplay.setText(getString(R.string.bpStage1)) ;
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage3));
                        break;
                    case 4:
                        rangeDisplay.setText(getString(R.string.bpStage2)) ;
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage4));
                        break;
                    default:
                        rangeDisplay.setText(getString(R.string.bpCrisis)) ;
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage5));
                        break;
                }







            }
        });

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        calculateAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }



}
