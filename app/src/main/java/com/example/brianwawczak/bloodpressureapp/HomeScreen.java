package com.example.brianwawczak.bloodpressureapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class HomeScreen extends AppCompatActivity {

    double systolicDouble;
    double diastolicDouble;
    EditText systolic;
    EditText diastolic;
    TextView bpDisplay;
    TextView rangeDisplay;
    Button logBp;
    Button viewHistory;
    TextView welcomeText;
    String welcomeMsg;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        welcomeText = findViewById(R.id.idWelcomeTxt);
        logBp = findViewById(R.id.idBtnLogBp);
        viewHistory = findViewById(R.id.idBtnViewHistory);
        systolic = findViewById(R.id.idUserBpInput1);
        diastolic = findViewById(R.id.idBpInput2);
        bpDisplay = findViewById(R.id.idBpDisplay);
        rangeDisplay = findViewById(R.id.idRangeDisplay);
        String a[] = readFromSharedPref();
        welcomeMsg = (getString(R.string.welcome) + " " + a[0] + (getString(R.string.enterBp)));
        welcomeText.setText(welcomeMsg);


        logBp.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String holdSystolic;
                String holdDiastolic;
                holdSystolic = systolic.getText().toString();
                holdDiastolic = diastolic.getText().toString();
                rangeDisplay.setBackgroundColor(getColor(R.color.background));
                int bpWarningLevel;

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
                        break;
                    case 6:
                        rangeDisplay.setText(getString(R.string.bpCrisis));
                        rangeDisplay.setBackgroundColor(getColor(R.color.stage5));
                        break;
                    case 7:
                        rangeDisplay.setText(getString(R.string.invalid));
                        rangeDisplay.setBackgroundColor(getColor(R.color.background));
                    default:
                        rangeDisplay.setText(getString(R.string.invalid));
                        break;
                }
                writeFile();

            }
        });

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistoryScreen();

            }
        });


    }

    public int calculateBpRange(double s, double d){
        int bpWarningLevel = 0;
        if (s <= 0 || d <= 0){
            bpWarningLevel = 7;
        }else if (s < 100 || d < 60){
            bpWarningLevel = 5;
        }else if (s < 120 && d < 80 && s >= 100 && d >= 60){
            bpWarningLevel = 1;
        }else if (s >= 120 && s < 130 && d <= 80){
            bpWarningLevel = 2;
        }else if (s >= 130 && s < 140 && d < 89 || d >= 80 && d < 89){
            bpWarningLevel = 3;
        } else if (s >= 140 && s < 180 && d < 120 || d >= 90 && d < 120) {
            bpWarningLevel = 4;
        }else if (s < 120 && d < 80) {
            bpWarningLevel = 5;
        }else if (s >= 180 || d >= 120)
            bpWarningLevel = 6;
        return bpWarningLevel;

    }

    public void writeFile() {
        String systolicUserInput = systolic.getText().toString();
        String diastolicUserInput = diastolic.getText().toString();
        String bloodPressure = dateTimeStamp() + "  " + systolicUserInput + " / " + diastolicUserInput + "\n";

        try {
            FileOutputStream fileOutputStream = openFileOutput("bloodPressureLog.txt", MODE_APPEND);
            fileOutputStream.write(bloodPressure.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Log saved", Toast.LENGTH_SHORT).show();

            systolic.setText("");
            diastolic.setText("");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String dateTimeStamp() {
        Date myDate = new Date();
        return DateFormat.getDateTimeInstance().format(myDate);
    }

    public void openHistoryScreen(){
        Intent intent = new Intent(HomeScreen.this, HistoryScreen.class);
        startActivity(intent);
    }

    public String[] readFromSharedPref() {

        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        String first = sp.getString("fName", "");
        String last = sp.getString("lName", "");
        String dob = sp.getString("dOfB", "");
        String user = sp.getString("uName", "");
        String pw = sp.getString("pass", "");
        return new String[]{first, last, dob, user, pw};

    }

}


