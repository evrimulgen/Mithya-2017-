package com.pcce.mithya.mithya2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Magnus Fernandes on 08-Mar-17.
 */

public class EventAdapter extends BaseAdapter{
    private Context ctx;
    private ArrayList<Event> events;
    private Activity activity;
    int pos;
    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 2;

    public EventAdapter(Context ctx, Activity activity, ArrayList<Event> events) {
        this.ctx = ctx;
        this.activity = activity;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        public ImageView eventImage;
        public CardView eventCard;
    }
    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(ctx));

            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(500)
                    .start();
        }
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.item_event, null);

            runEnterAnimation(convertView,position);

            view.eventImage = (ImageView) convertView.findViewById(R.id.eventImage);
            view.eventCard = (CardView) convertView.findViewById(R.id.eventCard);

            convertView.setTag(view);
        }
        else {
            view = (ViewHolder) convertView.getTag();
        }
        pos = position;
        view.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.sharedEvent = events.get(Integer.parseInt(events.get(position).getKey()));
                Intent intent = new Intent(ctx, EventPage.class);
               // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });
        if (EventsFragment.flag){
            EventsFragment.dialog.dismiss();
        }
        Picasso.with(ctx).load(events.get(position).getIcon()).into(view.eventImage,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        if (events.size() == position+1 && !EventsFragment.flag){
                            EventsFragment.eventList.setAdapter(EventsFragment.eventAdapter);
                            EventsFragment.dialog.dismiss();
                            EventsFragment.flag = true;
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        return convertView;
    }
}
