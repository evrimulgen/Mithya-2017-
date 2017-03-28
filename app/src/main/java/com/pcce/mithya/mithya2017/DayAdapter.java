package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Magnus Fernandes on 07-Mar-17.
 */

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder>{
    Context ctx;
    private int[] imageList;
    private String[] days;

    public DayAdapter(Context ctx, int[] imageList, String[] days) {
        this.ctx = ctx;
        this.imageList = imageList;
        this.days = days;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        CardView dayCard;

        public MyViewHolder(View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.dayImage);
            title = (TextView) view.findViewById(R.id.dayText);
            dayCard = (CardView) view.findViewById(R.id.dayCard);
        }
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_days, parent, false);
        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String day = days[position];
        holder.title.setText(day.toUpperCase());
        holder.title.setTypeface(Main.myCustomFont);
        holder.image.setImageResource(imageList[position]);
        holder.dayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsFragment.getData(day);
            }
        });
    }
}
