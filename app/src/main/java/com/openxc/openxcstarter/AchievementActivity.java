package com.openxc.openxcstarter;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.games.Games;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.openxc.openxcstarter.StarterActivity.*;
import static com.openxc.openxcstarter.StarterActivity.statusPercentage;

public class AchievementActivity extends AppCompatActivity {

    private Button mainButton;
    private TextView scoreView;
    private TextView timeView;
    private Button leaderboardButton;
    private Button showAchievementsButton;
    private int score = 0;
    private boolean playing = false;
    GoogleApiClient apiClient;
    String TAG = "Blabla";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_layout);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

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



        if(score>100) {
            Games.Achievements
                    .unlock(apiClient,
                            getString(R.string.achievement_lightning_fast));
        }


        mainButton = (Button)findViewById(R.id.main_button);
        leaderboardButton = (Button)findViewById(R.id.leaderboard_button);
        showAchievementsButton = (Button)findViewById(R.id.achievements_button);
        scoreView = (TextView)findViewById(R.id.score_view);
        timeView = (TextView)findViewById(R.id.time_view);


        showAchievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        Games.Achievements
                                .getAchievementsIntent(apiClient),
                        1
                );
                startActivityForResult(
                        Games.Achievements
                                .getAchievementsIntent(apiClient),
                        2
                );
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(
                        Games.Leaderboards.getLeaderboardIntent(apiClient,
                                getString(R.string.leaderboard_my_little_leaderboard)), 0);

            }
        });

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
                            Toast.makeText(AchievementActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // More code goes here
                //if(IGNITION.toLowerCase()== "off") playing = false;
                //if(IGNITION.toLowerCase()== "run")playing = true;
                if(!playing) {
                    // The first click
                    playing = true;

                    mainButton.setText("Keep Clicking");

                    // Initialize CountDownTimer to 60 seconds
                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeView.setText("Time remaining: " + millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            playing = false;

                            timeView.setText("Game over");
                            mainButton.setVisibility(View.GONE);
                            Games.Leaderboards.submitScore(apiClient,
                                    getString(R.string.leaderboard_my_little_leaderboard),
                                    score);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();


                            myRef.child("Drivers").child("Driver_id").setValue("55");
                            String playerName = Games.Players.getCurrentPlayer(apiClient).getName();
                            myRef.child("Drivers").child("Driver_name").setValue(playerName);
                            myRef.child("Drivers").child("Driver_score").setValue(statusPercentage);





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
