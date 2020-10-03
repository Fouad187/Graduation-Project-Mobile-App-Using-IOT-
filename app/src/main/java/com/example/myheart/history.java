package com.example.myheart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class history extends AppCompatActivity {


    GridView gridView;
    ArrayList<historydata> historylist;
    historygrid adapter = null;
    String email;
    AnyChartView anyChartView;
    int Nonum,Abnum;
    String [] arr={"Normal case","Abnormal"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        anyChartView=findViewById(R.id.any_chart_view);
        Nonum=0 ;
        Abnum=0;
        Intent i = getIntent();
        email = i.getStringExtra("email");

        gridView = (GridView) findViewById(R.id.gridview);
        historylist = new ArrayList<>();
        adapter = new historygrid(this, R.layout.row, historylist);
        gridView.setAdapter(adapter);

        historylist.clear();

        Query ref = FirebaseDatabase.getInstance().getReference("usersHistory").orderByChild("email").equalTo(email);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String bpm=snapshot.child("heartRate").getValue().toString()+" "+"bpm";
                    String date=snapshot.child("date").getValue().toString();
                    String time=snapshot.child("time").getValue().toString();
                    String casee=snapshot.child("casee").getValue().toString();
                    if(casee.equals("Normal Case"))
                    {
                        Nonum++;
                    }
                    else
                    {
                        Abnum++;
                    }
                    historylist.add(new historydata(bpm,date,time,casee));
                }
                adapter.notifyDataSetChanged();
                int [] numbers={Nonum,Abnum};
                List<DataEntry> dataEntries=new ArrayList<>();

                Pie pie =AnyChart.pie();
                for(int i=0 ; i<arr.length; i++)
                {
                    dataEntries.add(new ValueDataEntry(arr[i],numbers[i]));

                }
                pie.data(dataEntries);
                pie.title("History Graph");
                anyChartView.setChart(pie);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }
}