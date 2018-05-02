package com.openxc.openxcstarter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import static com.openxc.openxcstarter.BaseActivity.apiClient;

public class LeaderBoard extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_layout);


        if(apiClient.isConnected()){
            // good
            startActivityForResult(
                    Games.Leaderboards.getLeaderboardIntent(apiClient,
                            getString(R.string.leaderboard_my_little_leaderboard)), 0);

        }else{
            //connect it
            apiClient.connect(GoogleApiClient.SIGN_IN_MODE_REQUIRED);
            Toast.makeText(getApplicationContext(), "SIGN_IN_MODE_REQUIRED LDR", Toast.LENGTH_LONG).show();
        }

    }
}
