package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Magnus Fernandes on 21-Mar-17.
 */

public class EventCoordinatorAdapter extends ArrayAdapter<Coordinator>{
    EventCoordinatorAdapter(Context context, ArrayList<Coordinator> names){
        super(context, R.layout.item_eventcoordinator, names);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.item_eventcoordinator, parent, false);
        Coordinator coordinator = getItem(position);
        TextView nameText = (TextView) customView.findViewById(R.id.cdName);
        TextView numberText = (TextView) customView.findViewById(R.id.cdNo);
        nameText.setTypeface(Main.myCustomFont);
        numberText.setTypeface(Main.myCustomFont);
        nameText.setText(coordinator.getName());
        numberText.setText(coordinator.getContact());
        return customView;
    }
}
