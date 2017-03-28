package com.pcce.mithya.mithya2017;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;
    TextView presents;
    private static int SPLASH_TIME = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Main.myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/montserrat.ttf");
        linearLayout = (LinearLayout) findViewById(R.id.activity_splash);
        presents = (TextView) findViewById(R.id.presents);
        presents.setTypeface(Main.myCustomFont);
        animationDrawable =(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(200);
        animationDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }, SPLASH_TIME);
    }
}
