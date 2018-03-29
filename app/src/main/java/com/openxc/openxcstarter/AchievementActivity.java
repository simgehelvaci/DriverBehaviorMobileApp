package com.openxc.openxcstarter;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AchievementActivity extends AppCompatActivity {
    private Button mainButton;
    private TextView scoreView;
    private TextView timeView;

    private int score = 0;
    private boolean playing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_layout);
        mainButton = (Button)findViewById(R.id.main_button);
        scoreView = (TextView)findViewById(R.id.score_view);
        timeView = (TextView)findViewById(R.id.time_view);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playing) {
                    // The first click
                    playing = true;
                    mainButton.setText("Keep Clicking");

                    // Initialize CountDownTimer to 60 seconds
                    new CountDownTimer(20000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeView.setText("Time remaining: " + millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            playing = false;
                            timeView.setText("Game over");
                            mainButton.setVisibility(View.GONE);
                        }
                    }.start();  // Start the timer
                } else {
                    // Subsequent clicks
                    score++;
                    scoreView.setText("Score: " + score + " points");
                }
            }
        });
    }

}
