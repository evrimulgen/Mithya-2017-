package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sarveshpalav on 22/03/17.
 */
public class ScheduleAdapter   extends  RecyclerView.Adapter<ScheduleAdapter.MyView> {

    private List<Event> scheduleList;
    Context context;
    private static final int ANIMATED_ITEMS_COUNT = 2;
    private int lastAnimatedPosition = -1;
//public TextView Name;


    public ScheduleAdapter(View view) {

        super();

    }

    public ScheduleAdapter(List<Event> scheduleList, Context context) {
        this.scheduleList = scheduleList;
        this.context =context;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);

        return new MyView(itemView);



    }
    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(context));

            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(500)
                    .start();
        }
    }



    @Override
    public void onBindViewHolder(MyView holder,final  int position) {

        runEnterAnimation(holder.itemView, position);
        final Event schedule =scheduleList.get(position);
        holder.Name.setText(schedule.getName());
        holder.Time.setText(schedule.getDuration());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.sharedEvent = scheduleList.get(Integer.parseInt(scheduleList.get(position).getKey()));
              Intent intent = new Intent(context, EventPage.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }



    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class MyView extends RecyclerView.ViewHolder{

        public TextView Name,Time;
        public Object checkbox;
        private CardView cardView;

        public MyView(View view){

            super(view);
            Name = (TextView) view.findViewById(R.id.schedulename);
            Time = (TextView) view.findViewById(R.id.schduletiming);
            cardView =(CardView) view.findViewById(R.id.scheduleCard);

        }

    }



}
