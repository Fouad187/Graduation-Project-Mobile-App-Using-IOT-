package com.example.myheart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;
import okhttp3.Headers;
import okhttp3.FormBody;

import android.os.CountDownTimer;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class User extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 private final String channel_id="Personal_notifications" ;
 private final int notification_id=001;
 private DrawerLayout drawerLayout;
 private NavigationView navigationView;
 private View photo1;
 private String reso;
 private String myemail;
 private String userpassword;
 private String userdoctormail;
 private String useraddress;
    private String userage;
    private String userid;
    private String username;
    private String userlastname;
    private String athlete;
    private String diabetic;
    private String drinker;
    private String height;
    private String weight;
    private String bmi;
    private String gender;
    private String pressure;
    private String smooker;
    private String photo;
    private String bpm;
    private Toolbar toolbar;
    final int REQUEST_CODE_GALLERY = 999;
    ImageView myprofilephoto;
    ImageView img;
    private TextView welcome,timer;
    private Button start;
    private SpaceNavigationView spaceNavigationView;
    private static final long START_TIME_IN_MILLIS = 3*600000;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    private SharedPreferenceConfig sharedPreferenceConfigg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("ATTRIBUTES", R.drawable.account));
        spaceNavigationView.addSpaceItem(new SpaceItem("EMERGENCY", R.drawable.em));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        spaceNavigationView.setCentreButtonIcon(R.drawable.center);
        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.them));
        spaceNavigationView.setInActiveSpaceItemColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.white));

        sharedPreferenceConfigg=new SharedPreferenceConfig(getApplicationContext());
        navigationView = (NavigationView) findViewById(R.id.navigation);
        photo1 = navigationView.inflateHeaderView(R.layout.header);



        //myprofilephoto=findViewById(R.id.myphoto);


        Intent ii =getIntent();
        myemail= ii.getStringExtra("value");
        if(myemail == null)
        {
            myemail=sharedPreferenceConfigg.readmail();

        }
        loaduser();
        welcome=findViewById(R.id.welcome);

        timer=findViewById(R.id.timer);
        start=findViewById(R.id.startandpause);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        drawerLayout=findViewById(R.id.drawer);
        navigationView=(NavigationView) findViewById(R.id.navigation);

       if(navigationView !=null) {
           navigationView.setNavigationItemSelectedListener(this);
       }
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                int year=Integer.parseInt(userage);
                int he=Integer.parseInt(bpm);
                if(year <=0)
                {
                    Toast.makeText(getApplicationContext(),"Your Age was 0 if You think its made by mistake please update your Age",Toast.LENGTH_LONG).show();
                }else if(he<=0 ){
                    Toast.makeText(getApplicationContext(),"Your Heart Rate is 0 Please make sure you Touch the Sensor and Wait 5 seconds before Click Here",Toast.LENGTH_LONG).show();

                }
                else {
                    Intent topredict = new Intent(getApplicationContext(), pre.class);
                    topredict.putExtra("age", userage);
                    topredict.putExtra("heartrate", bpm);
                    topredict.putExtra("gender", gender);
                    topredict.putExtra("pressure", pressure);
                    topredict.putExtra("diabetic", diabetic);
                    topredict.putExtra("smooker", smooker);
                    topredict.putExtra("drinker", drinker);
                    topredict.putExtra("bmi", bmi);
                    topredict.putExtra("athlete", athlete);
                    topredict.putExtra("email", myemail);
                    topredict.putExtra("fname", username);
                    topredict.putExtra("lname", userlastname);
                    topredict.putExtra("doctor", userdoctormail);
                    topredict.putExtra("address",useraddress);
                    topredict.putExtra("key", "use");

                    startActivity(topredict);
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(itemName.equals("EMERGENCY")) {
                    Intent i = new Intent(getApplicationContext(), emergency.class);
                    startActivity(i);
                }else if (itemName.equals("ATTRIBUTES"))
                {
                    Intent j=new Intent(getApplicationContext(),cases.class);
                    j.putExtra("weight",weight);
                    j.putExtra("height",height);
                    j.putExtra("gender",gender);
                    j.putExtra("pressure",pressure);
                    j.putExtra("diabetic",diabetic);
                    j.putExtra("smooker",smooker);
                    j.putExtra("drinker",drinker);
                    j.putExtra("athlete",athlete);
                    j.putExtra("id",userid);

                    startActivity(j);
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if(itemName.equals("EMERGENCY")) {
                    Intent i = new Intent(getApplicationContext(), emergency.class);
                    startActivity(i);
                }else if (itemName.equals("ATTRIBUTES"))
                {
                    Intent j=new Intent(getApplicationContext(),cases.class);
                    j.putExtra("weight",weight);
                    j.putExtra("height",height);
                    j.putExtra("gender",gender);
                    j.putExtra("pressure",pressure);
                    j.putExtra("diabetic",diabetic);
                    j.putExtra("smooker",smooker);
                    j.putExtra("drinker",drinker);
                    j.putExtra("athlete",athlete);
                    j.putExtra("id",userid);

                    startActivity(j);
                }
            }
        });





    }


   public void loaduser()
   {
       Thread thread=new Thread(){
           @Override
           public void run() {
               try{
                   Query ref=FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(myemail);
                   ref.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           for (DataSnapshot snapshot:dataSnapshot.getChildren())
                           {
                               userpassword=snapshot.child("password").getValue().toString();
                               userdoctormail=snapshot.child("doctormail").getValue().toString();
                               username=snapshot.child("fname").getValue().toString();
                               userlastname=snapshot.child("lname").getValue().toString();

                               photo=snapshot.child("photo").getValue().toString();


                               welcome.setText("Welcome "+username);
                               img = (ImageView)photo1.findViewById(R.id.myphoto);
                               img.setImageResource(R.drawable.toaddprofile);

                               TextView tv = (TextView)photo1.findViewById(R.id.nameinheader);
                               tv.setText(username+" "+userlastname);
                               if(photo.equals("0"))
                               {
                                   img.setImageResource(R.drawable.toaddprofile);
                               }else {
                                   byte[] decodedString = Base64.decode(String.valueOf(photo), Base64.DEFAULT);
                                   Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                   img.setImageBitmap(decodedByte);

                               }
                               img.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       ActivityCompat.requestPermissions(
                                               User.this,
                                               new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                               REQUEST_CODE_GALLERY
                                       );
                                   }


                               });
                               useraddress=snapshot.child("address").getValue().toString();
                               userid=snapshot.child("id").getValue().toString();

                               String useragee=snapshot.child("age").getValue().toString();
                               String [] togetyear=useragee.split("/");
                               Calendar calendar=Calendar.getInstance();
                               String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                               String [] togetcurrentyear=date.split(" ");
                               int dateyear=Integer.parseInt(togetyear[2]);
                               int current=Integer.parseInt(togetcurrentyear[3]);
                               int makeyear=current-dateyear;
                               userage=String.valueOf(makeyear);
                               athlete=snapshot.child("athelete").getValue().toString();
                               if(athlete.equals("Yes"))
                               {
                                   athlete="1";
                               }else {
                                   athlete="0";
                               }
                               diabetic=snapshot.child("diabetic").getValue().toString();
                               if(diabetic.equals("Yes"))
                               {
                                   diabetic="1";
                               }else {
                                   diabetic="0";
                               }
                               gender=snapshot.child("gender").getValue().toString();
                               if(gender.equals("Male"))
                               {
                                   gender="1";
                               }else {
                                   gender="0";
                               }
                               height=snapshot.child("height").getValue().toString();
                               weight=snapshot.child("weight").getValue().toString();
                               pressure=snapshot.child("pressure").getValue().toString();
                               if(pressure.equals("Yes"))
                               {
                                   pressure="1";
                               }else {
                                   pressure="0";
                               }
                               smooker=snapshot.child("smoker").getValue().toString();
                               if(smooker.equals("Yes"))
                               {
                                   smooker="1";
                               }else {
                                   smooker="0";
                               }
                               drinker=snapshot.child("drinker").getValue().toString();
                               if(drinker.equals("Yes"))
                               {
                                   drinker="1";
                               }else{
                                   drinker="0";
                               }
                               int w=Integer.parseInt(weight);
                               int h=Integer.parseInt(height);
                               int hinm=h/100;
                               int bm=w/(hinm)*(hinm);
                               if(bm <=18){
                                   bmi="0";
                               }
                               else if(bm<=25)
                               {
                                   bmi="1";
                               }
                               else if (bm<=35)
                               {
                                   bmi="2";
                               }
                               else if (bm>35)
                               {
                                   bmi="3";
                               }


                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
                   Query ref2=FirebaseDatabase.getInstance().getReference("HeartRate");
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
               finally {

               }
           }
       };

      thread.start();


   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                FirebaseDatabase dbbb = FirebaseDatabase.getInstance();
                final DatabaseReference myreff=dbbb.getReference();
                String newimage=imageViewToByte(img);
                String newphoto = "users/" + userid + "/photo";
                myreff.child(newphoto).setValue(newimage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public static String imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId())
      {
          case android.R.id.home:
              drawerLayout.openDrawer(GravityCompat.START);
              return true;



      }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.logout:
                sharedPreferenceConfigg.writeloginstatues(false ," ");
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.myprofile:

                  Intent toprofile=new Intent(getApplicationContext(),Myprofile.class);
                  toprofile.putExtra("userdoctormail",userdoctormail);
                  toprofile.putExtra("mail",myemail);
                  toprofile.putExtra("address",useraddress);
                  toprofile.putExtra("password",userpassword);
                  toprofile.putExtra("id",userid);
                  toprofile.putExtra("fname",username);
                  toprofile.putExtra("lname",userlastname);
                  startActivity(toprofile);

                  break;
            case R.id.Reports:
                Intent toreport=new Intent(getApplicationContext(),report.class);
                startActivity(toreport);
                break;
            case R.id.help:
                Intent tohelp=new Intent(getApplicationContext(),help.class);
                startActivity(tohelp);
                break;
            case R.id.history:
                Intent tohistory=new Intent(getApplicationContext(),history.class);
                tohistory.putExtra("email",myemail);
                startActivity(tohistory);
                break;
        }


        return false;
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                start.setText("Start");
                start.setVisibility(View.INVISIBLE);
            }
        }.start();
        mTimerRunning = true;
        start.setText("pause");
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        start.setText("Start");
    }
 /*   private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    */

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted);
    }


}
