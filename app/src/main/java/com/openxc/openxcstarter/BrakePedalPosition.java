package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BrakePedalPosition extends BaseActivity {



    private TextView gearTxt;
    private TextView gearTxt2;
    private TextView acceleratorTxt;
    private TextView acceleratorTxt2;
    private TextView brakePedTxt;
    private TextView brakePedTxt2;
    private TextView steeringWheelAngleTxt;
    private TextView steeringWheelAngleTxt2;
    private TextView engineSpeedTxt;
    private TextView engineSpeedTxt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brakepedal_layout);



        gearTxt= (TextView) findViewById(R.id.gear_positionTxt);
        acceleratorTxt = (TextView) findViewById(R.id.gear_positionTxt2);
        brakePedTxt = (TextView) findViewById(R.id.brake_pedal_positionTxt);
        gearTxt2= (TextView) findViewById(R.id.gear_positionTxt2);
        acceleratorTxt2 = (TextView) findViewById(R.id.accelerator_pedalTxt2);
        brakePedTxt2 = (TextView) findViewById(R.id.brake_pedal_positionTxt2);
        engineSpeedTxt = (TextView) findViewById(R.id.engine_speedTxt);
        engineSpeedTxt2 = (TextView) findViewById(R.id.engine_speedTxt2);
        steeringWheelAngleTxt = (TextView) findViewById(R.id.steering_wheel_angleTxt);
        steeringWheelAngleTxt2 = (TextView) findViewById(R.id.steering_wheel_angleTxt2);



    }
    @Override
    public void onBrakePedalPositionUpdate() {
        super.onBrakePedalPositionUpdate();
        brakePedTxt2.setText(BaseActivity.BRAKEPEDALPOSITION);
    }
    @Override
    public void onGearPositionUpdate() {
        super.onGearPositionUpdate();
         gearTxt2.setText(BaseActivity.GEAR);
    }
    @Override
    public void  onAcceleratorPedalPositionUpdate() {
        super.onAcceleratorPedalPositionUpdate();
        acceleratorTxt2.setText(BaseActivity.ACCELERATORPEDALPOSITION);
    }
    @Override
    public void  onEngineSpeedUpdate() {
        super.onAcceleratorPedalPositionUpdate();
        engineSpeedTxt2.setText(BaseActivity.ENGINESPEED);
    }
    @Override
    public void  onSteeringWheelAngleUpdate() {
        super.onAcceleratorPedalPositionUpdate();
        steeringWheelAngleTxt2.setText(BaseActivity.STEERINGWHEELANGLE);
    }
}
