package com.pcce.mithya.mithya2017;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointD;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.pcce.mithya.mithya2017.StrengthFragment.MY_COLORS;

public class ScoresFragment extends Fragment {

    DatabaseReference mDatabase, score;
    private PieChart chart;

    int COMPSCORE, ITSCORE, MECHSCORE, ETCSCORE;
    private String[] xData = {"Information Technology", "Mechanical Engineering", "Computer Engineering", "Electronics and Telecommunications"};
    Activity ctx;
    String day;
    RecyclerView pointsrecyler;
    List<Ponts> list = new ArrayList<>();
    private ScoreRecylerAdapter mAdapter;
    private boolean bit=false,bmech=false,bcomp=false,betc=false;

    static int its, comps, mechs, etcs;

    private TextView it, comp, mech, etc;
    NestedScrollView scrollView;
    TextView scoretext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);
        Home.imageadd.setVisibility(View.INVISIBLE);
        pointsrecyler = (RecyclerView)view.findViewById(R.id.scoredetail);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        pointsrecyler.setLayoutManager(mLayoutManager);
        scrollView =(NestedScrollView)view.findViewById(R.id.scrollView);
        scrollView.setSmoothScrollingEnabled(true);
        scoretext =(TextView)view.findViewById(R.id.scoretext);

        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mAdapter = new ScoreRecylerAdapter(list, getActivity());
        pointsrecyler.setAdapter(mAdapter);



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


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
               // Toast.makeText(getActivity(), String.valueOf(h.getX()), Toast.LENGTH_LONG).show();
                list.clear();
                scoretext.setVisibility(View.VISIBLE);

             //   mAdapter.notifyDataSetChanged();
                if (h.getX() == 0.0f) {
                    bit =true;
                    bmech =false;
                    bcomp =false;
                    betc =false;
                }
                 if(h.getX()==1.0f)
                {
                    bmech =true;
                    bit =false;
                    bcomp =false;
                    betc =false;
                }
                if(h.getX()==2.0f)
                {
                    bcomp =true;
                    bit =false;
                    bmech =false;
                    betc =false;
                }
                 if(h.getX()==3.0f)
                {

                    betc =true;
                    bit =false;
                    bcomp =false;
                    bmech =false;
                }

                mDatabase.child("Day1")
                        .addValueEventListener(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {

                                                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                         //  list.clear();

                                                           // Event event = snapshot.getValue(Event.class);
                                                           // Log.d("Scores", "" + event.getName());

                                                           Event event = snapshot.getValue(Event.class);
                                                           Log.d("Scores", "" + event.getName() + event.getScores().getCOMP());
                                                           if(bit==true) {
                                                               if (event.getScores().getIT() > 0) {
                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getIT())));
                                                               }
                                                           }
                                                          else if(bmech==true) {
                                                               if (event.getScores().getMECH() > 0) {
                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getMECH())));
                                                               }
                                                           }
                                                         else  if(bcomp==true) {
                                                               if (event.getScores().getCOMP() > 0) {
                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getCOMP())));
                                                               }
                                                           }
                                                        else   if(betc==true) {
                                                               if (event.getScores().getETC() > 0) {
                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getETC())));
                                                               }
                                                           }






                                                       }
                                                       mAdapter = new ScoreRecylerAdapter(list, getActivity());
                                                       pointsrecyler.setAdapter(mAdapter);
                                                       mAdapter.notifyDataSetChanged();
                                            //           pointsrecyler.requestFocus();


                                                       mDatabase.child("Day 2")
                                                               .addValueEventListener(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                                           // Event event = snapshot.getValue(Event.class);
                                                                           // Log.d("Scores", "" + event.getName());

                                                                           Event event = snapshot.getValue(Event.class);
                                                                           Log.d("Scores", "" + event.getName() + event.getScores().getCOMP());
                                                                           if(bit==true) {
                                                                               if (event.getScores().getIT() > 0) {
                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getIT())));
                                                                               }
                                                                           }
                                                                           else if(bmech==true) {
                                                                               if (event.getScores().getMECH() > 0) {
                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getMECH())));
                                                                               }
                                                                           }
                                                                           else  if(bcomp==true) {
                                                                               if (event.getScores().getCOMP() > 0) {
                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getCOMP())));
                                                                               }
                                                                           }
                                                                           else   if(betc==true) {
                                                                               if (event.getScores().getETC() > 0) {
                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getETC())));
                                                                               }
                                                                           }



                                                                           event.getScores().getCOMP();


                                                                       }
                                                                       mAdapter = new ScoreRecylerAdapter(list, getActivity());
                                                                       pointsrecyler.setAdapter(mAdapter);
                                                                       mAdapter.notifyDataSetChanged();
                                                            //           pointsrecyler.requestFocus();


                                                                       mDatabase.child("Day 3")
                                                                               .addValueEventListener(new ValueEventListener() {
                                                                                   @Override
                                                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                                                           // Event event = snapshot.getValue(Event.class);
                                                                                           // Log.d("Scores", "" + event.getName());

                                                                                           Event event = snapshot.getValue(Event.class);
                                                                                           Log.d("Scores", "" + event.getName() + event.getScores().getCOMP());
                                                                                           if(bit==true) {
                                                                                               if (event.getScores().getIT() > 0) {
                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getIT())));
                                                                                               }
                                                                                           }
                                                                                           else if(bmech==true) {
                                                                                               if (event.getScores().getMECH() > 0) {
                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getMECH())));
                                                                                               }
                                                                                           }
                                                                                           else  if(bcomp==true) {
                                                                                               if (event.getScores().getCOMP() > 0) {
                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getCOMP())));
                                                                                               }
                                                                                           }
                                                                                           else   if(betc==true) {
                                                                                               if (event.getScores().getETC() > 0) {
                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getETC())));
                                                                                               }
                                                                                           }


                                                                                           event.getScores().getCOMP();


                                                                                       }
                                                                                       mAdapter = new ScoreRecylerAdapter(list, getActivity());
                                                                                       pointsrecyler.setAdapter(mAdapter);
                                                                                       mAdapter.notifyDataSetChanged();
                                                                                //       pointsrecyler.requestFocus();


                                                                                       mDatabase.child("Day 4")
                                                                                               .addValueEventListener(new ValueEventListener() {
                                                                                                   @Override
                                                                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                                                                           // Event event = snapshot.getValue(Event.class);
                                                                                                           // Log.d("Scores", "" + event.getName());

                                                                                                           Event event = snapshot.getValue(Event.class);
                                                                                                           Log.d("Scores", "" + event.getName() + event.getScores().getCOMP());
                                                                                                           if(bit==true) {
                                                                                                               if (event.getScores().getIT() > 0) {
                                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getIT())));
                                                                                                               }
                                                                                                           }
                                                                                                           else if(bmech==true) {
                                                                                                               if (event.getScores().getMECH() > 0) {
                                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getMECH())));
                                                                                                               }
                                                                                                           }
                                                                                                           else  if(bcomp==true) {
                                                                                                               if (event.getScores().getCOMP() > 0) {
                                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getCOMP())));
                                                                                                               }
                                                                                                           }
                                                                                                           else   if(betc==true) {
                                                                                                               if (event.getScores().getETC() > 0) {
                                                                                                                   list.add(new Ponts(event.getName(), String.valueOf(event.getScores().getETC())));
                                                                                                               }
                                                                                                           }





                                                                                                       }
                                                                                                       mAdapter = new ScoreRecylerAdapter(list, getActivity());
                                                                                                       pointsrecyler.setAdapter(mAdapter);
                                                                                                       mAdapter.notifyDataSetChanged();
                                                                                                 //      pointsrecyler.requestFocus();

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

                                                                                mAdapter = new ScoreRecylerAdapter(list, getActivity());
                                                                                pointsrecyler.setAdapter(mAdapter);
                                                                                mAdapter.notifyDataSetChanged();
              //  pointsrecyler.requestFocus();









            }
                            @Override
            public void onNothingSelected() {
                             //   list.clear();
            }
        });

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


}
