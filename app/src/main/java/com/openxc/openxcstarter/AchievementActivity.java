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

public class AchievementActivity extends BaseActivity {

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


    }



}
