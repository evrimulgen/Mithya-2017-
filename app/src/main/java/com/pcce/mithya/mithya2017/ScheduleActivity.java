package com.pcce.mithya.mithya2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

/**
 * Created by sarveshpalav on 22/03/17.
 */
public class ScheduleActivity extends AppCompatActivity {


    SchedulePageAdapter schedulePageAdapter;
    ViewPager mViewPager;
    Toolbar toolbar;
    TextView toolTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
       FirebaseApp.initializeApp(this);
        toolTitle = (TextView) findViewById(R.id.toolTitle);
        toolTitle.setTypeface(Main.myCustomFont);
        toolTitle.setText("Schedule - Mithya 2017");

        schedulePageAdapter = new SchedulePageAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(schedulePageAdapter);
        mViewPager.setOffscreenPageLimit(4);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScheduleActivity.this, Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }





}
