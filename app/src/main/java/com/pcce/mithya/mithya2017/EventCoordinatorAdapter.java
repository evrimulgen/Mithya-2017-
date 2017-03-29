package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Magnus Fernandes on 21-Mar-17.
 */

public class EventCoordinatorAdapter extends RecyclerView.Adapter<EventCoordinatorAdapter.MyViewHolder> {
    Context context;
    ArrayList<Coordinator> names;

    public EventCoordinatorAdapter(Context context, ArrayList<Coordinator> names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eventcoordinator, parent, false);
        return new EventCoordinatorAdapter.MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       holder.nameText.setTypeface(Main.myCustomFont);
       holder.numberText.setTypeface(Main.myCustomFont);
       // Toast.makeText(context,"kik"+names.get(position).getName(),Toast.LENGTH_SHORT).show();
        holder.nameText.setText(names.get(position).getName());
        holder.numberText.setText(names.get(position).getContact());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + holder.numberText.getText().toString()));
               context. startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, numberText;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.cdName);
            numberText = (TextView) view.findViewById(R.id.cdNo);
            linearLayout =(LinearLayout) view.findViewById(R.id.linear);
        }
    }

}










