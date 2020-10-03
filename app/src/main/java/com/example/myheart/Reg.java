package com.example.myheart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Reg extends AppCompatActivity {
private FirebaseAuth mauth;
private DatabaseReference DB;
    private String value,userid ;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onStart() {
        super.onStart();
        if(mauth.getCurrentUser()!=null)
        {
            //handleuser
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Button submit=findViewById(R.id.Submit);

    final     EditText fname=findViewById(R.id.fname);
    final     EditText lname=findViewById(R.id.lname);
    final     EditText email=findViewById(R.id.email);
    final     EditText password=findViewById(R.id.password);
    final     EditText age=findViewById(R.id.age);
    final     RadioButton male=findViewById(R.id.male);
    final     RadioButton female=findViewById(R.id.female);
    final     RadioGroup gender=findViewById(R.id.radioGroupforgender);
    final     RadioGroup pressure = findViewById(R.id.pressure);
    final     RadioGroup smoker = findViewById(R.id.smoker);
    final     RadioGroup drinker = findViewById(R.id.drinker);
    final     RadioGroup athelete = findViewById(R.id.athlete);
    final     RadioGroup diabetic = findViewById(R.id.diabetic);
    final     EditText height=findViewById(R.id.height);
    final     EditText weight=findViewById(R.id.weight);
    final     EditText security=findViewById(R.id.security);
    final     EditText doctormail=findViewById(R.id.doctormail);
    final     EditText address=findViewById(R.id.address);
        mauth= FirebaseAuth.getInstance();
        DB= FirebaseDatabase.getInstance().getReference("users");

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Reg.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        age.setText(date);
                    }
                },year,month,day );
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Fname = fname.getText().toString();
                final String Lname = lname.getText().toString();
                final String mail = email.getText().toString();
                final String pass = password.getText().toString();
                final String Age = age.getText().toString();
                final String docmail = doctormail.getText().toString();
                final String Address = address.getText().toString();
                final String Security=security.getText().toString();
                final String Height=height.getText().toString();
                final String Weghit=weight.getText().toString();
                final String value = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                final String Pressure = ((RadioButton) findViewById(pressure.getCheckedRadioButtonId())).getText().toString();
                final String Smoker = ((RadioButton) findViewById(smoker.getCheckedRadioButtonId())).getText().toString();
                final String Drinker = ((RadioButton) findViewById(drinker.getCheckedRadioButtonId())).getText().toString();
                final String Athelete = ((RadioButton) findViewById(athelete.getCheckedRadioButtonId())).getText().toString();
                final String Diabetic = ((RadioButton) findViewById(diabetic.getCheckedRadioButtonId())).getText().toString();
                final String Photo ="0";

                if(fname.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill Firstname", Toast.LENGTH_SHORT).show();

                }
                else if(security.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill Security key", Toast.LENGTH_SHORT).show();
                }
                else if(lname.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill lastname", Toast.LENGTH_SHORT).show();

                }
                else if(email.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill Email", Toast.LENGTH_SHORT).show();

                }
                else if(password.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill password", Toast.LENGTH_SHORT).show();

                }
                else if(age.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill Age", Toast.LENGTH_SHORT).show();

                }
                else if(doctormail.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill Doctormail", Toast.LENGTH_SHORT).show();

                }
                else if(address.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Reg.this, "Please fill your address", Toast.LENGTH_SHORT).show();

                }
                else {
                    mauth.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        userid = DB.push().getKey();

                                        Patient patient = new Patient(userid, Fname, Lname, mail, pass, Age,value, docmail
                                                , Address , Pressure , Smoker ,Drinker,Athelete,Diabetic ,Photo,Security,Height,Weghit);
                                        DB.child(userid).setValue(patient);
                                        Toast.makeText(Reg.this, "Created Successfully", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(Reg.this, "Email is exist , Register with another email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }



            }
        });

    }


}
