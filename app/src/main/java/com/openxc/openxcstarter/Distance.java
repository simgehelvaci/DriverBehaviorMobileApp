package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Distance extends Activity {

    private TextView DistanceView1;
    private TextView DistanceView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.distance_layout);

        DistanceView1= (TextView) findViewById(R.id.textView1);
        DistanceView2 = (TextView) findViewById(R.id.textView2);
        DistanceView2.setText(StarterActivity.DISTANCE);

    }
}
