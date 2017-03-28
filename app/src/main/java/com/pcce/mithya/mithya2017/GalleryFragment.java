package com.pcce.mithya.mithya2017;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {
    static Activity activity;
    RecyclerView galleryrecylerview;
    DatabaseReference mDatabase;
    private GalleryAdapter mAdapter;
    List<ImageUpload> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryrecylerview = (RecyclerView)view.findViewById(R.id.galleryrecylerview);
        mDatabase= FirebaseDatabase.getInstance().getReference("gallery");
        activity = getActivity();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        galleryrecylerview.setLayoutManager(mLayoutManager);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    Log.d("Gallery",data.child("url").getValue().toString());

                    ImageUpload imageUpload = new ImageUpload();
                    imageUpload.setUrl(data.child("url").getValue().toString());
                    imageUpload.setCaption(data.child("caption").getValue().toString());

                    list.add(imageUpload);
                }
                Collections.reverse(list);
                mAdapter=new GalleryAdapter(list,activity);
                galleryrecylerview.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

}
