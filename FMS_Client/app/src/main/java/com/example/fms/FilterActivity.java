package com.example.fms;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import models.Model;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filter_activity);

        Switch female_switch = findViewById(R.id.female_switch);
        female_switch.setChecked(Model.getInstance().getFilterFemale());
        female_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setFilterFemale(true);

                }
                if(!isChecked){
                    Model.getInstance().setFilterFemale(false);
                }
            }
        });

        Switch male_switch = findViewById(R.id.male_switch);
        male_switch.setChecked(Model.getInstance().getFilterMale());
        male_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setFilterMale(true);

                }
                if(!isChecked){
                    Model.getInstance().setFilterMale(false);
                }
            }
        });

        Switch mother_switch = findViewById(R.id.mother_switch);
        mother_switch.setChecked(Model.getInstance().getFilterMotherSide());
        mother_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setFilterMotherSide(true);

                }
                if(!isChecked){
                    Model.getInstance().setFilterMotherSide(false);
                }
            }
        });

        Switch father_switch = findViewById(R.id.father_switch);
        father_switch.setChecked(Model.getInstance().getFilterFatherSide());
        father_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Model.getInstance().setFilterFatherSide(true);

                }
                if(!isChecked){
                    Model.getInstance().setFilterFatherSide(false);
                }
            }
        });

        ListView lv = (ListView) findViewById(R.id.filter_list);

        ArrayList<String> itemsInList = Model.getInstance().getTypes();

        lv.setAdapter(new CustomListAdapter(this, R.layout.filter_item, itemsInList));

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

    private class CustomListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;

        private CustomListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.aswitch = (Switch) convertView.findViewById(R.id.filter_switch);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            final String type = Model.getInstance().getTypes().get(position);

            mainViewholder.aswitch.setChecked(Model.getInstance().getEventTypes().get(type));
            mainViewholder.aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        Model.getInstance().getEventTypes().put(type, true);

                    }
                    if(!isChecked){
                        Model.getInstance().getEventTypes().put(type, false);
                    }
                }
            });


            mainViewholder.aswitch.setText(getItem(position));


            return convertView;
        }
    }

    public class ViewHolder {

        Switch aswitch;
    }
}

