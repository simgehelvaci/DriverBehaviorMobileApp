package com.openxc.openxcstarter;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.openxc.VehicleManager;
import com.openxc.measurements.FuelLevel;
import com.openxc.measurements.AcceleratorPedalPosition;
import com.openxc.measurements.BrakePedalStatus;
import com.openxc.measurements.EngineSpeed;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.TransmissionGearPosition;
import com.openxc.measurements.VehicleSpeed;
import com.openxc.measurements.IgnitionStatus;
import com.openxc.measurements.SteeringWheelAngle;
import com.openxc.units.Boolean;
import com.openxc.measurements.TurnSignalStatus;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "StarterActivity";

    private String detectedSignalName;
    private String detectedBirthMiliSeconds;
    private String currentSignalName;
    private String currentBirthMiliSeconds;



    private int dataCount = 0;
    private List<Pair<Double, Long>> timeSpeedList = new ArrayList<>();
    private AlertDialog alertDialog;
    private VehicleManager mVehicleManager;
    private long gForceTimer = -1;
    private double gForceVelocity = -1;
    private final double gConstant = 9.80665;
    public static int statusPercentage = 100;
    private String gearPosition = "neutral";
    private Double pedalPos = 0.0;
    public static int agresifCounter = 0;
    public static int harshBrakeCounter = 0;
    public static int aragazCounter = 0;
    public static int wrongLeftSignalCounter=0;
    public static int wrongRightSignalCounter=0;

    public static String IGNITION ;
    public static String DISTANCE;
    public static String FUEL;
    public static String GEAR;
    public static String SPEED;
    public static String ENGINESPEED;
    public static String ACCELERATORPEDALPOSITION;
    public static String BRAKEPEDALPOSITION;
    public static String STEERINGWHEELANGLE;
    public  static Double ODOMETER = 0.0;
    public static String TURNSIGNALSTATUS ="LEFT";
    private int SPEEDLIMIT=70;
    private Double lastDistance = -1.0;
    public static int status = 100;
    public static double HIZ=0.0;

    private double gForce = -1;
    private double weight = 0.5;
    private Double threshold = 360.0;
    private boolean turnStart = false;
    private boolean turnCont = false;
    private boolean turnEnd = false;
    boolean isTurnLeft =false;
    boolean isTurnRight = false;


    private boolean playing = false;
    public static GoogleApiClient apiClient;

    public static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Contact contact = new Contact();





        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(System.currentTimeMillis()%2==0){

                                        TURNSIGNALSTATUS = "NULL";
                                         onTurnSignalStatusUpdate();


                                    }


                                if(System.currentTimeMillis()%3==0) {
                                    TURNSIGNALSTATUS = "RIGHT";
                                    onTurnSignalStatusUpdate();

                                    //writeToFile(TURNSIGNALSTATUS);
                                }
                                else if (System.currentTimeMillis()%3!=0 && System.currentTimeMillis()%2!=0  ){
                                    TURNSIGNALSTATUS ="LEFT";
                                    onTurnSignalStatusUpdate();
                                    //writeToFile(TURNSIGNALSTATUS);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e(TAG, "Could not connect to Play games services");
                        finish();
                    }

                }).build();

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(BaseActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }



    public void checkThreshold (Double angle) {

        if (angle > threshold || angle < -threshold) {

            if (IGNITION.equals("OFF")) {       return;     }

            if (turnStart) {    turnCont = true;    }

            if (turnStart == false) {   turnStart = true;   /*turn baslıyor burdaki saniyeyi AL !! */ }


        } else {

                if (turnCont) {

                        calcTurn(angle);
                        turnEnd = true;   //Burada turn bitmiş oluyor.
                        Toast.makeText(getApplicationContext(), "Turn end ", Toast.LENGTH_SHORT).show();

                }

                turnStart = false;
                turnCont = false;
        }

    }
    public void calcTurn(Double angle) {

        //if (IGNITION.equals("OFF")) {   return;     }
        if (angle > 0) {    isTurnRight = true;
            onStatusRightUpdate();
            Toast.makeText(getApplicationContext(), "Right Turn with Signal"+TURNSIGNALSTATUS , Toast.LENGTH_SHORT).show();



            if(TURNSIGNALSTATUS!="RIGHT"){
                wrongRightSignalCounter++;
                onStatusRightSignalMisuseUpdate();
                Toast.makeText(getApplicationContext(), "Right Signal misuse!", Toast.LENGTH_LONG).show();
            }
       }
        else {  isTurnLeft=true;
            onStatusLeftUpdate();

            Toast.makeText(getApplicationContext(), "Left Turn with Signal"+TURNSIGNALSTATUS, Toast.LENGTH_SHORT).show();

            if(TURNSIGNALSTATUS!="LEFT"){
                wrongLeftSignalCounter++;
                onStatusLeftSignalMisuseUpdate();
                Toast.makeText(getApplicationContext(), "Left Signal misuse!", Toast.LENGTH_LONG).show();
            }

        }

    }


    public int getStatus() {
        return status;
    }

    public void onStatusUpdate() {}
    public void onStatusLeftUpdate() {}
    public void onStatusRightUpdate() {}

    public void onStatusAggUpdate() {}
    public void onStatusHarshUpdate() {}
    public void onStatusNeutUpdate() {}
    public void onStatusLeftSignalMisuseUpdate(){}
    public void onStatusRightSignalMisuseUpdate(){}

    public void onSteeringWheelAngleUpdate() {}

    public void onIgnitionUpdate() {}

    public void onHizUpdate() {}

    public void onDistanceUpdate() {}

    public void onSpeedUpdate() {}

    public void onTurnSignalStatusUpdate(){}

    public void onEngineSpeedUpdate() {}

    public void onGearPositionUpdate() {}

    public void onFuelLevelUpdate() {}

    public void onAcceleratorPedalPositionUpdate() {}

    public void onBrakePedalPositionUpdate() {}



    @Override
    public void onPause() {
        super.onPause();
        // When the activity goes into the background or exits, we want to make
        // sure to unbind from the service to avoid leaking memory
        if(mVehicleManager != null) {
            Log.i(TAG, "Unbinding from Vehicle Manager");

            mVehicleManager.removeListener(EngineSpeed.class,
                    mSpeedListener);
            unbindService(mConnection);
            mVehicleManager = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // When the activity starts up or returns from the background,
        // re-connect to the VehicleManager so we can receive updates.
        if(mVehicleManager == null) {
            Intent intent = new Intent(this, VehicleManager.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    boolean acceleratorPedalOver70 = false;
    long pedalOver70Timer = 0;
    long neutralGasDetectedTimer = -1;
    long brakePedalDetectedTimer = -1;
    long ignitionOffDetectedTimer = -1;
    long ignitionOnDetectedTimer = -1;
    long aggressiveDetectedTimer= -1;
    long harshBrakeDetectedTimer=-1;

    private void neutralGasDetected(long time) {
        if (neutralGasDetectedTimer == -1) {
            neutralGasDetectedTimer = time;
        }
        else if (time - neutralGasDetectedTimer > 3500) {
            //status = statusPercentage - 1;
            onStatusUpdate();
            //Toast.makeText(getApplicationContext(), "Throttle while gear neutral position", Toast.LENGTH_LONG).show();

            neutralGasDetectedTimer = -1;
            aragazCounter++;
            onStatusNeutUpdate();

        }
    }

    private void brakePedalUsageDetected(long time) {
        if (brakePedalDetectedTimer == -1) {
            brakePedalDetectedTimer = time;
        }
        else if (time - brakePedalDetectedTimer > 9500) {
           // Toast.makeText(getApplicationContext(), "Brake Pedal Overused!", Toast.LENGTH_LONG).show();
            status = statusPercentage - 1;
            onStatusUpdate();

            brakePedalDetectedTimer = -1;
        }
    }


    public boolean ignitionOffDetected(long time) {


        if (ignitionOffDetectedTimer == -1) {   ignitionOffDetectedTimer = time;    }

        else if (time - ignitionOffDetectedTimer > 2000) {

            // Toast.makeText(getApplicationContext(), "Ignition Off!", Toast.LENGTH_LONG).show();

            ignitionOffDetectedTimer = -1;
            playing = false;

            //Toast.makeText(getApplicationContext(), "Game over", Toast.LENGTH_LONG).show();
            if(apiClient.isConnected()){
                // good
                Games.Leaderboards.submitScore(apiClient,
                        getString(R.string.leaderboard_my_little_leaderboard),
                        statusPercentage);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();


                //myRef.child("Drivers").child("Driver_id").setValue("55");
                String playerName = Games.Players.getCurrentPlayer(apiClient).getName();
                String playerId = Games.Players.getCurrentPlayer(apiClient).getPlayerId();

                myRef.child("Drivers").child(playerId ).child("Driver_name").setValue(playerName);

                myRef.child("Drivers").child(playerId ).child("Driver_score").setValue(statusPercentage);
                if(statusPercentage>90) {
                    Games.Achievements
                            .unlock(apiClient,
                                    getString(R.string.achievement_perfect_driving));
                }

                if(ODOMETER>1.0) {
                    Games.Achievements
                            .unlock(apiClient,
                                    getString(R.string.achievement_long_road_driver_candidate));
                }
                if(ODOMETER>100.0) {
                    Games.Achievements
                            .unlock(apiClient,
                                    getString(R.string.achievement_long_road_driver_master));
                }
            }else{
                //connect it
                apiClient.connect(GoogleApiClient.SIGN_IN_MODE_REQUIRED);
                Toast.makeText(getApplicationContext(), "SIGN_IN_MODE_REQUIRED", Toast.LENGTH_LONG).show();
            }




        }

        return true;
    }


    public boolean ignitionOnDetected(long time) {

        if (ignitionOnDetectedTimer == -1) {
            ignitionOnDetectedTimer = time;

        }
        else if (time - ignitionOnDetectedTimer > 2000) {

           // Toast.makeText(getApplicationContext(), "Ignition Start!", Toast.LENGTH_LONG).show();

            ignitionOnDetectedTimer = -1;
            if(!playing){
                playing = true;
                Toast.makeText(getApplicationContext(), "Play Start!", Toast.LENGTH_LONG).show();
            }

        }

        return true;
    }


    public boolean agressiveAccelerationDetected(long time) {

        if (aggressiveDetectedTimer == -1) {
            aggressiveDetectedTimer = time;

        }
        else if (time - aggressiveDetectedTimer > 100) {

            // Toast.makeText(getApplicationContext(), "Ignition Start!", Toast.LENGTH_LONG).show();

            aggressiveDetectedTimer = -1;
            agresifCounter++;
            onStatusAggUpdate();
            Toast.makeText(getApplicationContext(), "Agressive Accelerate!", Toast.LENGTH_LONG).show();


        }

        return true;
    }

    public boolean harshBrakeDetected(long time) {

        if (harshBrakeDetectedTimer == -1) {
            harshBrakeDetectedTimer = time;

        }
        else if (time - harshBrakeDetectedTimer > 100) {


            harshBrakeDetectedTimer = -1;
            harshBrakeCounter++;
            onStatusHarshUpdate();
            Toast.makeText(getApplicationContext(), "Harsh Brake!", Toast.LENGTH_LONG).show();


        }

        return true;
    }








    public void checkTimeSince(long time, boolean over70){
        if (!acceleratorPedalOver70 && over70) {
            pedalOver70Timer = time;
            acceleratorPedalOver70 = true;
        }
        else if (acceleratorPedalOver70 && over70){
            if (time - pedalOver70Timer > 7000){

                //Toast.makeText(getApplicationContext(), "Aggressive acceleration", Toast.LENGTH_LONG).show();
                status = statusPercentage - 1;
                onStatusUpdate();
                //Toast.makeText(getApplicationContext(), "Pedal position over 30", Toast.LENGTH_LONG).show();
                acceleratorPedalOver70 = false;

            }
        }
        else {
            acceleratorPedalOver70 = false;
        }

    }



    SteeringWheelAngle.Listener mSteeringWheelAngleListener = new EngineSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final SteeringWheelAngle steeringWheelAngle = (SteeringWheelAngle) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    String s = steeringWheelAngle.getValue().toString();

                    String[] sp = s.split(" ");

                    Double sAngle = Double.parseDouble(sp[0]);

                    STEERINGWHEELANGLE= (steeringWheelAngle.getValue().toString());

                    onSteeringWheelAngleUpdate();

                    checkThreshold(sAngle);


                }
            });
        }
    };

    TurnSignalStatus.Listener mTurnSignalStatusListener = new TurnSignalStatus.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final TurnSignalStatus turnSignalStatus = (TurnSignalStatus) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    TURNSIGNALSTATUS= (turnSignalStatus.getValue().toString());
                    onTurnSignalStatusUpdate();




                }
            });
        }
    };




    EngineSpeed.Listener mEngineSpeedListener = new EngineSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final EngineSpeed engineSpeed = (EngineSpeed) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    ENGINESPEED= (engineSpeed.getValue().toString());
                    onEngineSpeedUpdate();

                }
            });
        }
    };

    IgnitionStatus.Listener mIgnitionListener = new IgnitionStatus.Listener() {
        @Override
        public void receive(Measurement measurement) {
            final IgnitionStatus ignitionStatus = (IgnitionStatus) measurement;
            runOnUiThread(new Runnable() {
                public void run() {

                    IGNITION= (ignitionStatus.getValue().toString());
                    onIgnitionUpdate();
                    if (IGNITION.equalsIgnoreCase("off")) {
                        ignitionOffDetected(ignitionStatus.getBirthtime());
                    }
                    else if (IGNITION.equalsIgnoreCase("start")) {
                        ignitionOnDetected(ignitionStatus.getBirthtime());
                    }



                }
            });
        }
    };

    FuelLevel.Listener mFuelLevelListener = new FuelLevel.Listener() {
        @Override
        public void receive(Measurement measurement) {
            final FuelLevel FuelLevelStatus = (FuelLevel) measurement;
            runOnUiThread(new Runnable() {
                public void run() {

                    FUEL= (FuelLevelStatus.getValue().toString());
                    onFuelLevelUpdate();


                }
            });
        }
    };



    private VehicleSpeed.Listener mSpeedListener = new VehicleSpeed.Listener() {

        @Override
        public void receive(Measurement measurement) {
            final VehicleSpeed speed = (VehicleSpeed) measurement;
            runOnUiThread(new Runnable() {
                public void run() {
                    SPEED= (speed.getValue().toString());
                    onSpeedUpdate();

                    if (dataCount++ % 10 != 0) return;

                    timeSpeedList.add(Pair.create(speed.getValue().doubleValue(), System
                            .currentTimeMillis() / 1000));

                    double averageAcceleration = 0;

                    final double g = 9.81;

                    for (Pair p : timeSpeedList) {
                        Log.e("f", "" + p.first);
                        Log.e("s", "" + p.second);
                    }
                    for (int i = timeSpeedList.size() - 1; i > Math.max(timeSpeedList.size() - 10,
                            0); i--) {
                                        Pair<Double, Long> p1 = timeSpeedList.get(i);
                                        Pair<Double, Long> p2 = timeSpeedList.get(i - 1);
                                        averageAcceleration += (p1.first - p2.first) / (p1.second -
                                                p2.second);
                                        Log.e("avg", "" + averageAcceleration);
                    }

                    averageAcceleration /= Math.min(10, timeSpeedList.size());

                    Log.e("avgAcceleration", "" + averageAcceleration);

                    if (averageAcceleration > 5) {

                        agressiveAccelerationDetected(speed.getBirthtime());



                    } else if (averageAcceleration < -0.8 * 5 ) {

                        harshBrakeDetected(speed.getBirthtime());

                    } else if (averageAcceleration < -1.2 * 5) {

                        //Toast.makeText(getApplicationContext(), "Emergency! " +
                          //      "car!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    };



    AcceleratorPedalPosition.Listener mAcceleratorPedalPositionListener = new AcceleratorPedalPosition.Listener() {
        @Override
        public void receive(Measurement measurement) {

            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final AcceleratorPedalPosition position = (AcceleratorPedalPosition) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    pedalPos = position.getValue().doubleValue();

                    ACCELERATORPEDALPOSITION = pedalPos.toString();
                    onAcceleratorPedalPositionUpdate();
                    onHizUpdate();
                    //checkTimeSince(position.getBirthtime(), true);

                    if (gearPosition.equalsIgnoreCase("neutral")) {
                        neutralGasDetected(position.getBirthtime());
                    }

                    if (pedalPos > 30) {
                        checkTimeSince(position.getBirthtime(), true);

                    }
                    else if (pedalPos <= 30) {
                        checkTimeSince(0, false);
                    }


                }
            });
        }
    };

    private void distanceTravelled(Double d) {
        if (lastDistance < 0) {
            lastDistance = d;
        }
        else if (d - lastDistance > 10.9) {

                status=statusPercentage ++;
                lastDistance = -1.0;
                Toast.makeText(getApplicationContext(), "+1 Point Long Driving!", Toast.LENGTH_SHORT).show();

        }
    }

    BrakePedalStatus.Listener mBrakePedalStatusListener = new BrakePedalStatus.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final BrakePedalStatus position = (BrakePedalStatus) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    Boolean brakePos = position.getValue();


                    BRAKEPEDALPOSITION = brakePos.toString();
                    onBrakePedalPositionUpdate();

                    if (brakePos.booleanValue()) {
                        brakePedalUsageDetected(position.getBirthtime());
                    }

                }
            });
        }
    };

    Odometer.Listener mOdometerListener = new Odometer.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final Odometer distance = (Odometer) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    DISTANCE=distance.getValue().toString();
                    ODOMETER =distance.getValue().doubleValue();
                    onDistanceUpdate();
                    Double d = distance.getValue().doubleValue();
                    distanceTravelled(d);


                }
            });
        }
    };

    TransmissionGearPosition.Listener mGearPositionListener = new TransmissionGearPosition.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final TransmissionGearPosition position = (TransmissionGearPosition) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
           runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    gearPosition = position.toString();
                    GEAR=position.toString();
                    onGearPositionUpdate();


                }
            });


        }
    };





    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the VehicleManager service is
        // established, i.e. bound.
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.i(TAG, "Bound to VehicleManager");
            // When the VehicleManager starts up, we store a reference to it
            // here in "mVehicleManager" so we can call functions on it
            // elsewhere in our code.
            mVehicleManager = ((VehicleManager.VehicleBinder) service)
                    .getService();

            // We want to receive updates whenever the EngineSpeed changes. We
            // have an EngineSpeed.Listener (see above, mSpeedListener) and here
            // we request that the VehicleManager call its receive() method
            // whenever the EngineSpeed changes

            mVehicleManager.addListener(SteeringWheelAngle.class,mSteeringWheelAngleListener);
            mVehicleManager.addListener(FuelLevel.class,  mFuelLevelListener);
            mVehicleManager.addListener(EngineSpeed.class, mEngineSpeedListener);
            mVehicleManager.addListener(VehicleSpeed.class, mSpeedListener);
            mVehicleManager.addListener(Odometer.class, mOdometerListener);
            mVehicleManager.addListener(IgnitionStatus.class,mIgnitionListener);
            mVehicleManager.addListener(AcceleratorPedalPosition.class, mAcceleratorPedalPositionListener);
            mVehicleManager.addListener(TransmissionGearPosition.class, mGearPositionListener);
            mVehicleManager.addListener(BrakePedalStatus.class, mBrakePedalStatusListener);
            mVehicleManager.addListener(TurnSignalStatus.class,mTurnSignalStatusListener);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.w(TAG, "VehicleManager Service  disconnected unexpectedly");
            mVehicleManager = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starter, menu);
        return true;
    }
}
