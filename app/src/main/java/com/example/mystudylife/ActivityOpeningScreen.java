package com.example.mystudylife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class ActivityOpeningScreen extends AppCompatActivity {

    private static int CHARGING_TIME = 4800;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(ActivityOpeningScreen.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        }, CHARGING_TIME);
    }
}



