package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AcceleratorPedalPosition extends BaseActivity {



    private TextView View1;
    private TextView View2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerator_layout);



        View1= (TextView) findViewById(R.id.textView1);
        View2 = (TextView) findViewById(R.id.textView2);
        View2.setText(StarterActivity.ACCELERATORPEDALPOSITION);



    }

    @Override
    public void  onAcceleratorPedalPositionUpdate() {
        super.onAcceleratorPedalPositionUpdate();
        View2.setText(BaseActivity.ACCELERATORPEDALPOSITION);
    }
}

