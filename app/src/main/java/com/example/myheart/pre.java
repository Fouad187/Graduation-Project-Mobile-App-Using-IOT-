package com.example.myheart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class pre extends AppCompatActivity{
private  String reso;
    private final String channel_id="Personal_notifications" ;
    private final int notification_id=001;
    Handler handler;
    private DatabaseReference DB;
    userHistory userhistory;
 private  String email;
 private  String bpm;
 private String casee;
 private  String name;
 Bitmap bmp,scale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        ActivityCompat.requestPermissions(this,new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);
      bmp = BitmapFactory.decodeResource(getResources(),R.drawable.header);
      scale=Bitmap.createScaledBitmap(bmp,1200,418,false);

      final   Intent i =getIntent();
        final   String age = i.getStringExtra("age");
        bpm = i.getStringExtra("heartrate");
        final   String gender = i.getStringExtra("gender");
        final String pressure = i.getStringExtra("pressure");
        final String diabetic = i.getStringExtra("diabetic");
        final  String smooker = i.getStringExtra("smooker");
        final  String drinker = i.getStringExtra("drinker");
        final String athlete = i.getStringExtra("athlete");
        final String doctor=i.getStringExtra("doctor");
        final String address=i.getStringExtra("address");
        final String bmi = i.getStringExtra("bmi");
        final  String key=i.getStringExtra("key");
        email=i.getStringExtra("email");
        name=i.getStringExtra("fname")+" "+i.getStringExtra("lname");

        DB= FirebaseDatabase.getInstance().getReference().child("usersHistory");

        //Button pr=findViewById(R.id.pred);
        final TextView textView=findViewById(R.id.predresult);
      //  final TextView textView1=findViewById(R.id.predresulttttt);
        final TextView textView2=findViewById(R.id.pulsenumber);
        //int fn=Integer.parseInt(bpm);

        final PulsatorLayout pulsatorLayout=(PulsatorLayout) findViewById(R.id.pulse);
        pulsatorLayout.start();


       final Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    reso=makePost(age,bpm,gender,pressure,diabetic,smooker,drinker,athlete,bmi);
                    if(reso !=null && reso!="error")
                    {
                        String[] sp = reso.split(":");
                        String[] xp = sp[1].split("]");
                        String x = "\\[";
                        String[] finall = xp[0].split("\\[");
                        reso = finall[1];
                    }





                }
                finally {

                }
            }
        };
        thread.start();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.post(new Runnable() {
                    @Override
                    public void run() {

                        textView2.setText(bpm+" bpm");
                        if(reso !=null && reso.equals("0") && key.equals("use"))
                        {   casee="Abnormal Case";
                            savetohistory();
                            textView.setText("Abnormal Case");


                            Calendar calendar=Calendar.getInstance();
                            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                            String time=format.format(calendar.getTime());
                            String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                            createpdf(i.getStringExtra("fname")+" "+i.getStringExtra("lname"),email,doctor,address,date,time,bpm,pressure,smooker,drinker,athlete,diabetic,bmi,gender,age);
                            String body=makeEmail(i.getStringExtra("fname"),i.getStringExtra("lname"));
                            File file=new File(Environment.getExternalStorageDirectory()+"/Download","/Case.pdf");
                            sendEmail(doctor,body,file);
                            notfication();

                        }
                        else if(reso.equals("1") && key.equals("use"))
                        {
                            casee="Normal Case";
                            savetohistory();
                            textView.setText("Normal Case");

                        }
                        if(reso !=null && reso.equals("0") && key.equals("em"))
                        {
                            textView.setText("Abnormal Case");
                        }

                        else if(reso.equals("1") && key.equals("em"))
                        {
                        textView.setText("Normal Case");

                        }

                    }
                });
            }
        },3000);






         resetbpm();

        File file=new File(Environment.getExternalStorageDirectory()+"/Download","/Case.pdf");
        file.delete();




    }


    private void resetbpm()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference myref=db.getReference();
       myref.child("HeartRate").child("BPM").setValue("0");

    }
    private void sendEmail(String doctor,String Body,File file) {
        //Creating SendMail object
        SendMail sm = new SendMail(this,doctor,"Rescue Your Patient ",Body,file);

        sm.execute();
    }
    private String makeEmail(String fname,String lname)
    {
        String dear="Dear Doctor \nYour Patient : ";
        String makename=fname+" "+lname+"\n";
        String plus=dear+makename+"have Abnormal Case in his Heart rate and her cases in This Report\n";
        return plus;
    }

    private void notfication()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="My Heart";
            String Description="Your Heart rate is Abnormal Please Go to Hospital or Doctor";
            int important= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notficationchannel=new NotificationChannel(channel_id,name,important);
            notficationchannel.setDescription(Description);
            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notficationchannel);
        }
        else {
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,channel_id);
            builder.setSmallIcon(R.drawable.ic_sms_black_24dp);
            builder.setContentTitle("My Heart");
            builder.setContentText("Your Heart rate is Abnormal Please Go to Hospital or Doctor");
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(notification_id,builder.build());
        }
    }

    private void savetohistory()
    {
        Calendar calendar=Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        String time=format.format(calendar.getTime());

        userhistory =new userHistory();

        userhistory.setEmail(email);
        userhistory.setUserName(name);
        userhistory.setHeartRate(bpm);
        userhistory.setDate(date);
        userhistory.setTime(time);
        userhistory.setCasee(casee);
        DB.push().setValue(userhistory);
    }
    private String makePost(String age , String heartrate, String gender , String bloodpressure , String diabetic , String smooker
            , String drinker , String athlete, String bmi )
    {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("age", age)
                    .add("heartrate", heartrate)
                    .add("gender", gender)
                    .add("bloodpressure", bloodpressure)
                    .add("diabetic", diabetic)
                    .add("smooker", smooker)
                    .add("drinker", drinker)
                    .add("athlete", athlete)
                    .add("bmi", bmi)

                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://saka-gp.herokuapp.com/api/?fbcid=IwAR14MoRD-YfTHCFLkD5JRWHVDtw7Lj7gk_FHIMlZG22ezHhznJ0zevfJr9o")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        catch (IOException ex){
            return "error";
        }


    }


    private void createpdf(String patient,String patientmail,String doctormail,String address
            ,String date,String time,String heartrate,String pressure,String smoker,
             String drinker,String athelete,String diabetic,String bmi,String gender,String age)
    {
        if(gender.equals("1"))
        {
            gender="Male";
        }else {
            gender="Female";
        }
        if(pressure.equals("1"))
        {
            pressure="Yes";
        }else{
            pressure="No";
        }
        if(diabetic.equals("1"))
        {
            diabetic="Yes";
        }else{
            diabetic="No";
        }
        if(smoker.equals("1"))
        {
            smoker="Yes";
        }else{
            smoker="No";
        }
        if(drinker.equals("1"))
        {
            drinker="Yes";
        }else{
            drinker="No";
        }
        if(athelete.equals("1"))
        {
            athelete="Yes";
        }else{
            athelete="No";
        }
        PdfDocument myPdfDocoment= new PdfDocument();
        Paint myPaint=new Paint();
        Paint title=new Paint();

        PdfDocument.PageInfo myPageinfo1=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page myPage1=myPdfDocoment.startPage(myPageinfo1);
        Canvas canvas=myPage1.getCanvas();
        canvas.drawBitmap(scale,0,0,myPaint);
        title.setTextAlign(Paint.Align.CENTER);
        title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        title.setTextSize(50);
        title.setColor(Color.RED);
        canvas.drawText("Report For Emergency Case",1200/2,475,title);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Patient Name: "+patient,20,590,myPaint);
        canvas.drawText("Contact Email: "+patientmail,20,640,myPaint);
        canvas.drawText("Doctor Email: "+doctormail,20,690,myPaint);

        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Address: "+address,1200-20,590,myPaint);
        canvas.drawText("Date: "+date,1200-20,640,myPaint);
        canvas.drawText("Time: "+time,1200-20,690,myPaint);


        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(2);
        canvas.drawRect(20,780,1200-20,860,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("Number",40,830,myPaint);
        canvas.drawText("Attributes",200,830,myPaint);
        canvas.drawText("Result",600,830,myPaint);

        canvas.drawLine(190,790,190,840,myPaint);
        canvas.drawLine(590,790,590,840,myPaint);

        canvas.drawText("1.",40,950,myPaint);
        canvas.drawText("Heart Rate",200,950,myPaint);
        canvas.drawText(heartrate,600,950,myPaint);

        canvas.drawText("2.",40,1020,myPaint);
        canvas.drawText("Pressure",200,1020,myPaint);
        canvas.drawText(pressure,600,1020,myPaint);

        canvas.drawText("3.",40,1090,myPaint);
        canvas.drawText("Smoker",200,1090,myPaint);
        canvas.drawText(smoker,600,1090,myPaint);

        canvas.drawText("4.",40,1160,myPaint);
        canvas.drawText("Drinker",200,1160,myPaint);
        canvas.drawText(drinker,600,1160,myPaint);


        canvas.drawText("5.",40,1230,myPaint);
        canvas.drawText("Athelete",200,1230,myPaint);
        canvas.drawText(athelete,600,1230,myPaint);

        canvas.drawText("6.",40,1300,myPaint);
        canvas.drawText("Diabetic",200,1300,myPaint);
        canvas.drawText(diabetic,600,1300,myPaint);

        canvas.drawText("7.",40,1370,myPaint);
        canvas.drawText("BMI",200,1370,myPaint);
        canvas.drawText(bmi,600,1370,myPaint);

        canvas.drawLine(680,1520,1200-20,1520,myPaint);
        canvas.drawText("Age: "+age,700,1570,myPaint);
        canvas.drawText("Gender : "+gender,700,1620,myPaint);

        myPaint.setColor(Color.RED);
        canvas.drawRect(680,1670,1200-20,1770,myPaint);
        myPaint.setColor(Color.WHITE);
        myPaint.setTextSize(50f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Abnormal Case",900,1735,myPaint);

        myPdfDocoment.finishPage(myPage1);
        File file=new File(Environment.getExternalStorageDirectory()+"/Download","/Case.pdf");

        try
        {
         myPdfDocoment.writeTo(new FileOutputStream(file));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        myPdfDocoment.close();





    }
}
