package com.example.brianwawczak.bloodpressureapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public enum LoginSuccess {
        login(),
        password(),
        success();

        LoginSuccess(){
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        final TextView txtLogin = findViewById(R.id.idUserLogin);
        final TextView txtPass = findViewById(R.id.idUserPassword);
        Button btnLogin = findViewById(R.id.idLoginButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(CheckLogin(txtLogin.getText().toString(), txtPass.getText().toString())){
                    case login:
                        Toast.makeText(getApplicationContext(),getString(R.string.errMessageLogin), Toast.LENGTH_LONG).show();
                        txtLogin.requestFocus();
                        break;

                    case password:
                        Toast.makeText(getApplicationContext(), getString(R.string.errMessagePassword), Toast.LENGTH_LONG).show();
                        txtPass.requestFocus();
                        break;
                    default:
                        openHomeScreen();
                        break;
                }
            }
        });
    }

    public void openHomeScreen(){
        Intent intent = new Intent (this, HomeScreen.class);
        startActivity(intent);
    }

    LoginSuccess CheckLogin(String txtLogin, String txtPassword){
        String holdLogin = "Brian";
        String holdPassword = "password";

        if(!(holdLogin.equals(txtLogin))){
            return LoginSuccess.login;
        }
        if(!(holdPassword.equals(txtPassword))){
            return LoginSuccess.password;
        }
        return LoginSuccess.success;
    }
}
