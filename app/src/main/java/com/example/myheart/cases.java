package com.example.myheart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cases extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        Intent i =getIntent();

        String weight= i.getStringExtra("weight");
        String height= i.getStringExtra("height");
        String gender=i.getStringExtra("gender");
        String pressure= i.getStringExtra("pressure");
        String diabetic=i.getStringExtra("diabetic");
        String smooker=i.getStringExtra("smooker");
        String drinker=i.getStringExtra("drinker");
        String athlete=i.getStringExtra("athlete");
    final     String id=i.getStringExtra("id");

    final    TextView w=findViewById(R.id.weightcase);
        w.setText(weight);
    final     TextView h=findViewById(R.id.heightcase);
        h.setText(height);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Attributes");



        final RadioButton gendermale=findViewById(R.id.malecase);
        RadioButton genderfemale=findViewById(R.id.femalecase);

        if(gender.equals("1"))
        {
            gendermale.setChecked(true);
            genderfemale.setChecked(false);
        }else {
            genderfemale.setChecked(true);
            gendermale.setChecked(false);
        }

        final RadioButton pressureyes=findViewById(R.id.pressureyescase);
        RadioButton pressureno=findViewById(R.id.pressurenocase);
        if(pressure.equals("1"))
        {
            pressureyes.setChecked(true);
        }else {
            pressureno.setChecked(true);
        }

        final RadioButton diabeticyes=findViewById(R.id.diabeticyescase);
        RadioButton diabeticno=findViewById(R.id.diabeticnocase);
        if(diabetic.equals("1"))
        {
            diabeticyes.setChecked(true);
        }else
        {
            diabeticno.setChecked(true);
        }

        final RadioButton smokeryes=findViewById(R.id.smokeryescase);
        RadioButton smokerno=findViewById(R.id.smokernocase);
       if(smooker.equals("1"))
       {
           smokeryes.setChecked(true);
       }
       else{
           smokerno.setChecked(true);
       }
        final RadioButton drinkeryes=findViewById(R.id.drinkeryescase);
        RadioButton drinkerno=findViewById(R.id.drinkernocase);
       if(drinker.equals("1"))
       {
           drinkeryes.setChecked(true);
       }else
       {
           drinkerno.setChecked(true);
       }
        final RadioButton athleteyes=findViewById(R.id.athleteyescase);
        RadioButton athelteno=findViewById(R.id.athletenocase);

       if(athlete.equals("1"))
       {
           athleteyes.setChecked(true);
       }else
       {
           athelteno.setChecked(true);
       }
        Button update=findViewById(R.id.updatecase);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference myref=db.getReference();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(w.getText().toString().trim().length()==0 || h.getText().toString().trim().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Make Sure about height and width",Toast.LENGTH_LONG).show();
                }else {
                    String newweight = "users/" + id + "/weight";
                    String newheight = "users/" + id + "/height";

                    myref.child(newweight).setValue(w.getText().toString());
                    myref.child(newheight).setValue(h.getText().toString());

                    String newgender = "users/" + id + "/gender";
                    if (gendermale.isChecked()) {
                        myref.child(newgender).setValue("Male");

                    } else {
                        myref.child(newgender).setValue("Female");
                    }
                    String newpressure = "users/" + id + "/pressure";
                    if (pressureyes.isChecked()) {
                        myref.child(newpressure).setValue("Yes");

                    } else {
                        myref.child(newpressure).setValue("No");

                    }
                    String newsmoker = "users/" + id + "/smoker";
                    if (smokeryes.isChecked()) {
                        myref.child(newsmoker).setValue("Yes");

                    } else {
                        myref.child(newsmoker).setValue("No");
                    }
                    String newdrinker = "users/" + id + "/drinker";
                    if (drinkeryes.isChecked()) {
                        myref.child(newdrinker).setValue("Yes");

                    } else {
                        myref.child(newdrinker).setValue("No");
                    }
                    String newdiabetic = "users/" + id + "/diabetic";
                    if (diabeticyes.isChecked()) {
                        myref.child(newdiabetic).setValue("Yes");

                    } else {
                        myref.child(newdiabetic).setValue("No");

                    }
                    String newathlete = "users/" + id + "/athelete";
                    if (athleteyes.isChecked()) {
                        myref.child(newathlete).setValue("Yes");

                    } else {
                        myref.child(newathlete).setValue("No");

                    }

                }


            }
        });




    }
}
