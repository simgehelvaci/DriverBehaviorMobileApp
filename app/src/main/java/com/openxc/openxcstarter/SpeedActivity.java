package com.openxc.openxcstarter;

import android.os.Bundle;
import android.widget.TextView;

public class SpeedActivity extends BaseActivity {




    private TextView SpeedTxt;
    private TextView SpeedTxt2;
    private TextView AccPedalTxt;
    private TextView AccPedalTxt2;
    private TextView DistanceTxt;
    private TextView DistanceTxt2;
    private TextView TurnSignalTxt;
    private TextView TurnSignalTxt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_layout);

        SpeedTxt= (TextView) findViewById(R.id.speed_Txt);
        SpeedTxt2=(TextView) findViewById(R.id.speed_Txt2);
        AccPedalTxt=(TextView) findViewById(R.id.accelerator_pedalTxt);
        AccPedalTxt2=(TextView) findViewById(R.id.accelerator_pedalTxt2);
        DistanceTxt = (TextView) findViewById(R.id.odometerTxt);
        DistanceTxt2= (TextView) findViewById(R.id.odometerTxt2);
        TurnSignalTxt = (TextView) findViewById(R.id.turnSignalStatusTxt);
        TurnSignalTxt2 = (TextView) findViewById(R.id.turnSignalStatusTxt2);







    }

    @Override
    public void onSpeedUpdate() {
        super.onSpeedUpdate();
        SpeedTxt2.setText(BaseActivity.SPEED);
    }
    @Override
    public void  onAcceleratorPedalPositionUpdate() {
        super.onAcceleratorPedalPositionUpdate();
        AccPedalTxt2.setText(BaseActivity.ACCELERATORPEDALPOSITION);
    }

    @Override
    public void onDistanceUpdate() {
        super.onDistanceUpdate();
        DistanceTxt2.setText(BaseActivity.DISTANCE);
    }
    @Override
    public void onTurnSignalStatusUpdate() {
        super.onDistanceUpdate();
        TurnSignalTxt2.setText(BaseActivity.TURNSIGNALSTATUS);
    }
}
