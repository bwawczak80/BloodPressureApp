package com.example.brianwawczak.bloodpressureapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeScreen extends AppCompatActivity {

    double systolicDouble;
    double diastolicDouble;
    EditText systolic;
    EditText diastolic;
    TextView bpDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final Button logBp = findViewById(R.id.idBtnLogBp);
        final Button viewHistory = findViewById(R.id.idBtnViewHistory);
        final Button calculateAverage = findViewById(R.id.idBtnAverage);
        systolic = findViewById(R.id.idUserBpInput1);
        diastolic = findViewById(R.id.idBpInput2);
        bpDisplay = findViewById(R.id.idBpDisplay);
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

                calculateBpRange(systolicDouble, diastolicDouble);

                bpWarningLevel = calculateBpRange(systolicDouble, diastolicDouble);

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
                    case 5:
                        rangeDisplay.setText(getString(R.string.bpLow));
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage2));

                    default:
                        rangeDisplay.setText(getString(R.string.bpCrisis)) ;
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage5));
                        break;
                }
                writeFile();

            }
        });

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();

            }
        });

        calculateAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public int calculateBpRange(double s, double d){
        int bpWarningLevel = 0;
        if (s < 120 && d < 80){
            bpWarningLevel = 1;
        }else if (s >= 120 && s < 130 && d < 80){
            bpWarningLevel = 2;
        }else if (s >= 130 && s < 140 && d < 89 || d >= 80 && d < 89){
            bpWarningLevel = 3;
        } else if (s >= 140 && s < 180 && d < 120 || d >= 90 && d < 120) {
            bpWarningLevel = 4;
        }else if (s >= 180 || d >= 120){
            bpWarningLevel = 5;
        }else if (s < 100 || d < 60)
            bpWarningLevel = 6;
        return bpWarningLevel;


    }

    public void writeFile() {
        String systolicUserInput = systolic.getText().toString();
        String diastolicUserInput = diastolic.getText().toString();
        String bloodPressure = systolicUserInput + " / " + diastolicUserInput;

        try {
            FileOutputStream fileOutputStream = openFileOutput("bloodPressureLog.txt", MODE_PRIVATE);
            fileOutputStream.write(bloodPressure.getBytes());
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(), "Log saved", Toast.LENGTH_LONG).show();

            systolic.setText("");
            diastolic.setText("");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            bpDisplay.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
