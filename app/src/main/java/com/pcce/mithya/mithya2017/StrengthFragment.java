package com.pcce.mithya.mithya2017;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StrengthFragment extends Fragment {
    private PieChart chart;
    DatabaseReference mDatabase;
    private Integer[] yData = {5000, 2000, 3500, 3000};
    private String[] xData = {"Information Technology", "Mechanical Engineering", "Computer Engineering", "Electronics and Telecommunications"};
    Context ctx;
    String day;
    public static  final int[] MY_COLORS = {
            Color.parseColor("#e74c3c"),
            Color.parseColor("#3498db"),
            Color.parseColor("#2ecc71"),
            Color.parseColor("#f1c40f")
    };

    private TextView it, comp, mech, etc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strength, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference("strength");
        ctx = getActivity();
        chart = (PieChart) view.findViewById(R.id.chart);
        it = (TextView) view.findViewById(R.id.itLabel);
        comp = (TextView) view.findViewById(R.id.compLabel);
        mech = (TextView) view.findViewById(R.id.mechLabel);
        etc = (TextView) view.findViewById(R.id.etcLabel);
        it.setTypeface(Main.myCustomFont);
        comp.setTypeface(Main.myCustomFont);
        mech.setTypeface(Main.myCustomFont);
        etc.setTypeface(Main.myCustomFont);
        chart.setCenterText(generateCenterSpannableText());
        chart.setHoleColor(Color.parseColor("#ecf0f1"));
    //    loadChart(yData);
        chart.setEntryLabelColor(Color.WHITE);



        mDatabase.child("it").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                yData[0]= Integer.valueOf(dataSnapshot.getValue().toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadChart(yData);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("mech").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                yData[1]= Integer.valueOf(dataSnapshot.getValue().toString());
                loadChart(yData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("etc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                yData[2]= Integer.valueOf(dataSnapshot.getValue().toString());
                loadChart(yData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("comp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                yData[3]= Integer.valueOf(dataSnapshot.getValue().toString());
                loadChart(yData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("day").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                day = dataSnapshot.getValue().toString();
                chart.invalidate();
                chart.setCenterText(generateCenterSpannableText());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        return view;
    }

    private void loadChart(Integer[] yvals){
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        ArrayList<String> xvalues = new ArrayList<>();
        for (Integer yval: yvals) {
            yvalues.add(new PieEntry(yval));
        }
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);
        for (String xval: xData) {
            xvalues.add(xval);
        }

        PieData data = new PieData(dataSet);
        data.setValueTypeface(Main.myCustomFont);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        chart.setDescription(null);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
    }

    private SpannableStringBuilder generateCenterSpannableText() {

        SpannableStringBuilder s = new SpannableStringBuilder("SHOW OF STRENGTH \n"+"Day "+day);
        s.setSpan(new RelativeSizeSpan(1.2f), 0, 16, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, 16, 0);
       // s.setSpan(new RelativeSizeSpan(.8f), 16, s.length() - 5, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 17, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 17, s.length(), 0);
        s.setSpan(new CustomTypefaceSpan("", Main.myCustomFont), 0, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return s;
    }
}
