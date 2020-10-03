package com.example.myheart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class emergency extends AppCompatActivity {
     EditText age,weight,height;
     String bpm,bmi,year,value,Pressure,Smoker,Drinker,Athelete,Diabetic;
    RadioGroup gender,pressure,smoker,drinker,athelete,diabetic;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        age=findViewById(R.id.age1);
        weight=findViewById(R.id.weight1);
        height=findViewById(R.id.height1);

         gender=findViewById(R.id.radioGroupforgender1);
         pressure = findViewById(R.id.pressure1);
         smoker = findViewById(R.id.smoker1);
         drinker = findViewById(R.id.drinker1);
         athelete = findViewById(R.id.athlete1);
         diabetic = findViewById(R.id.diabetic1);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Emergency");



        Button submit=findViewById(R.id.checkresult);
        getheart();
        submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(age.getText().toString().trim().length()==0 || weight.getText().toString().trim().length()==0 || height.getText().toString().trim().length()==0)
                 {
                     Toast.makeText(getApplicationContext(),"Make Sure About your data",Toast.LENGTH_LONG).show();
                 }
                 else {

                     year = age.getText().toString();
                     value = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                     if (value.equals("Male")) {
                         value = "1";
                     } else {
                         value = "0";
                     }
                     Pressure = ((RadioButton) findViewById(pressure.getCheckedRadioButtonId())).getText().toString();
                     if (Pressure.equals("Yes")) {
                         Pressure = "1";
                     } else {

                         Pressure = "0";
                     }
                     Smoker = ((RadioButton) findViewById(smoker.getCheckedRadioButtonId())).getText().toString();
                     if (Smoker.equals("Yes")) {
                         Smoker = "1";
                     } else {

                         Smoker = "0";
                     }
                     Drinker = ((RadioButton) findViewById(drinker.getCheckedRadioButtonId())).getText().toString();
                     if (Drinker.equals("Yes")) {
                         Drinker = "1";
                     } else {

                         Drinker = "0";
                     }
                     Athelete = ((RadioButton) findViewById(athelete.getCheckedRadioButtonId())).getText().toString();
                     if (Athelete.equals("Yes")) {
                         Athelete = "1";
                     } else {

                         Athelete = "0";
                     }
                     Diabetic = ((RadioButton) findViewById(diabetic.getCheckedRadioButtonId())).getText().toString();
                     if (Diabetic.equals("Yes")) {
                         Diabetic = "1";
                     } else {

                         Diabetic = "0";
                     }


                     int w = Integer.parseInt(weight.getText().toString());
                     int h = Integer.parseInt(height.getText().toString());
                     int hinm = h / 100;
                     int bm = w / (hinm) * (hinm);
                     if (bm <= 18) {
                         bmi = "0";
                     } else if (bm <= 25) {
                         bmi = "1";
                     } else if (bm <= 35) {
                         bmi = "2";
                     } else if (bm > 35) {
                         bmi = "3";
                     }
                     int hr = Integer.parseInt(bpm);
                     if (hr <= 0) {
                         Toast.makeText(getApplicationContext(), "Your Heart Rate is 0 Please make sure you Touch the Sensor and Wait 5 seconds before Click Here", Toast.LENGTH_LONG).show();
                     } else {
                         Intent topredict = new Intent(getApplicationContext(), pre.class);
                         topredict.putExtra("age", year);
                         topredict.putExtra("heartrate", bpm);
                         topredict.putExtra("gender", value);
                         topredict.putExtra("pressure", Pressure);
                         topredict.putExtra("diabetic", Diabetic);
                         topredict.putExtra("smooker", Smoker);
                         topredict.putExtra("drinker", Drinker);
                         topredict.putExtra("bmi", bmi);
                         topredict.putExtra("athlete", Athelete);
                         topredict.putExtra("key", "em");

                         startActivity(topredict);
                     }
                 }
             }
         });

    }


    void getheart()
    {
        Query ref2= FirebaseDatabase.getInstance().getReference("HeartRate");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    bpm=snapshot.getValue().toString();
                    break;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
