package com.openxc.openxcstarter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.openxc.VehicleManager;
import com.openxc.measurements.FuelConsumed;
import com.openxc.measurements.FuelLevel;
import com.openxc.measurements.AcceleratorPedalPosition;
import com.openxc.measurements.BrakePedalStatus;
import com.openxc.measurements.EngineSpeed;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.TransmissionGearPosition;
import com.openxc.measurements.VehicleSpeed;
import com.openxc.measurements.IgnitionStatus;
import com.openxc.units.Boolean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StarterActivity extends Activity {
    private static final String TAG = "StarterActivity";
    List<Book> lstBook ;
    private TextView mIgnitionStatusView;
    private TextView mFuelLevelStatusView;
    private VehicleManager mVehicleManager;
    private TextView mEngineSpeedView;
    private TextView mVehicleSpeedView;
    private TextView mOdometerView;
    private TextView mAcceleratorPedalPositionView;
    private TextView mGearPositionView;
    private TextView mGForceView;
    private TextView mBrakePedalView;
    private ProgressBar statusBar, throttlePos, enginePos;
    private TextView statusBarText;
    private TextView warningText;
//    private TextView gR, gN, g1, g2, g3, g4, g5, g6;
    private long gForceTimer = -1;
    private double gForceVelocity = -1;
    private final double gConstant = 9.80665;
    public static int statusPercentage = 100;
    private String gearPosition = "neutral";
    private Double pedalPos = 0.0;
    public static String IGNITION ;
    public static String DISTANCE;
    public static String FUEL;
    public static String GEAR;
    public static String SPEED;
    public static String ACCELERATORPEDALPOSITION;
    public static String BRAKEPEDALPOSITION;

    private double gForce = -1;
    private double weight = 0.5;
    Score scoreClass = new Score();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);



        lstBook = new ArrayList<>();
        lstBook.add(new Book("Achievements","Google Play Services","Description",R.drawable.achv));
        lstBook.add(new Book("LeaderBoard","Google Play Services","Description",R.drawable.lb));
        lstBook.add(new Book("Username","Google Play Services","Description",R.drawable.users));
        lstBook.add(new Book("Ignition Status","OpenXC Input","Description",R.drawable.sw));
        lstBook.add(new Book("Score","Evaluation","Description",R.drawable.score));
        lstBook.add(new Book("Distance","OpenXC Input","Description",R.drawable.route));
        lstBook.add(new Book("Fuel Level","OpenXC Input","Description",R.drawable.fuel));
        lstBook.add(new Book("Nearest Gas Station","Google Maps","Description",R.drawable.gas));
        lstBook.add(new Book("Emergency Button","SMS_Sender","Description",R.drawable.siren));
        lstBook.add(new Book("Speed","OpenXC Input","Description",R.drawable.speed));
        lstBook.add(new Book("Gear Position","OpenXC Input","Description",R.drawable.gearshift));
        lstBook.add(new Book("Accelerator Pedal Position","OpenXC Input","Description",R.drawable.pedals));
        lstBook.add(new Book("Brake Pedal Position","OpenXC Input","Description",R.drawable.pedal));

        lstBook.add(new Book("Location","Google Maps","Description",R.drawable.location));
        lstBook.add(new Book("Settings","Settings","Description",R.drawable.settings));



        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);





        // grab a reference to the engine speed text object in the UI, so we can
        // manipulate its value later from Java code
        mFuelLevelStatusView = (TextView) findViewById(R.id.fuel_level);
        mEngineSpeedView = (TextView) findViewById(R.id.engine_speed);
        mVehicleSpeedView = (TextView) findViewById(R.id.vehicle_speed);
        mOdometerView = (TextView) findViewById(R.id.odometer);
        mAcceleratorPedalPositionView = (TextView) findViewById(R.id.accelerator_pedal);
        mGearPositionView = (TextView) findViewById(R.id.gear_position);
        mBrakePedalView = (TextView) findViewById(R.id.brake_pedal_position);
        mGForceView= (TextView) findViewById(R.id.g_view);
        statusBar = (ProgressBar) findViewById(R.id.statusBar);
        statusBarText = (TextView) findViewById(R.id.statusBarText);
        warningText = (TextView) findViewById(R.id.warningText);
        mIgnitionStatusView = (TextView) findViewById(R.id.ignitionStatus);
        throttlePos = (ProgressBar) findViewById(R.id.throttlePos);
        enginePos = (ProgressBar) findViewById(R.id.enginePos);


        statusBar.setMax(statusPercentage);

        setStatus(statusPercentage);
    }

    private void setStatus(int percentage) {
        if (percentage < 0) { percentage = 0; }
        statusPercentage = percentage;
        statusBar.setProgress(percentage);

        String text = Integer.toString(percentage) + "%";
        statusBarText.setText(text);
    }

    @Override
    public void onPause() {
        super.onPause();
        // When the activity goes into the background or exits, we want to make
        // sure to unbind from the service to avoid leaking memory
        if(mVehicleManager != null) {
            Log.i(TAG, "Unbinding from Vehicle Manager");
            // Remember to remove your listeners, in typical Android
            // fashion.
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

    private void neutralGasDetected(long time) {
        if (neutralGasDetectedTimer == -1) {
            neutralGasDetectedTimer = time;
        }
        else if (time - neutralGasDetectedTimer > 3500) {
          setStatus(statusPercentage - 1);
            Toast.makeText(getApplicationContext(), "Throttle while gear neutral position", Toast.LENGTH_LONG).show();
            warningText.setText("Throttle while gear neutral position");
            neutralGasDetectedTimer = -1;
        }
    }

    private void hardBrakeDetected(long time) {
        if (brakePedalDetectedTimer == -1) {
            brakePedalDetectedTimer = time;
        }
        else if (time - brakePedalDetectedTimer > 2500) {
            setStatus(statusPercentage - 1);
            Toast.makeText(getApplicationContext(), "Brake Pedal Overused!", Toast.LENGTH_LONG).show();
            warningText.setText("Brake Pedal Overused!");
            brakePedalDetectedTimer = -1;
        }
    }
    private void gForceTracker(double velocity, long birthtime) {
        //gForce = ((velocity) - gForceVelocity) * (1000/(60 * 60)) / (1000 / ((birthtime) - gForceTimer));
        gForce = gForce * 1/gConstant * (1 - weight) + weight * ((velocity) - gForceVelocity) / 3.6 * (1000 / Double.valueOf(birthtime - gForceTimer));
        String gString = String.format(Locale.ENGLISH, "%.10f", gForce);
        //Log.d("gforce", gString);
        mGForceView.setText(gString);
        gForceTimer = birthtime;
        gForceVelocity = velocity;
    }

    public void checkTimeSince(long time, boolean over70){
        if (!acceleratorPedalOver70 && over70) {
            pedalOver70Timer = time;
            acceleratorPedalOver70 = true;
        }
        else if (acceleratorPedalOver70 && over70){
            if (time - pedalOver70Timer > 7000){
                Toast.makeText(getApplicationContext(), "Aggressive acceleration", Toast.LENGTH_LONG).show();
                warningText.setText("Aggressive acceleration");
                setStatus(statusPercentage - 1);
//                AlertDialog.Builder builder = new AlertDialog.Builder(findViewById(android.R.id.content).getContext());
//                builder.setTitle("Aggressive acceleration");
//                builder.setMessage("Losing points...");
//                builder.setCancelable(true);
//
//                final AlertDialog closedialog= builder.create();
//
//                closedialog.show();
//
//                final Timer timer2 = new Timer();
//                timer2.schedule(new TimerTask() {
//                    public void run() {
//                        closedialog.dismiss();
//                        timer2.cancel(); //this will cancel the pedalOver70Timer of the system
//                    }
//                }, 5000);
                acceleratorPedalOver70 = false;
            }
        }
        else {
            acceleratorPedalOver70 = false;
        }

    }

    /* This is an OpenXC measurement listener object - the type is recognized
     * by the VehicleManager as something that can receive measurement updates.
     * Later in the file, we'll ask the VehicleManager to call the receive()
     * function here whenever a new EngineSpeed value arrives.
     */
    EngineSpeed.Listener mSpeedListener = new EngineSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final EngineSpeed speed = (EngineSpeed) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mEngineSpeedView.setText("Engine speed (RPM): "
                            + speed.getValue().doubleValue());
                    enginePos.setProgress(speed.getValue().intValue());
                }
            });
        }
    };

    IgnitionStatus.Listener mIgnitionListener = new IgnitionStatus.Listener() {
        @Override
        public void receive(Measurement measurement) {
            final IgnitionStatus ignitionStatus = (IgnitionStatus) measurement;
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {


                    mIgnitionStatusView.setText("Ignition:"
                            + ignitionStatus.getValue().toString());
                    IGNITION= (ignitionStatus.getValue().toString());
                    Log.d("ıgnition:",IGNITION);

                }
            });
        }
    };

    FuelLevel.Listener mFuelLevelListener = new FuelLevel.Listener() {
        @Override
        public void receive(Measurement measurement) {
            final FuelLevel FuelLevelStatus = (FuelLevel) measurement;
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {


                    mFuelLevelStatusView.setText("Fuel:"
                            + FuelLevelStatus.getValue().toString());
                    FUEL= (FuelLevelStatus.getValue().toString());
                    Log.d("fuel:",FUEL);

                }
            });
        }
    };

    VehicleSpeed.Listener mVehicleListener = new VehicleSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final VehicleSpeed speed = (VehicleSpeed) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    gForceTracker(speed.getValue().doubleValue(), speed.getBirthtime());
                    mVehicleSpeedView.setText("Vehicle speed (km/h): "
                            + speed.getValue().doubleValue());

                    SPEED= (speed.getValue().toString());
                    Log.d("speed:",SPEED);
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
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    pedalPos = position.getValue().doubleValue();
                    mAcceleratorPedalPositionView.setText("AcceleratorPedalPosition value: "
                            + pedalPos);
                        ACCELERATORPEDALPOSITION = pedalPos.toString();
                    //checkTimeSince(position.getBirthtime(), true);

                    throttlePos.setProgress(pedalPos.intValue());
                    if (gearPosition.equalsIgnoreCase("neutral")) {
                        neutralGasDetected(position.getBirthtime());
                    }

                    if (pedalPos > 70) {
                        checkTimeSince(position.getBirthtime(), true);
                    }
                    else {
                        checkTimeSince(0, false);
                    }


                }
            });
        }
    };


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
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    Boolean brakePos = position.getValue();
                    mBrakePedalView.setText("BrakePedalStatus value: "
                            + brakePos);

                    BRAKEPEDALPOSITION = brakePos.toString();


                    if (brakePos.booleanValue()) {
                        hardBrakeDetected(position.getBirthtime());
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
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mOdometerView.setText(distance.getValue().toString());
                    DISTANCE=distance.getValue().toString();
                    Log.d("distance",DISTANCE);
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
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mGearPositionView.setText("Gear: "
                            + position.toString());
                }
            });

            gearPosition = position.toString();
            GEAR=position.getValue().toString();
            Log.d("Gear",GEAR);
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

            mVehicleManager.addListener(FuelLevel.class,  mFuelLevelListener);
            mVehicleManager.addListener(EngineSpeed.class, mSpeedListener);
            mVehicleManager.addListener(VehicleSpeed.class, mVehicleListener);
            mVehicleManager.addListener(Odometer.class, mOdometerListener);
            mVehicleManager.addListener(IgnitionStatus.class,mIgnitionListener);
            mVehicleManager.addListener(AcceleratorPedalPosition.class, mAcceleratorPedalPositionListener);
            mVehicleManager.addListener(TransmissionGearPosition.class, mGearPositionListener);
            mVehicleManager.addListener(BrakePedalStatus.class, mBrakePedalStatusListener);
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
