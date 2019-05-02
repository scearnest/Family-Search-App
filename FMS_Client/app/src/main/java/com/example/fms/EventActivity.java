package com.example.fms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_activity);

        final String eventID = getIntent().getStringExtra("EventID");

        FragmentManager fm = getSupportFragmentManager();

            Fragment frag = new MapFragment(eventID);
            fm.beginTransaction()
                    .add(R.id.Frag_Layout, frag)
                    .commit();

    }
}
