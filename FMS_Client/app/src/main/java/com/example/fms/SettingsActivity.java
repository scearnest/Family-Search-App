package com.example.fms;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import models.Model;
import requests.EventsRequest;
import requests.PersonsRequest;
import results.EventsResult;
import results.PersonsResult;
import serverproxy.ServerProxy;

public class SettingsActivity extends AppCompatActivity {

    private EventsResult eventsResult;
    private PersonsResult personsResult;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.setting_activity);

        Switch spouse_button = findViewById(R.id.spouse_switch);
        spouse_button.setChecked(Model.getInstance().getSpouseLines());
        spouse_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setSpouseLines(true);

                }
                if(!isChecked){
                    Model.getInstance().setSpouseLines(false);
                }
            }
        });

        Switch life_button = findViewById(R.id.life_switch);
        life_button.setChecked(Model.getInstance().getLifeStory());
        life_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setLifeStory(true);

                }
                if(!isChecked){
                    Model.getInstance().setLifeStory(false);
                }
            }
        });

        Switch family_button = findViewById(R.id.family_switch);
        family_button.setChecked(Model.getInstance().getFamilyTreeLines());
        family_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setFamilyTreeLines(true);

                }
                if(!isChecked){
                    Model.getInstance().setFamilyTreeLines(false);
                }
            }
        });

        final Spinner spouseSpinner = findViewById(R.id.spouseColor);
        spouseSpinner.setSelection(provideSelection(Model.getInstance().getSpouseLinesColor()));
        spouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(spouseSpinner.getSelectedItem().toString().equals("Red"))
                {
                    Model.getInstance().setSpouseLinesColor(Color.RED);
                }

                if(spouseSpinner.getSelectedItem().toString().equals("Blue"))
                {
                    Model.getInstance().setSpouseLinesColor(Color.BLUE);
                }

                if(spouseSpinner.getSelectedItem().toString().equals("Green"))
                {
                    Model.getInstance().setSpouseLinesColor(Color.GREEN);
                }

                if(spouseSpinner.getSelectedItem().toString().equals("Yellow"))
                {
                    Model.getInstance().setSpouseLinesColor(Color.YELLOW);
                }

                if(spouseSpinner.getSelectedItem().toString().equals("Black"))
                {
                    Model.getInstance().setSpouseLinesColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // intentionally blank
            }
        });

        final Spinner familySpinner = findViewById(R.id.familyTreeSpinner);
        familySpinner.setSelection(provideSelection(Model.getInstance().getFamilyTreeLinesColor()));
        familySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(familySpinner.getSelectedItem().toString().equals("Red"))
                {
                    Model.getInstance().setFamilyTreeLinesColor(Color.RED);
                }

                if(familySpinner.getSelectedItem().toString().equals("Blue"))
                {
                    Model.getInstance().setFamilyTreeLinesColor(Color.BLUE);
                }

                if(familySpinner.getSelectedItem().toString().equals("Green"))
                {
                    Model.getInstance().setFamilyTreeLinesColor(Color.GREEN);
                }

                if(familySpinner.getSelectedItem().toString().equals("Yellow"))
                {
                    Model.getInstance().setFamilyTreeLinesColor(Color.YELLOW);
                }

                if(familySpinner.getSelectedItem().toString().equals("Black"))
                {
                    Model.getInstance().setFamilyTreeLinesColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // intentionally blank
            }
        });

        final Spinner lifeSpinner = findViewById(R.id.lifeSpinner);
        lifeSpinner.setSelection(provideSelection(Model.getInstance().getLifeStoryColor()));
        lifeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(lifeSpinner.getSelectedItem().toString().equals("Red"))
                {
                    Model.getInstance().setLifeStoryColor(Color.RED);
                }

                if(lifeSpinner.getSelectedItem().toString().equals("Blue"))
                {
                    Model.getInstance().setLifeStoryColor(Color.BLUE);
                }

                if(lifeSpinner.getSelectedItem().toString().equals("Green"))
                {
                    Model.getInstance().setLifeStoryColor(Color.GREEN);
                }

                if(lifeSpinner.getSelectedItem().toString().equals("Yellow"))
                {
                    Model.getInstance().setLifeStoryColor(Color.YELLOW);
                }

                if(lifeSpinner.getSelectedItem().toString().equals("Black"))
                {
                    Model.getInstance().setLifeStoryColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // intentionally blank
            }
        });

        final Spinner typeSpinner = findViewById(R.id.mapSpinner);
        typeSpinner.setSelection(Model.getInstance().getMapType() -1);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(typeSpinner.getSelectedItem().toString().equals("Normal"))
                {
                    Model.getInstance().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

                if(typeSpinner.getSelectedItem().toString().equals("Hybrid"))
                {
                    Model.getInstance().setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }

                if(typeSpinner.getSelectedItem().toString().equals("Satellite"))
                {
                    Model.getInstance().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }

                if(typeSpinner.getSelectedItem().toString().equals("Terrain"))
                {
                    Model.getInstance().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // intentionally blank
            }
        });

        final Button logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model.getInstance().setLoggedIn(false);

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        final Button syncButton = findViewById(R.id.resync);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReSyncTask task = new ReSyncTask();
                task.execute("token");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

    private int provideSelection(int color)
    {
        if(color == Color.RED)
        {
            return 0;
        }
        if(color == Color.BLUE)
        {
            return 1;
        }
        if(color == Color.GREEN)
        {
            return 2;
        }

        if(color == Color.YELLOW)
        {
            return 3;
        }

        if(color == Color.BLACK)
        {
            return 4;
        }

        return 0;
    }

    private class ReSyncTask extends AsyncTask<String, Void, Long> {

        protected Long doInBackground(String... requests) {
            long totalSize = 0;

            String authToken = requests[0];

            ServerProxy server = new ServerProxy();
            server.setServerHost(Model.getInstance().getServerHost());
            server.setServerPort(Model.getInstance().getServerPort());

            EventsRequest eventsRequest = new EventsRequest();
            eventsRequest.setAuthToken(Model.getInstance().getAuthToken());
            PersonsRequest personsRequest = new PersonsRequest();
            personsRequest.setAuthToken(Model.getInstance().getAuthToken());

            personsResult = server.persons(personsRequest);
            eventsResult = server.events(eventsRequest);

            return totalSize;
        }

        protected void onPostExecute(Long result) {

            if(eventsResult.getMessage() != null || personsResult.getMessage() != null)
            {

                String message = "Could not properly resync";
                Toast.makeText(getApplicationContext(), message,
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            Model.getInstance().setLoggedIn(true);
        }
    }
}


