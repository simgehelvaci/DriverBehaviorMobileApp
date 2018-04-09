package com.openxc.openxcstarter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.openxc.openxcstarter.StarterActivity.statusPercentage;

public class Score extends BaseActivity {

    private ProgressBar statusBar;
    private TextView statusBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        statusBar = (ProgressBar) findViewById(R.id.statusBar);
        statusBarText = (TextView) findViewById(R.id.statusBarText);
        statusBar.setMax(statusPercentage);

        setStatus(getStatus());


    }

    @Override
    public void onStatusUpdate() {
        super.onStatusUpdate();
        setStatus(getStatus());
    }

    public void setStatus(int percentage) {
        if (percentage < 0) { percentage = 0; }
        statusPercentage = percentage;
        statusBar.setProgress(percentage);

        String text = Integer.toString(percentage) + "%";
        statusBarText.setText(text);
    }
}
