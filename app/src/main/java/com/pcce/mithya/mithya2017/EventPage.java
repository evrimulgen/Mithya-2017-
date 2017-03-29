package com.pcce.mithya.mithya2017;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventPage extends AppCompatActivity {

    private TextView title, description, duration, venue, type, toolTitle, rules, rulesHead;
    private Toolbar toolbar;
    private ImageView image, icon;
    private CardView cardView;
    RecyclerView list;
    private FloatingActionButton reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        title = (TextView) findViewById(R.id.eventTitle);
        description = (TextView) findViewById(R.id.eventDescription);
        duration = (TextView) findViewById(R.id.eventDuration);
        venue = (TextView) findViewById(R.id.eventVenue);
        type = (TextView) findViewById(R.id.eventType);
        rules = (TextView) findViewById(R.id.eventRules);
        rulesHead = (TextView) findViewById(R.id.rulesHead);
        list = (RecyclerView) findViewById(R.id.coordinators);
        toolTitle = (TextView) findViewById(R.id.toolbar_title);
        cardView =(CardView)findViewById(R.id.rulescard);

//        reminder = (FloatingActionButton)findViewById(R.id.addreminder);

        image = (ImageView) findViewById(R.id.eventImage);
        icon = (ImageView) findViewById(R.id.eventIcon);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent i = new Intent(EventPage.this, Home.class);
             //   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });

        title.setTypeface(Main.myCustomFont);
        type.setTypeface(Main.myCustomFont);
        description.setTypeface(Main.myCustomFont);
        duration.setTypeface(Main.myCustomFont);
        venue.setTypeface(Main.myCustomFont);
        toolTitle.setTypeface(Main.myCustomFont);
        rules.setTypeface(Main.myCustomFont);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(EventPage.this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(mLayoutManager);



//        reminder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),gettime(duration.getText().toString()),Toast.LENGTH_LONG).show();
//                String time = "530pm";
//                SimpleDateFormat dateFormat = new SimpleDateFormat("hmm");
//                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm aa");
//                try {
//                    Date date = dateFormat.parse(time);
//
//                    String out = dateFormat2.format(date);
//                    Log.e("Time", out);
//                } catch (ParseException e) {
//                }
//                Calendar cal = Calendar.getInstance();
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setType("vnd.android.cursor.item/event");
//                intent.putExtra("beginTime","09:00");
//                intent.putExtra("allDay", false);
//                intent.putExtra("rrule", "FREQ=DAILY");
//                intent.putExtra("endTime", "09:30");
//                intent.putExtra("title", title.getText());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        title.setText(Main.sharedEvent.getName());
        toolTitle.setText(Main.sharedEvent.getName().toUpperCase());
        description.setText(Main.sharedEvent.getDescription());
        duration.setText(Main.sharedEvent.getDuration());
        venue.setText(Main.sharedEvent.getVenue());
        type.setText(Main.sharedEvent.getType() + "  EVENT");
        String rulesText = "";
        for (String rule: Main.sharedEvent.getRules()) {
            rulesText += "• " + rule;
            rulesText += "\n";
        }
        if(rulesText.equals(""))
        {
            cardView.setVisibility(View.GONE);
        }else
        {
            rules.setText(rulesText);
        }
        Picasso.with(getApplicationContext()).load(Main.sharedEvent.getImage()).into(image);
        EventCoordinatorAdapter listAdapter = new EventCoordinatorAdapter(EventPage.this, Main.sharedEvent.getCoordinators());
     //  Toast.makeText(EventPage.this,String.valueOf(Main.sharedEvent.getCoordinators().size()) ,Toast.LENGTH_LONG).show();
        list.setAdapter(listAdapter);
    }

    private String gettime(String s)
    {
        String beforeFirstDot = s.split("\\-")[0];
        return beforeFirstDot;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
