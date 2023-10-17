package com.example.cob;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Feed extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(Feed.this, R.color.black));
        setContentView(R.layout.activity_feed);


        button=(Button) findViewById(R.id.mapbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        button=(Button) findViewById(R.id.LogOUT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {logout();
            }
        });

        button=(Button) findViewById(R.id.forum);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {forumChat();
            }
        });


    }

    private void forumChat() {
        Intent intent = new Intent(this,ForumPage.class);
        Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
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

        private void openMap() {
            Intent intent = new Intent(this,MapPage.class);
            Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(intent,b);
    }
}