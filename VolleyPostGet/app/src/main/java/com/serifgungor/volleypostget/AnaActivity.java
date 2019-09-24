package com.serifgungor.volleypostget;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnaActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int userid = sharedPreferences.getInt("userid",0);
        String adsoyad = sharedPreferences.getString("adsoyad","");
        String telefonno = sharedPreferences.getString("telefonno","");
        String email = sharedPreferences.getString("email","");

        setTitle(adsoyad);
    }
}
