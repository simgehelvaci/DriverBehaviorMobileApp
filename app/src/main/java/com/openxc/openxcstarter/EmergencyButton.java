package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

public class EmergencyButton extends Activity {


    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_button);




        int GET_MY_PERMISSION = 1;

        if(ContextCompat.checkSelfPermission(EmergencyButton.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(EmergencyButton.this,
                    new String[]{Manifest.permission.SEND_SMS},GET_MY_PERMISSION);

        }


                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+905536331118", null, "\"Accident Occured Here: https://www.google.com.tr/maps/@40.9755881,29.2338394,16.65z?hl=tr!\"", null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


    }
    public void sendLocationSMS(String phoneNumber, Location currentLocation) {
        SmsManager smsManager = SmsManager.getDefault();
        StringBuffer smsBody = new StringBuffer();
        smsBody.append("http://maps.google.com?q=");
       // smsBody.append(currentLocation.getLatitude());
        smsBody.append(",");
       // smsBody.append(currentLocation.getLongitude());
        smsManager.sendTextMessage(phoneNumber, null, smsBody.toString(), null, null);
    }
}
