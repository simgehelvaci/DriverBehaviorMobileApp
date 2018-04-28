package com.openxc.openxcstarter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.openxc.openxcstarter.EmergencyButton.telephone1;
import static com.openxc.openxcstarter.EmergencyButton.telephone2;
import static com.openxc.openxcstarter.EmergencyButton.username;

public class Settings extends Activity {

    private TextView userNameTxt;
    private TextView resultTxt;
    private TextView telephone1Txt;
    private TextView telephone2Txt;
    private EditText userNameEditTxt;
    private EditText telephone1EditTxt;
    private EditText telephone2EditTxt;
    private Button buttonSubmit;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.settings_layout);


        userNameTxt= (TextView) findViewById(R.id.userNameTxt);
        telephone1Txt = (TextView) findViewById(R.id.telephone1Txt);
        telephone2Txt= (TextView) findViewById(R.id.telephone2Txt);
        userNameEditTxt = (EditText) findViewById(R.id.userNameEditTxt);
        telephone1EditTxt = (EditText) findViewById(R.id.telephone1EditTxt);
        telephone2EditTxt = (EditText) findViewById(R.id.telephone2EditTxt);
        resultTxt=(TextView) findViewById(R.id.resultTxt);
        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                username=userNameEditTxt.getText().toString();
                EmergencyButton.telephone1=telephone1EditTxt.getText().toString();
                EmergencyButton.telephone2=telephone2EditTxt.getText().toString();
                Toast.makeText(getApplicationContext(), "Changed!"+username+telephone1+telephone2,
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
