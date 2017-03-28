package com.pcce.mithya.mithya2017;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    public RecyclerView daysList;
    public static GridView eventList;
    DayAdapter dayAdapter;
    int[] imageList = new int[]{R.drawable.day1, R.drawable.day2, R.drawable.day3, R.drawable.day4};
    String[] days = {"Day 1", "Day 2", "Day 3", "Day 4"};
    static ArrayList<Event> events;
    public static Dialog dialog;
    public static EventAdapter eventAdapter;

    public static boolean flag;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference eventsRef = database.getReference("events");
    static Context ctx;
    static Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ctx = getContext();
        activity = getActivity();
       // Home.toolTitle.setText("Events - Mithya 2017");
        daysList = (RecyclerView) view.findViewById(R.id.dayList);
        eventList = (GridView) view.findViewById(R.id.eventList);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        daysList.setLayoutManager(mLayoutManager);
        daysList.setItemAnimator(new DefaultItemAnimator());
        dayAdapter = new DayAdapter(view.getContext(), imageList, days);
        daysList.setAdapter(dayAdapter);
        events = new ArrayList<>();
        getData("Day 1");
        return view;
    }

    public static void getData(final String dayT){
        if(flag==false) {
            showDialog();
        }
        flag = true;
        events.clear();
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    String day = data.getKey();
                    if (day.equals(dayT)){
                        for (DataSnapshot dataSnap: data.getChildren()) {
                            Event event = new Event();
                            event.setKey(dataSnap.getKey());
                            event.setName((String) dataSnap.child("Name").getValue());
                            event.setDescription((String) dataSnap.child("Description").getValue());
                            event.setDuration((String) dataSnap.child("Duration").getValue());
                            event.setVenue((String) dataSnap.child("Venue").getValue());
                            event.setParticipants((String) dataSnap.child("Participants").getValue());
                            event.setType((String) dataSnap.child("Type").getValue());
                            event.setImage((String) dataSnap.child("Image").getValue());
                            event.setIcon((String) dataSnap.child("Icon").getValue());
                            ArrayList<Coordinator> coordinators = new ArrayList<>();
                            for (DataSnapshot member: dataSnap.child("Coordinators").getChildren()) {
                                Coordinator coordinator = new Coordinator();
                                coordinator.setName(member.getKey());
                                coordinator.setContact(member.getValue().toString());
                                coordinators.add(coordinator);
                            }
                            event.setCoordinators(coordinators);
                            ArrayList<String> rules = new ArrayList<>();
                            for (DataSnapshot member: dataSnap.child("Rules").getChildren()) {
                                rules.add(member.getValue().toString());
                            }
                            event.setRules(rules);
                            events.add(event);
                        }
                    }
                }
                eventAdapter = new EventAdapter(ctx, activity, events);
                eventList.setAdapter(eventAdapter);
                if(flag)
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Operations", "Failed to read value.", error.toException());
            }
        });
    }
    public static void showDialog(){
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress);
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
        TextView wait = (TextView) dialog.findViewById(R.id.wait);
        wait.setTypeface(Main.myCustomFont);
        avi.show();
        dialog.show();
    }



}
