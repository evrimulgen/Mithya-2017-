package com.pcce.mithya.mithya2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class HomeFragment extends Fragment {
    public ImageView events, schedule, teams;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference bannerRef = database.getReference("banner");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        events = (ImageView) view.findViewById(R.id.eventsButton);
        schedule = (ImageView) view.findViewById(R.id.scheduleButton);
        Home.imageadd.setVisibility(View.VISIBLE);

        teams = (ImageView) view.findViewById(R.id.teamButton);
       final  BannerSlider bannerSlider = (BannerSlider) view.findViewById(R.id.banner);
       bannerRef.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                                                   bannerSlider.addBanner(new RemoteBanner(dataSnap.child("Image").getValue().toString()));
                                               }
                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {

                                           }

                                       });





        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsClicked();
            }
        });
        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamClicked();
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleClicked();
            }
        });
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {

                Home.toolTitle.setText("Gallery - Mithya 2017");
                Home.mDrawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = Home.mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, new GalleryFragment()).commit();
            }
        });



        return  view;
    }

    public void eventsClicked(){
        Home.toolTitle.setText("Events - Mithya 2017");
        Home.mDrawerLayout.closeDrawers();
        FragmentTransaction fragmentTransaction = Home.mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, new EventsFragment()).commit();
    }

    public void teamClicked(){
        Home.toolTitle.setText("Team - Mithya 2017");
        Home.mDrawerLayout.closeDrawers();
        FragmentTransaction fragmentTransaction = Home.mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, new TeamFragment()).commit();
    }


    public void ScheduleClicked()
    {

        startActivity(new Intent(getActivity(),ScheduleActivity.class));
    }
}
