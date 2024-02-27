package com.example.cob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void logout() {
        SharedPreferences sharedPreferences=getSharedPreferences(LogIn.PREFS_NAME,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putBoolean("hasLoggedIn",false);
        editor.commit();

        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}