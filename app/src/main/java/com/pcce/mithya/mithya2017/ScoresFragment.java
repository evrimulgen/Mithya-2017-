package com.pcce.mithya.mithya2017;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
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

import static com.pcce.mithya.mithya2017.StrengthFragment.MY_COLORS;

public class ScoresFragment extends Fragment {

    DatabaseReference mDatabase, score;
    private PieChart chart;

    int COMPSCORE, ITSCORE, MECHSCORE, ETCSCORE;
    private String[] xData = {"Information Technology", "Mechanical Engineering", "Computer Engineering", "Electronics and Telecommunications"};
    Activity ctx;
    String day;

    private TextView it, comp, mech, etc;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("events");


        //  Home.toolTitle.setText("Scores - Mithya 2017");

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
//        chart.setCenterText(generateCenterSpannableText());
        chart.setHoleColor(Color.parseColor("#ecf0f1"));
        // loadChart(yData);
        chart.setEntryLabelColor(Color.WHITE);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                COMPSCORE = 0;
                ITSCORE = 0;
                MECHSCORE = 0;
                ETCSCORE = 0;


                getData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;


    }

    void getData() {

        mDatabase.child("Day1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            Event event = snapshot.getValue(Event.class);
                            Log.d("Scores", "" + event.getName());


                            COMPSCORE = COMPSCORE + event.getScores().getCOMP();
                            ITSCORE = ITSCORE + event.getScores().getIT();
                            MECHSCORE = MECHSCORE + event.getScores().getMECH();
                            ETCSCORE = ETCSCORE + event.getScores().getETC();


                        }

                        mDatabase.child("Day 2")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                            Event event = snapshot.getValue(Event.class);
                                            Log.d("Scores", "" + event.getName());


                                            COMPSCORE = COMPSCORE + event.getScores().getCOMP();
                                            ITSCORE = ITSCORE + event.getScores().getIT();
                                            MECHSCORE = MECHSCORE + event.getScores().getMECH();
                                            ETCSCORE = ETCSCORE + event.getScores().getETC();


                                        }

                                        mDatabase.child("Day 3")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                            Event event = snapshot.getValue(Event.class);
                                                            Log.d("Scores", "" + event.getName());


                                                            COMPSCORE = COMPSCORE + event.getScores().getCOMP();
                                                            ITSCORE = ITSCORE + event.getScores().getIT();
                                                            MECHSCORE = MECHSCORE + event.getScores().getMECH();
                                                            ETCSCORE = ETCSCORE + event.getScores().getETC();


                                                        }

                                                        mDatabase.child("Day 4")
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                                            Event event = snapshot.getValue(Event.class);
                                                                            Log.d("Scores", "" + event.getName());


                                                                            COMPSCORE = COMPSCORE + event.getScores().getCOMP();
                                                                            ITSCORE = ITSCORE + event.getScores().getIT();
                                                                            MECHSCORE = MECHSCORE + event.getScores().getMECH();
                                                                            ETCSCORE = ETCSCORE + event.getScores().getETC();


                                                                        }
                                                                        Integer[] yData = {ITSCORE, MECHSCORE, COMPSCORE, ETCSCORE};

                                                                        loadChart(yData);
                                                                        Log.d("Scores", "" + COMPSCORE);
                                                                        Log.d("Scores", "" + ITSCORE);
                                                                        Log.d("Scores", "" + MECHSCORE);
                                                                        Log.d("Scores", "" + ETCSCORE);


                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                    }


                                                                });


                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }


                                                });


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }


                                });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }


                });


    }


    private void loadChart(Integer[] yvals) {
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        ArrayList<String> xvalues = new ArrayList<>();
        for (Integer yval : yvals) {
            yvalues.add(new PieEntry(yval));
        }
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);
        for (String xval : xData) {
            xvalues.add(xval);
        }

        PieData data = new PieData(dataSet);
        data.setValueTypeface(Main.myCustomFont);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        chart.setDescription(null);
        chart.getLegend().setEnabled(false);
        chart.invalidate();
        chart.setData(data);
    }


//    private SpannableStringBuilder generateCenterSpannableText() {
//
//        SpannableStringBuilder s = new SpannableStringBuilder("SCORES SO FAR");
//        s.setSpan(new RelativeSizeSpan(1.2f), 0, 16, 0);
//        s.setSpan(new StyleSpan(Typeface.BOLD), 0, 16, 0);
////        s.setSpan(new RelativeSizeSpan(.8f), 16, s.length() - 5, 0);
//        s.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 17, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 17, s.length(), 0);
//        s.setSpan(new CustomTypefaceSpan("", Main.myCustomFont), 0, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        return s;
//    }

}
