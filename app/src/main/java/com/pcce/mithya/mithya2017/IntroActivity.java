package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.Manifest;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

/**
 * Created by Aldrich on 28-Mar-17.
 */

public class IntroActivity extends MaterialIntroActivity {

    SharedPreferences sharedPreferences;
    boolean firstTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("first", false);


        if (firstTime) {

            Intent intent = new Intent(IntroActivity.this, Splash.class);
            startActivity(intent);
            finish();
        } else {


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first", true);
            editor.apply();


            hideBackButton();

            addSlide(new SlideFragmentBuilder()
                    .backgroundColor(R.color.colorPrimary)
                    .buttonsColor(R.color.colorAccent)
                    .neededPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA})
                    .title("Hello there")
                    .description("Before we get started we need a Few Permissions")
                    .build());


            addSlide(new SlideFragmentBuilder()
                            .backgroundColor(R.color.colorPrimary)
                            .buttonsColor(R.color.colorAccent)
                            .image(R.mipmap.ic_launcher)
                            .title("Almost there")
                            .build(),
                    new MessageButtonBehaviour(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    Intent i = new Intent(IntroActivity.this, Splash.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 3000);


                        }
                    }, "Continue"));

        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
    }

}
