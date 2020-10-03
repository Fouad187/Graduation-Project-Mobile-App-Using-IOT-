package com.example.myheart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog Loading;
    private EditText Email , password;
    private FirebaseAuth fAuth;
    private String x;
    private CheckBox checkBox;
    private SharedPreferenceConfig preferenceConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         checkBox=(CheckBox)findViewById(R.id.chec);

        preferenceConfig=new SharedPreferenceConfig(getApplicationContext());

      if(preferenceConfig.readLoginstatues())
      {
          Intent i=new Intent(getApplicationContext(),User.class);
          i.putExtra("valuee" , preferenceConfig.getEmail());
          startActivity(i);
          finish();


      }



        Email = (EditText) findViewById(R.id.usermail);
        password =(EditText) findViewById(R.id.userpassword);
        fAuth = FirebaseAuth.getInstance();
        TextView textView=(TextView) findViewById(R.id.toreg);
        TextView textView1=(TextView) findViewById(R.id.forget);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Reg.class);
                startActivity(i);
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),forget.class);
                startActivity(i);
            }
        });


        Button join=findViewById(R.id.login);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mail = Email.getText().toString();
                final String pass = password.getText().toString();
                if (mail.trim().length()==0)
                {
                    Toast.makeText(MainActivity.this,"please enter your Email",Toast.LENGTH_LONG).show();
                }
                else if (pass.trim().length()==0)
                {
                    Toast.makeText(MainActivity.this,"please enter your Password",Toast.LENGTH_SHORT).show();
                }
                else {


                    fAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                Patient patient = new Patient();
                                x = patient.getEmail();


                                Intent i = new Intent(getApplicationContext(), User.class);
                                i.putExtra("value", mail);
                                startActivity(i);
                                if (checkBox.isChecked()) {

                                    preferenceConfig.writeloginstatues(true, mail);
                                    finish();
                                }
                                Email.setText(null);
                                password.setText(null);
                                checkBox.setChecked(false);

                            } else {
                                Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }



            }
        });
    }


}
