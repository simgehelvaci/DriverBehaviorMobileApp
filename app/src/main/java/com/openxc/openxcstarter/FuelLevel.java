package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FuelLevel extends BaseActivity {



    private TextView FuelLevelTxt;
    private TextView TurnSignalTxt;
    private TextView SpeedTxt;
    private TextView IgnitionTxt;
    private TextView DistanceTxt;
    private TextView TurnSignalTxt2;
    private TextView FuelLevelTxt2;
    private TextView SpeedTxt2;
    private TextView IgnitionTxt2;
    private TextView DistanceTxt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_layout);

        FuelLevelTxt2= (TextView) findViewById(R.id.fuel_levelTxt);
        SpeedTxt2 = (TextView) findViewById(R.id.speedTxt);
        IgnitionTxt2= (TextView) findViewById(R.id.ignitionStatusTxt);
        DistanceTxt2 = (TextView) findViewById(R.id.odometerTxt);
        TurnSignalTxt=(TextView)findViewById(R.id.turnSignalStatusTxt);
        TurnSignalTxt2=(TextView)findViewById(R.id.turnSignalStatusTxt2);

        FuelLevelTxt= (TextView) findViewById(R.id.fuel_levelTxt2);
        SpeedTxt = (TextView) findViewById(R.id.speedTxt2);
        IgnitionTxt= (TextView) findViewById(R.id.ignitionStatusTxt2);
        DistanceTxt = (TextView) findViewById(R.id.odometerTxt2);






    }
    @Override
    public void onFuelLevelUpdate() {
        super.onDistanceUpdate();
        FuelLevelTxt.setText(BaseActivity.FUEL);
    }
    @Override
    public void onSpeedUpdate() {
        super.onSpeedUpdate();
        SpeedTxt.setText(BaseActivity.SPEED);
    }
    @Override
    public void onIgnitionUpdate() {
        super.onIgnitionUpdate();
        IgnitionTxt.setText(BaseActivity.IGNITION);
    }
    @Override
    public void onDistanceUpdate() {
        super.onDistanceUpdate();
        DistanceTxt.setText(BaseActivity.DISTANCE);
    }
    @Override
    public void onTurnSignalStatusUpdate() {
        super.onDistanceUpdate();
        TurnSignalTxt2.setText(BaseActivity.TURNSIGNALSTATUS);
    }
}
