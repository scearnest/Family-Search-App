package com.example.fms;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import models.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.Frag_Layout);

        if(Model.getInstance().getLoggedIn())
        {
            swapToMap();
        }

        if(frag == null && !Model.getInstance().getLoggedIn())
        {
            frag = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.Frag_Layout, frag)
                    .commit();
        }
    }

    public void swapToMap()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = new MapFragment();
        fm.beginTransaction()
                .replace(R.id.Frag_Layout, frag)
                .commit();
    }
}
