package com.example.myheart;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.widget.Toast;

public class Myprofile extends AppCompatActivity {
   public EditText profmail,profpassword,docmail,address;
   private ImageView prof;
   private TextView name;
    private String photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Intent ii =getIntent();
        String doc = ii.getStringExtra("userdoctormail");
        String pass=ii.getStringExtra("password");
        String add=ii.getStringExtra("address");
        String myemail=ii.getStringExtra("mail");
        final  String id=ii.getStringExtra("id");
        final String name1=ii.getStringExtra("fname");
        final String name2=ii.getStringExtra("lname");



       profmail=findViewById(R.id.profilemail);
       name=findViewById(R.id.nameinprofile);
       name.setText(name1+" "+name2);
       prof=findViewById(R.id.meme);

        Query ref=FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(myemail);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    photo=snapshot.child("photo").getValue().toString();
                    if(photo.equals("0")) {
                        prof.setImageResource(R.drawable.toaddprofile);

                    }
                    else {
                        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        prof.setImageBitmap(decodedByte);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       Button button=findViewById(R.id.save);

       profpassword=findViewById(R.id.profilepassword);
       docmail=findViewById(R.id.profildoctoremail);
       address=findViewById(R.id.profaddress);
       docmail.setText(doc);
       profpassword.setText(pass);
       address.setText(add);
       profmail.setText(myemail);


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference myref=db.getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.getText().toString().trim().length()==0 || docmail.getText().toString().trim().length()==0 ||
                        profpassword.getText().toString().trim().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Make Sure About your data",Toast.LENGTH_LONG).show();
                }
                else
                    {
                    String ad = "users/" + id + "/address";
                    String docm = "users/" + id + "/doctormail";
                    String pas = "users/" + id + "/password";

                    myref.child(ad).setValue(address.getText().toString());
                    myref.child(docm).setValue(docmail.getText().toString());
                    myref.child(pas).setValue(profpassword.getText().toString());
                }
            }
        });
    }
}
