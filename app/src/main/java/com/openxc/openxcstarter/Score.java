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
    private TextView scoreText;
    private TextView scoreValue;
    private TextView averageScoreText;
    private TextView averageScoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        statusBar = (ProgressBar) findViewById(R.id.statusBar);
        statusBarText = (TextView) findViewById(R.id.statusBarText);
        scoreText = (TextView) findViewById(R.id.txt_Score);
        scoreValue = (TextView) findViewById(R.id.txt_ScoreValue);
        averageScoreText = (TextView) findViewById(R.id.txt_AverageScore);
        averageScoreValue=(TextView) findViewById(R.id.txt_AverageScoreValue);
       // statusBar.setMax(statusPercentage);

       // setStatus(getStatus());


    }

    @Override
    public void onStatusUpdate() {
        super.onStatusUpdate();
        setStatus(getStatus());
    }

    public void setStatus(int percentage) {
        if (percentage < 0) { percentage = 0; }
        statusPercentage = percentage;
        //average status percentage: sum of status percentages so far divided by dayCount
        statusBar.setProgress(percentage);
        int dayCount = 1;
        Double average = Double.valueOf(statusPercentage/dayCount);
        String text = Integer.toString(percentage) + "%";
        String score = Integer.toString(percentage);
        String averageScore = Double.toString(average);
        statusBarText.setText(text);

        scoreValue.setText(score);
        averageScoreValue.setText(averageScore);

    }
}
