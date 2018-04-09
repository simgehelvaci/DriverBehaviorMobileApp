package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class IgnitionStatus extends BaseActivity {

    private TextView View1;
    private TextView View2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ignition_layout);



       View1= (TextView) findViewById(R.id.textView1);
        View2 = (TextView) findViewById(R.id.textView2);




    }

    @Override
    public void onIgnitionUpdate() {
        super.onIgnitionUpdate();
        View2.setText(BaseActivity.IGNITION);
    }
}
