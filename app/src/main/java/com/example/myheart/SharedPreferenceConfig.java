package com.example.myheart;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {



    private SharedPreferences sharedPreferences;
    private Context context;
    private String email;


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public SharedPreferenceConfig(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.Login_Preference),Context.MODE_PRIVATE);


    }
    public void writeloginstatues(boolean statues,String mail)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.Login_statues_preference),statues);
        editor.putString("myemail",mail);
        editor.commit();
    }

    public boolean readLoginstatues()
    {
        boolean statues=false;
        String mail=" ";
        statues=sharedPreferences.getBoolean(context.getResources().getString(R.string.Login_statues_preference),false);
        mail=sharedPreferences.getString("myemail",mail);
        return statues;


    }
    public String readmail()
    {
        String mail=" ";
        mail=sharedPreferences.getString("myemail",mail);
        return mail;

    }
}
