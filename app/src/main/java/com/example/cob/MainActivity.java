package com.example.cob;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences(LogIn.PREFS_NAME,0);
        boolean hasLoggedIn=sharedPreferences.getBoolean("hasLoggedIn",false);

        if(hasLoggedIn) {
            Intent intent = new Intent(MainActivity.this, Feed.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(MainActivity.this,LogIn.class);
            startActivity(intent);
            finish();
        }

        button=(Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogIN();
            }
        });
        button=(Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

    }

    private void openRegister() {
        Intent intent = new Intent(this,Register.class);
        Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
    }

    private void openLogIN() {
        Intent intent =new Intent(this,LogIn.class);
        Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
    }
}