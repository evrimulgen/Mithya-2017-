package com.pcce.mithya.mithya2017;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

public class TeamFragment extends Fragment {
    public static DiscreteScrollView scrollView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference teamRef = database.getReference("team");
    ArrayList<Coordinator> team;
    TextView fname, lname, designation;
    Button contact;
    String phone;
    public static TeamAdapter teamAdapter;
    public static Dialog dialog;
    int count=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        Home.toolTitle.setText("Crew - Mithya 2017");

        Home.imageadd.setVisibility(View.INVISIBLE);

        scrollView = (DiscreteScrollView) view.findViewById(R.id.picker);
        fname = (TextView) view.findViewById(R.id.teamFName);
        lname = (TextView) view.findViewById(R.id.teamLName);
        contact = (Button) view.findViewById(R.id.teamContact);
        designation = (TextView) view.findViewById(R.id.teamDesignation);
        team = new ArrayList<>();
        teamAdapter = new TeamAdapter(getActivity(),team);
        scrollView.setAdapter(teamAdapter);
//        showDialog();
        getData();

        scrollView.setClipToPadding(false);
        scrollView.setPaddingRelative(150,0,150,0);
        fname.setTypeface(Main.myCustomFont);
        lname.setTypeface(Main.myCustomFont);
        contact.setTypeface(Main.myCustomFont);
        designation.setTypeface(Main.myCustomFont);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                try{
                    getActivity().startActivity(callIntent);
                } catch (SecurityException e){
                    onCall();
                }
            }
        });

        scrollView.setScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {

            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                getInfo(adapterPosition);

            }

            @Override
            public void onScroll(float scrollPosition, @NonNull RecyclerView.ViewHolder currentHolder, @NonNull RecyclerView.ViewHolder newCurrent) {

            }


        });

        return view;
    }

    public void getInfo(int position){
        fname.setText(Main.splitName(team.get(position).getName(), "first").toUpperCase());
        lname.setText(Main.splitName(team.get(position).getName(), "last").toUpperCase());
        designation.setText(team.get(position).getDesignation());
        phone = team.get(position).getContact();
    }

    public  void getData(){
        teamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Coordinator coordinator = new Coordinator();
                    Log.d("yolo",(String)data.child("Name").getValue());
                    coordinator.setName((String)data.child("Name").getValue());
                    coordinator.setContact((String) data.child("Phone").getValue());
                    coordinator.setDesignation((String) data.child("Designation").getValue());
                    coordinator.setImage((String) data.child("Image").getValue());
                    team.add(coordinator);
                }

                teamAdapter = new TeamAdapter(getContext(), team);
                scrollView.setAdapter(teamAdapter);
                teamAdapter.notifyDataSetChanged();
                scrollView.setItemTransformer(new ScaleTransformer.Builder()
                        .setMinScale(0.8f)
                        .build());
                getInfo(scrollView.getCurrentItem());


            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Operations", "Failed to read value.", error.toException());
            }



        });
    }
    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + phone)));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("Operations", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


}
