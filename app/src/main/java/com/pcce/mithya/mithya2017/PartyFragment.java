package com.pcce.mithya.mithya2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;

public class PartyFragment extends Fragment {

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party, container, false);
        image = (ImageView) view.findViewById(R.id.image);
//        Get image from firebase party.jpg
        return view;
    }

}
