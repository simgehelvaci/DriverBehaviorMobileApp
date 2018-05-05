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

public class StarterActivity extends BaseActivity {
    private static final String TAG = "StarterActivity";
    List<Book> lstBook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);



        lstBook = new ArrayList<>();
        lstBook.add(new Book("Achievements","Google Play Services","Description",R.drawable.achv));
        lstBook.add(new Book("LeaderBoard","Google Play Services","Description",R.drawable.lb));
        lstBook.add(new Book("Location POI","Google Maps","Description",R.drawable.route));
        lstBook.add(new Book("Fuel Info","OpenXC Input","Description",R.drawable.fuel));
        lstBook.add(new Book("Score","Evaluation","Description",R.drawable.score));
        lstBook.add(new Book("Speed","OpenXC Output","Description",R.drawable.speed));

        lstBook.add(new Book("Emergency Button","SMS_Sender","Description",R.drawable.siren));
        lstBook.add(new Book("Pedal Position","OpenXC Input","Description",R.drawable.pedal));
        lstBook.add(new Book("Settings","Settings","Description",R.drawable.settings));



        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);





    }

}
