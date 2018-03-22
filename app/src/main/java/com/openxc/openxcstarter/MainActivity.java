package com.openxc.openxcstarter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextClock;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> lstBook ;
    private TextClock textClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);



        lstBook = new ArrayList<>();
        lstBook.add(new Book("Achievements","Google Play Services","Description",R.drawable.achv));
        lstBook.add(new Book("LeaderBoard","Google Play Services","Description",R.drawable.lb));
        lstBook.add(new Book("Username","Google Play Services","Description",R.drawable.users));
        lstBook.add(new Book("Ignition Status","OpenXC Input","Description",R.drawable.sw));
        lstBook.add(new Book("Score","Evaluation","Description",R.drawable.score));
        lstBook.add(new Book("Distance","OpenXC Input","Description",R.drawable.route));
        lstBook.add(new Book("Fuel Level","OpenXC Input","Description",R.drawable.fuel));
        lstBook.add(new Book("Nearest Gas Station","Google Maps","Description",R.drawable.gas));
        lstBook.add(new Book("Emergency Button","SMS_Sender","Description",R.drawable.siren));
        lstBook.add(new Book("Speed","OpenXC Input","Description",R.drawable.speed));
        lstBook.add(new Book("Gear Position","OpenXC Input","Description",R.drawable.gearshift));
        lstBook.add(new Book("Accelerator Pedal Position","OpenXC Input","Description",R.drawable.pedals));
        lstBook.add(new Book("Brake Pedal Position","OpenXC Input","Description",R.drawable.pedal));

        lstBook.add(new Book("Location","Google Maps","Description",R.drawable.location));
        lstBook.add(new Book("Settings","Settings","Description",R.drawable.settings));



        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);


    }
}
