package com.pcce.mithya.mithya2017;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarveshpalav on 22/03/17.
 */
public class Day1Schedule extends Fragment {


    RecyclerView schedulerecyler;
    DatabaseReference mDatabase,day;
    private ScheduleAdapter mAdapter;
    List<Event> list = new ArrayList<>();
    static Activity activity;
    public static Dialog dialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.fragment_scheduleday1, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference("events");
        activity = getActivity();
        schedulerecyler = (RecyclerView)rootView.findViewById(R.id.schedulerecylerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        schedulerecyler.setLayoutManager(mLayoutManager);
       // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(schedulerecyler.getContext(),
      //          LinearLayoutManager.HORIZONTAL);
     //   schedulerecyler.addItemDecoration(dividerItemDecoration);
        day = mDatabase.child("Day 1");
        showDialog();
        day.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap: dataSnapshot.getChildren())
                {

                    Event event = new Event();
                    event.setKey(dataSnap.getKey());
                    event.setName( dataSnap.child("Name").getValue().toString());
                  Log.d("lol", dataSnap.child("Name").getValue().toString());
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
                    list.add(event);









                }

                mAdapter=new ScheduleAdapter(list,getActivity());
                schedulerecyler.setAdapter(mAdapter);
dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
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
