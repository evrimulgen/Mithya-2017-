package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sarveshpalav on 22/03/17.
 */
public class SchedulePageAdapter extends FragmentPagerAdapter {


    Context mContext;

    public SchedulePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {

        // Create fragment object
        Fragment fragment =null;

        switch (position)
        {
            case 0:
               fragment = new Day1Schedule();
                break;
            case 1:
                 fragment = new Day2Schedule();
                break;
            case 2:
                 fragment = new Day3Schedule();
                break;
            case  3:
                fragment = new Day4Schedule();
                break;
                default:
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Day  " + (position + 1);
    }
}
