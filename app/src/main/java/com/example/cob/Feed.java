package com.example.cob;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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


    }

        private void openMap() {
            Intent intent = new Intent(this,MapPage.class);
            Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(intent,b);
    }
}