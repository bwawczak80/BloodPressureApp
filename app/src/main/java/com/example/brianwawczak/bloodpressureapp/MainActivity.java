package com.example.brianwawczak.bloodpressureapp;
import android.content.Intent;
import android.content.SharedPreferences;
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
        Button createNewUser = findViewById(R.id.idNewUserBtn);


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

        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewUserScreen();
            }
        });
    }

    public void openHomeScreen() {
        Intent homeScreenIntent = new Intent (MainActivity.this, HomeScreen.class);
        startActivity(homeScreenIntent);
    }

    public void openNewUserScreen() {
        Intent newUserIntent = new Intent (MainActivity.this, NewUserScreen.class);
        startActivity(newUserIntent);

    }

    LoginSuccess CheckLogin(String tLogin, String tPass){
        String userLogin[] = readFromSharedPref();
        if(!(tLogin.equals(userLogin[3]))){
            return LoginSuccess.login;
        }
        if(!(tPass.equals(userLogin[4]))){
            return LoginSuccess.password;
        }
        return LoginSuccess.success;

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
// TODO


// Auto fill UserName and Password upon return from creating a new user.