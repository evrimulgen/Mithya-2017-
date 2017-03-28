package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {
    private Context ctx;
    private ArrayList<Coordinator> data;
    Coordinator coordinator;

    public TeamAdapter(Context ctx, ArrayList<Coordinator> data) {
        this.ctx = ctx;
        this.data = data;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Picasso.with(ctx).load(data.get(position).getImage()).into(holder.image);


    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.teamImage);
        }
    }


}
