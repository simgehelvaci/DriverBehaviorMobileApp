package com.openxc.openxcstarter;

import android.os.Bundle;
import android.widget.TextView;

public class ErrorActivity extends BaseActivity {




    private TextView aggresive_Txt;
    private TextView aggressive_Txt2;
    private TextView harsh_Txt;
    private TextView harsh_Txt2;
    private TextView throttle_Txt;
    private TextView throttle_Txt2;
    private TextView left_signal_Txt;
    private TextView left_signal_Txt2;
    private TextView right_signal_Txt;
    private TextView right_signal_Txt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_layout);

        aggresive_Txt= (TextView) findViewById(R.id.aggresive_Txt);
        aggressive_Txt2=(TextView) findViewById(R.id.aggressive_Txt2);
        harsh_Txt=(TextView) findViewById(R.id.harsh_Txt);
        harsh_Txt2=(TextView) findViewById(R.id.harsh_Txt2);
        throttle_Txt = (TextView) findViewById(R.id.throttle_Txt);
        throttle_Txt2= (TextView) findViewById(R.id.throttle_Txt2);
        right_signal_Txt = (TextView) findViewById(R.id.right_signal_Txt);
        right_signal_Txt2 = (TextView) findViewById(R.id.right_signal_Txt2);
        left_signal_Txt = (TextView) findViewById(R.id.left_signal_Txt);
        left_signal_Txt2 = (TextView) findViewById(R.id.left_signal_Txt2);







    }







    @Override
    public void onStatusAggUpdate() {
        super.onDistanceUpdate();
        String agresif = String.valueOf(BaseActivity.agresifCounter);
        aggressive_Txt2.setText(agresif);
    }
    @Override
    public void onStatusHarshUpdate() {
        super.onDistanceUpdate();
        String harsh = String.valueOf(BaseActivity.harshBrakeCounter);
        harsh_Txt2.setText(harsh);
    }
    @Override
    public void onStatusNeutUpdate() {
        super.onDistanceUpdate();
        String throttle = String.valueOf(BaseActivity.aragazCounter);
        throttle_Txt2.setText(throttle);
    }
    @Override
    public void onStatusLeftSignalMisuseUpdate() {
        super.onDistanceUpdate();
        String left = String.valueOf(BaseActivity.wrongLeftSignalCounter);

        left_signal_Txt2.setText(left);
    }
    @Override
    public void onStatusRightSignalMisuseUpdate() {
        super.onDistanceUpdate();
        String right = String.valueOf(BaseActivity.wrongRightSignalCounter);
        right_signal_Txt2.setText(right);
    }

}
