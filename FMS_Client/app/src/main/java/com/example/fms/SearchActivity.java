package com.example.fms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.Model;
import models.Person;


public class SearchActivity extends AppCompatActivity {

    String searchString;
    ArrayList<Person> people;
    ArrayList<Event> events;
    int peopleSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_activity);

        EditText searchBar;
        searchBar = findViewById(R.id.searchText);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                searchString = s.toString().toLowerCase();
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchString = s.toString().toLowerCase();
            }
        });

        Button searchButton;
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setEnabled(true);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView lv = (ListView) findViewById(R.id.search_list);

                people = Model.getInstance().searchPeople(searchString);
                events = Model.getInstance().searchEvents(searchString);
                peopleSize = people.size();

                ArrayList<String> searchResults = new ArrayList<String>();

                for(int i = 0; i < peopleSize; ++i)
                {
                    String personInfo = people.get(i).getFirstName() + " " + people.get(i).getLastName();
                    searchResults.add(personInfo);
                }

                for(int i = 0; i < events.size(); ++i)
                {
                    Person eventPerson = Model.getInstance().getPersonFromID(events.get(i).getPersonID());
                    String eventInfo = events.get(i).getEventType() + " " + events.get(i).getCity() + ", " +
                            events.get(i).getCountry() + " (" + events.get(i).getYear() + ") \n" + eventPerson.getFirstName() +
                            " " + eventPerson.getLastName();
                    searchResults.add(eventInfo);
                }


                lv.setAdapter(new SearchActivity.MyListAdaper(SearchActivity.this, R.layout.search_item, searchResults));
            }
        });
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;

        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            SearchActivity.ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                SearchActivity.ViewHolder viewHolder = new SearchActivity.ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.searchResultText);
                convertView.setTag(viewHolder);
            }

            mainViewholder = (SearchActivity.ViewHolder) convertView.getTag();
            mainViewholder.textView.setText(getItem(position));
            mainViewholder.textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(position < peopleSize)
                    {
                        Intent personIntent = new Intent(SearchActivity.this, PersonActivity.class);
                        personIntent.putExtra("PersonID", people.get(position).getPersonID());
                        startActivity(personIntent);
                    }

                    else if(position >= peopleSize)
                    {
                        Intent eventIntent = new Intent(SearchActivity.this, EventActivity.class);
                        eventIntent.putExtra("EventID", events.get(position - peopleSize).getEventID());
                        startActivity(eventIntent);
                    }
                }

            });

            return convertView;
        }
    }

    public class ViewHolder {

        TextView textView;
    }
}

