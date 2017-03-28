package com.pcce.mithya.mithya2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class HomeFragment extends Fragment {
    public ImageView events, schedule, teams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        events = (ImageView) view.findViewById(R.id.eventsButton);
        schedule = (ImageView) view.findViewById(R.id.scheduleButton);

        teams = (ImageView) view.findViewById(R.id.teamButton);
        BannerSlider bannerSlider = (BannerSlider) view.findViewById(R.id.banner);
        bannerSlider.addBanner(new RemoteBanner("https://firebasestorage.googleapis.com/v0/b/mithya-2017.appspot.com/o/Slider%2F1.jpg?alt=media&token=b7826868-6854-48f4-a5eb-785542525ba3"));
        bannerSlider.addBanner(new RemoteBanner("https://firebasestorage.googleapis.com/v0/b/mithya-2017.appspot.com/o/Slider%2F2.jpg?alt=media&token=ed40a700-9a60-4bb5-8833-d2c755dd9ec7"));
        bannerSlider.addBanner(new RemoteBanner("https://firebasestorage.googleapis.com/v0/b/mithya-2017.appspot.com/o/Slider%2F3.jpg?alt=media&token=19e5e824-16d2-48fc-9f01-6b29d07e3293"));
        bannerSlider.addBanner(new RemoteBanner("https://firebasestorage.googleapis.com/v0/b/mithya-2017.appspot.com/o/Slider%2F4.jpg?alt=media&token=080c3b45-c427-40b3-acfb-8b6e89ccda84"));
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
