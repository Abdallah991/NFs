package com.fathom.nfs;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class SplashScreenActivity extends AppCompatActivity {
    // handler will able us to delay and show case the splash screen
    private Handler myHandler;
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myHandler = new Handler();

        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),
                        OnBoarding.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);



    }
}