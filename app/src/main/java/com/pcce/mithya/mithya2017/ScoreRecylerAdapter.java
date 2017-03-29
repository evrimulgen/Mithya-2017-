package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sarvesh on 29-03-2017.
 */

public class ScoreRecylerAdapter extends  RecyclerView.Adapter<ScoreRecylerAdapter.MyView> {

    private List<Ponts> pointsList;
    private Context context;

    public ScoreRecylerAdapter(List<Ponts> pointsList, Context context) {
        this.pointsList = pointsList;
        this.context = context;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score_recycler, parent, false);

        return new ScoreRecylerAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        holder.Name.setText(pointsList.get(position).getName());
        holder.Points.setText(pointsList.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
     return    pointsList.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {


        TextView Name;
        TextView Points;

        public MyView(View view) {

            super(view);
            Name = (TextView) view.findViewById(R.id.nameofevent);
            Points =(TextView)view.findViewById(R.id.pointsofevent);

        }

    }


}
