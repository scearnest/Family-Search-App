package com.example.fms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import models.Event;
import models.Model;
import models.Person;

public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback
{
    private MapFragment mapFrag;
    private GoogleMap mMap = null;
    private Marker myMarker;
    private List<Polyline> polyLines = new ArrayList<Polyline>();
    private int familyTreeLineWidth;
    private String currEventID = null;
    final private int BASE_LINE_WIDTH = 5;

    public MapFragment()
    {

    }

    public MapFragment(String eventID)
    {
        currEventID = eventID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mapFrag = new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.map_layout, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

            LinearLayout eventInfo = (LinearLayout) v.findViewById(R.id.eventInfo);
            eventInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currEventID != null) {
                        String personID = Model.getInstance().getEventFromID(currEventID).getPersonID();
                        Intent settingsIntent = new Intent(getActivity(), PersonActivity.class);
                        settingsIntent.putExtra("PersonID", personID);
                        startActivity(settingsIntent);
                    }
                }
            });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if(currEventID == null) {
            inflater.inflate(R.menu.mapmenu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);

                return true;

            case R.id.filter:

                Intent filterIntent = new Intent(getActivity(), FilterActivity.class);
                startActivity(filterIntent);

                return true;

            case R.id.search:

                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(Model.getInstance().getMapType());

        addMarkers();
        if(currEventID != null)
        {
            clearPolyLines();
            drawLines(currEventID);
            Event event = Model.getInstance().getEventFromID(currEventID);
            CameraUpdate location = CameraUpdateFactory.newLatLng(new LatLng(event.getLatitude(), event.getLongitude()));
            mMap.moveCamera(location);
        }

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        clearPolyLines();
        currEventID = marker.getTitle();
        drawLines(currEventID);

        return true;
    }

    private void drawLines(String eventID)
    {
        if(Model.getInstance().getLifeStory())
            addStoryLines(eventID);
        if(Model.getInstance().getSpouseLines())
            addSpouseLines(eventID);
        if(Model.getInstance().getFamilyTreeLines()) {
            familyTreeLineWidth = BASE_LINE_WIDTH;
            addFamilyTreeLines(eventID);

        }

        updateEventInfo(eventID);
    }

    private void addMarkers()
    {
        ArrayList<Event> events = Model.getInstance().getEvents();
        for(int i = 0; i < events.size(); ++i)
        {
            if(Model.getInstance().checkFilteredEvent(events.get(i).getEventID())) {
                LatLng location = new LatLng(events.get(i).getLatitude(), events.get(i).getLongitude());
                myMarker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(events.get(i).getEventID())
                        .snippet(events.get(i).getEventType())
                        .icon(BitmapDescriptorFactory.defaultMarker(getTypeFloat(events.get(i).getEventType()))));
            }
        }
    }

    @Override
    public void onResume() {

        if(mMap != null) {
            mMap.clear();
            mMap.setMapType(Model.getInstance().getMapType());
            addMarkers();

            if (currEventID != null && Model.getInstance().checkFilteredEvent(currEventID)) {
                clearPolyLines();
                drawLines(currEventID);
                Event event = Model.getInstance().getEventFromID(currEventID);
                CameraUpdate location = CameraUpdateFactory.newLatLng(new LatLng(event.getLatitude(), event.getLongitude()));
                mMap.moveCamera(location);
            }
        }

        super.onResume();
    }

    private void updateEventInfo(String eventID)
    {
        Event event = Model.getInstance().getEventFromID(eventID);
        Person person = Model.getInstance().getPersonFromID(event.getPersonID());

        TextView name = (TextView) getView().findViewById(R.id.name);
        name.setText(person.getFirstName() + " " + person.getLastName() + "(" + person.getGender() + ")");

        TextView location = (TextView) getView().findViewById(R.id.location);
        location.setText(event.getCity() + " " + event.getCountry());

        TextView type = (TextView) getView().findViewById(R.id.eventType);
        type.setText(event.getEventType() + " " + event.getYear());

    }

    private float getTypeFloat(String eventType)
    {
        eventType = eventType.toLowerCase();

        if(eventType.equals("birth"))
        {
            return BitmapDescriptorFactory.HUE_GREEN;
        }
        if(eventType.equals("marriage"))
        {
            return BitmapDescriptorFactory.HUE_RED;
        }
        if(eventType.equals("baptism"))
        {
            return BitmapDescriptorFactory.HUE_BLUE;
        }

        else {

            float type = ((float) eventType.length());
            type = type * type * type;

            if(type >= 360)
                type = 255;
            return type;
        }
    }

    public void addStoryLines(String eventID)
    {
        String personID = Model.getInstance().getEventFromID(eventID).getPersonID();
        Map<String, ArrayList<Event>> eventsByPerson = Model.getInstance().getEventsByPerson();
        ArrayList<Event> events;
        ArrayList<Event> filteredEvents = new ArrayList<Event>();

        ArrayList<Float> lats = new ArrayList<Float>();
        ArrayList<Float> longs = new ArrayList<Float>();

        events = eventsByPerson.get(personID);

        for(int i = 0; i < events.size(); ++i)
        {
            if(Model.getInstance().checkFilteredEvent(events.get(i).getEventID()))
            {
                filteredEvents.add(events.get(i));
            }
        }

        if(!(filteredEvents.size() <= 1)) {

            Collections.sort(filteredEvents);

            for (int i = 0; i < filteredEvents.size(); ++i) {
                lats.add(filteredEvents.get(i).getLatitude());
                longs.add(filteredEvents.get(i).getLongitude());
            }

            for (int j = 0; j < lats.size(); ++j) {
                if (j + 1 != lats.size()) {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(lats.get(j), longs.get(j)), new LatLng(lats.get(j + 1), longs.get(j + 1)))
                            .width(5)
                            .color(Model.getInstance().getLifeStoryColor()));
                    polyLines.add(line);
                }
            }
        }
    }

    private void addSpouseLines(String eventID)
    {
        Event currEvent = Model.getInstance().getEventFromID(eventID);
        Event spouseEvent = Model.getInstance().getSpouseRecentEvent(currEvent.getEventType(), currEvent.getPersonID());

        if(spouseEvent != null)
        {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(currEvent.getLatitude(), currEvent.getLongitude()), new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()))
                    .width(5)
                    .color(Model.getInstance().getSpouseLinesColor()));
            polyLines.add(line);
        }
    }

    private void addFamilyTreeLines(String eventID)
    {
        Event startEvent = Model.getInstance().getEventFromID(eventID);
        String personID = startEvent.getPersonID();

        populateFamilyLines(personID, startEvent);
    }

    private void populateFamilyLines(String personID, Event currEvent)
    {
        Person father = Model.getInstance().getFather(personID);
        if(father != null)
        {
            Event fatherEvent = Model.getInstance().getMostRecentEvent(father.getPersonID());
            drawLineBetweenEvents(currEvent, fatherEvent);
            familyTreeLineWidth = familyTreeLineWidth/2;
            populateFamilyLines(father.getPersonID(), fatherEvent);
            familyTreeLineWidth = familyTreeLineWidth *2;
        }

        Person mother = Model.getInstance().getMother(personID);
        if(mother != null)
        {
            Event motherEvent = Model.getInstance().getMostRecentEvent(mother.getPersonID());
            drawLineBetweenEvents(currEvent, motherEvent);
            familyTreeLineWidth = familyTreeLineWidth/2;
            populateFamilyLines(mother.getPersonID(), motherEvent);
            familyTreeLineWidth = familyTreeLineWidth *2;
        }
    }

    private void drawLineBetweenEvents(Event event1, Event event2)
    {
        if(event1 != null && event2 != null) {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(event1.getLatitude(), event1.getLongitude()), new LatLng(event2.getLatitude(), event2.getLongitude()))
                    .width(familyTreeLineWidth)
                    .color(Model.getInstance().getFamilyTreeLinesColor()));
            polyLines.add(line);
        }
    }

    private void clearPolyLines()
    {
        for(Polyline line : polyLines)
        {
            line.remove();
        }

        polyLines.clear();
    }
}

