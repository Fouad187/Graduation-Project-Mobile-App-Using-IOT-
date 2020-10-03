package com.example.myheart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class forget extends AppCompatActivity {

    private  EditText email,key;
    private TextView yourpassword;
    private Button check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        email=findViewById(R.id.resetmail);
        key=findViewById(R.id.securitykey);
        yourpassword=findViewById(R.id.yourpassword);
        check=findViewById(R.id.checksec);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().length()==0)
                {
                    Toast.makeText(forget.this, "Please fill your email", Toast.LENGTH_SHORT).show();
                }
                else if (key.getText().toString().trim().length()==0)
                {
                    Toast.makeText(forget.this, "Please fill your Security key", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        Query ref = FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(email.getText().toString());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    String pass = snapshot.child("password").getValue().toString();
                                    String key1 = snapshot.child("security").getValue().toString();

                                    if(key1.equals(key.getText().toString()))
                                    {
                                        yourpassword.setVisibility(View.VISIBLE);
                                        yourpassword.setText("Your Password : "+pass);

                                    }else {

                                        yourpassword.setVisibility(View.VISIBLE);
                                        yourpassword.setText("incorrect key ");

                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e)
                    {
                        Toast.makeText(forget.this,"wrong Mail or key",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
}
