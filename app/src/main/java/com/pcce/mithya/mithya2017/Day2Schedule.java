package com.pcce.mithya.mithya2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarveshpalav on 22/03/17.
 */
public class Day2Schedule extends Fragment {

    RecyclerView schedulerecyler;
    DatabaseReference mDatabase,day;
    private ScheduleAdapter mAdapter;
    List<Event> list = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.fragment_scheduleday1, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference("events");

        schedulerecyler = (RecyclerView)rootView.findViewById(R.id.schedulerecylerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        schedulerecyler.setLayoutManager(mLayoutManager);

        day = mDatabase.child("Day 2");


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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }


}
