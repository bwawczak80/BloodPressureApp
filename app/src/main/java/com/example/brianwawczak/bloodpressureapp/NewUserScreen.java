package com.example.brianwawczak.bloodpressureapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewUserScreen extends AppCompatActivity {

    Button homeBtn;
    Button submitBtn;
    TextView userFirstName;
    TextView userLastName;
    TextView userDob;
    TextView userNewUser;
    TextView userNewPassword;
    TextView userVerifyPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);

        homeBtn = findViewById(R.id.idBtnHome);
        submitBtn = findViewById(R.id.idBtnSubmit);
        userFirstName = findViewById(R.id.idUserFirstName);
        userLastName = findViewById(R.id.idUserLastName);
        userDob = findViewById(R.id.idUserDob);
        userNewUser = findViewById(R.id.idNewUserName);
        userNewPassword = findViewById(R.id.idNewPassword);
        userVerifyPassword = findViewById(R.id.idVerifyPassword);



        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO
                //cancelNewUser();

                goHome();

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passwordMatches()) {
                    saveUserData();
                    goHome();


                }else{
                    noMatch();
                }

            }
        });
    }

    public void goHome(){
        Intent returnToLoginIntent = new Intent(NewUserScreen.this, MainActivity.class);

        startActivity(returnToLoginIntent);
    }

    public boolean passwordMatches() {
        String checkPw1 = userNewPassword.getText().toString();
        String checkPw2 = userVerifyPassword.getText().toString();
        return checkPw1.equals(checkPw2);
    }

    public void saveUserData() {

        String holdFirst = userFirstName.getText().toString();
        String holdLast = userLastName.getText().toString();
        String holdDob = userDob.getText().toString();
        String holdUserName = userNewUser.getText().toString();
        String holdPassword = userNewPassword.getText().toString();

        SharedPreferences sp = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("fName", holdFirst);
        editor.putString("lName", holdLast);
        editor.putString("dOfB", holdDob);
        editor.putString("uName", holdUserName);
        editor.putString("pass", holdPassword);
        editor.apply();

    }


    public void noMatch() {
        Toast.makeText(getApplicationContext(), getString(R.string.pwMatchError), Toast.LENGTH_LONG).show();
        userNewPassword.setText("");
        userVerifyPassword.setText("");
        userNewPassword.requestFocus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            userNewPassword.setHintTextColor(getColor(R.color.stage5));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            userVerifyPassword.setHintTextColor(getColor(R.color.stage5));
        }

    }
//TODO
    public void cancelNewUser(View v){ finish();
    }



    }







