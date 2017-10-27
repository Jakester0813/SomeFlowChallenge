package com.jakester.someflowchallenge.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakester.someflowchallenge.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Runnable endSplash = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LogInActivity.class);
                startActivity(i);
            }
        };
        new Handler().postDelayed(endSplash, 3000L);
    }
}
