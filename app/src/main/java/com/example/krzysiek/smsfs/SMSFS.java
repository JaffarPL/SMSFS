package com.example.krzysiek.smsfs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.IntentFilter;
import android.widget.TextView;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSFS extends AppCompatActivity {
    SmsListener smsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsfs);
        smsListener = new SmsListener();
        smsListener.AppCompatActivity = this;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");;
        this.registerReceiver(smsListener, filter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            TextView tv = (TextView) findViewById(R.id.statusview);
            tv.setText("premission granted");
        } else {
            //requestCameraPermission();
            TextView tv = (TextView) findViewById(R.id.statusview);
            tv.setText("premission not granted");
            ActivityCompat.requestPermissions(SMSFS.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},18);
        }
    }

    public boolean containsIgnoreCase( String haystack, String needle ) {
        if(needle.equals(""))
            return true;
        if(haystack == null || needle == null || haystack .equals(""))
            return false;
        Pattern p = Pattern.compile(needle,Pattern.CASE_INSENSITIVE+ Pattern.LITERAL);
        Matcher m = p.matcher(haystack);
        return m.find();
    }

    public void smsInterpreter(String smsPayload){
        if(containsIgnoreCase(smsPayload,"rozbrojony")){
            updateTextView("Alarm jest rozbrojony.");
        }
        else if(containsIgnoreCase(smsPayload,"uzbrojony")){
            updateTextView("Alarm jest uzbrojony.");
        }
        else {updateTextView(smsPayload);}
    }

    public void updateLEDicon(String kolorek){

    }

    public void updateTextView(String tresc){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            TextView tv = (TextView) findViewById(R.id.statusview);
            tv.setText(tresc);
        } else {

        }
    }

    //private void requestCameraPermission() {
    //    ActivityCompat.requestPermissions(AppCompatActivity.this,
    //            new String[]{Manifest.permission.RECEIVE_SMS},
    //            PERMISSION_REQUEST_RECEIVE_SMS);
    //}
}
