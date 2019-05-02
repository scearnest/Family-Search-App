package models;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model
{
    private static Model single_instance = null;

    //User Info
    private String authToken;
    private String userPersonID;
    private String serverHost;
    private String serverPort;
    private boolean loggedIn;

    //People and Events
    private static ArrayList<Person> persons = null;
    private static ArrayList<Event> events = null;

    //Sorted People and Events
    private static Map<String, Person> PersonsByID = new HashMap<String, Person>();

    private static Map<String, ArrayList<Event>> eventsByPerson = new HashMap<String, ArrayList<Event>>();
    private static Map<String, ArrayList<Event>> eventsBySpouse = new HashMap<String, ArrayList<Event>>();
    private static Map<String, Boolean> eventTypes = new HashMap<String, Boolean>();
    private static ArrayList<String> types = new ArrayList<String>();
    private static Map<String, ArrayList<Event>> eventsByType = new HashMap<String, ArrayList<Event>>();

    private ArrayList<Event> motherEvents = new ArrayList<Event>();
    private ArrayList<Event> fatherEvents = new ArrayList<Event>();
    private ArrayList<String> familyMemberIDs = new ArrayList<String>();

    //Map Data
    private boolean lifeStory;
    private boolean spouseLines;
    private boolean familyTreeLines;

    private int lifeStoryColor;
    private int spouseLinesColor;
    private int familyTreeLinesColor;

    private int mapType;

    //Filter Data
    private boolean filterMale;
    private boolean filterFemale;
    private boolean filterMotherSide;
    private boolean filterFatherSide;


    public Model()
    {
        lifeStory = true;
        spouseLines = true;
        familyTreeLines = true;

        lifeStoryColor = Color.RED;
        spouseLinesColor = Color.BLUE;
        familyTreeLinesColor = Color.GREEN;

        mapType = GoogleMap.MAP_TYPE_NORMAL;
        loggedIn = false;

        filterMale = true;
        filterFemale = true;
        filterMotherSide = true;
        filterFatherSide = true;
    }

    public static Model getInstance()
    {
        if(single_instance == null)
            single_instance = new Model();

        return single_instance;
    }

    //Getters and Setters
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
        sortPersonsByID();
    }

    public ArrayList<Person> getPersons()
    {
        return persons;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        sortEventsByPerson();
        sortEventsBySpouse();
        sortEventsByType();
        sortEventsBySide();
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public boolean getLifeStory() {
        return lifeStory;
    }

    public void setLifeStory(boolean lifeStory) {
        this.lifeStory = lifeStory;
    }

    public boolean getSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(boolean spouseLines) {
        this.spouseLines = spouseLines;
    }

    public boolean getFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(boolean familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public Map<String, ArrayList<Event>> getEventsByPerson() {
        return eventsByPerson;
    }

    public Person getPersonFromID(String personID) {

        return PersonsByID.get(personID);
    }

    public int getLifeStoryColor() {
        return lifeStoryColor;
    }

    public void setLifeStoryColor(int lifeStoryColor) {
        this.lifeStoryColor = lifeStoryColor;
    }

    public int getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public int getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }


    public void setUserPersonID(String userPersonID) {
        this.userPersonID = userPersonID;
    }

    public boolean getFilterMale() {
        return filterMale;
    }

    public void setFilterMale(boolean filterMale) {
        this.filterMale = filterMale;
    }

    public boolean getFilterFemale() {
        return filterFemale;
    }

    public void setFilterFemale(boolean filterFemale) {
        this.filterFemale = filterFemale;
    }

    public boolean getFilterMotherSide() {
        return filterMotherSide;
    }

    public void setFilterMotherSide(boolean filterMotherSide) {
        this.filterMotherSide = filterMotherSide;
    }

    public boolean getFilterFatherSide() {
        return filterFatherSide;
    }

    public void setFilterFatherSide(boolean filterFatherSide) {
        this.filterFatherSide = filterFatherSide;
    }

    public ArrayList<String> getFamilyMemberIDs() {
        return familyMemberIDs;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public static Map<String, Boolean> getEventTypes() {
        return eventTypes;
    }

    public Person getFather(String personID)
    {
        Person person = PersonsByID.get(personID);
        if(person.getFather() == null)
        {
            return null;
        }

        Person father = PersonsByID.get(person.getFather());
        return father;
    }

    public Person getMother(String personID)
    {
        Person person = PersonsByID.get(personID);
        if(person.getMother() == null)
        {
            return null;
        }

        Person mother = PersonsByID.get(person.getMother());
        return mother;
    }

    public Event getEventFromID(String eventID) {
        for(int i = 0; i < events.size(); ++i)
        {
            if(events.get(i).getEventID().equals(eventID))
            {
                return events.get(i);
            }
        }

        return null;
    }

    public Event getSpouseRecentEvent(String eventType, String personID)
    {
        ArrayList<Event> unFilteredSpouseEvents = eventsBySpouse.get(personID);
        ArrayList<Event> spouseEvents = new ArrayList<Event>();

        if(unFilteredSpouseEvents == null)
        {
            return null;
        }

        for(int i = 0; i < unFilteredSpouseEvents.size(); ++i)
        {
            if(checkFilteredEvent(unFilteredSpouseEvents.get(i).getEventID()))
            {
                spouseEvents.add(unFilteredSpouseEvents.get(i));
            }
        }

        Event recentEvent;

        for(int i = 0; i < spouseEvents.size(); ++i)
        {
            if(spouseEvents.get(i).getEventType().equals(eventType))
            {
                recentEvent = spouseEvents.get(i);
                return recentEvent;
            }
        }

        if(spouseEvents.isEmpty())
        {
            return null;
        }

        Collections.sort(spouseEvents);
        return spouseEvents.get(0);
    }

    public Event getMostRecentEvent(String personID)
    {
        ArrayList<Event> unFilteredPersonsEvents = eventsByPerson.get(personID);
        ArrayList<Event> personsEvents = new ArrayList<Event>();

        for(int i = 0; i < unFilteredPersonsEvents.size(); ++i)
        {
            if(checkFilteredEvent(unFilteredPersonsEvents.get(i).getEventID()))
            {
                personsEvents.add(unFilteredPersonsEvents.get(i));
            }
        }

        if(personsEvents.isEmpty())
        {
            return null;
        }

        Collections.sort(personsEvents);
        return personsEvents.get(0);
    }

    public ArrayList<String> getChildren (String personID)
    {
        ArrayList<String> children = new ArrayList<String>();

        for(int i = 0; i < persons.size(); ++i)
        {
            if(persons.get(i).getFather() != null && persons.get(i).getFather().equals(personID))
            {
                children.add(persons.get(i).getPersonID());
            }

            if(persons.get(i).getMother() != null && persons.get(i).getMother().equals(personID))
            {
                children.add(persons.get(i).getPersonID());
            }
        }

        return children;
    }

    //Sorting Methods
    private void sortEventsByType()
    {
        for(int i = 0; i < events.size(); ++i)
        {
            String type = events.get(i).getEventType().toLowerCase();

            if(!eventsByType.containsKey(type))
            {
                ArrayList<Event> eventGroup = new ArrayList<Event>();
                eventGroup.add(events.get(i));
                eventsByType.put(type,eventGroup);
                eventTypes.put(type,true);
                types.add(type);
            }

            else
            {
                eventsByType.get(type).add(events.get(i));
            }
        }
    }

    private void sortEventsByPerson()
    {
        eventsByPerson.clear();

        for(int i = 0; i < events.size(); ++i)
        {
            String ID = events.get(i).getPersonID();

            if(!eventsByPerson.containsKey(ID))
            {
                ArrayList<Event> eventGroup = new ArrayList<Event>();
                eventGroup.add(events.get(i));
                eventsByPerson.put(ID,eventGroup);
            }

            else
            {
                eventsByPerson.get(ID).add(events.get(i));
            }
        }
    }

    private void sortPersonsByID()
    {
        PersonsByID.clear();

        for(int i = 0; i < persons.size(); ++i)
        {
            String ID = persons.get(i).getPersonID();
            PersonsByID.put(ID, persons.get(i));
        }
    }

    private void sortEventsBySpouse()
    {
        eventsBySpouse.clear();

        for(int i = 0; i < events.size(); ++i)
        {
            String ID = events.get(i).getPersonID();

            if(!eventsBySpouse.containsKey(i))
            {
                ArrayList<Event> eventGroup = new ArrayList<Event>();
                eventGroup.add(events.get(i));
                String spouseID = PersonsByID.get(ID).getSpouse();

                eventGroup = eventsByPerson.get(spouseID);
                eventsBySpouse.put(ID,eventGroup);
            }
        }
    }

    private void sortEventsBySide()
    {
        Person user = PersonsByID.get(userPersonID);

        recursiveAddEvent(user.getMother(), motherEvents);
        recursiveAddEvent(user.getFather(), fatherEvents);
    }

    //Filtered Methods
    public boolean checkFilteredEvent(String eventID)
    {
        Event event = getEventFromID(eventID);
        if(!eventTypes.get(event.getEventType().toLowerCase()))
        {
            return false;
        }

        Person person = getPersonFromID(event.getPersonID());
        if((!filterMale) && (person.getGender().toLowerCase().charAt(0) == 'm'))
        {
            return false;
        }
        if((!filterFemale) && (person.getGender().toLowerCase().charAt(0) == 'f'))
        {
            return false;
        }
        if((!filterFatherSide) && (fatherEvents.contains(event)))
        {
            return false;
        }
        if((!filterMotherSide) && (motherEvents.contains(event)))
        {
            return false;
        }

        return true;
    }

    public void filterEventType(String type, boolean filter)
    {
        eventTypes.put(type.toLowerCase(), filter);
    }


    //Search Methods
    public ArrayList<Person> searchPeople(String searchString)
    {
        ArrayList<Person> people = new ArrayList<Person>();
        for(int i = 0; i < persons.size(); ++i)
        {
            if(persons.get(i).getFirstName().toLowerCase().contains(searchString))
            {
                people.add(persons.get(i));
            }
            else if(persons.get(i).getLastName().toLowerCase().contains(searchString))
            {
                people.add(persons.get(i));
            }
        }

        return people;
    }

    public ArrayList<Event> searchEvents(String searchString)
    {
        ArrayList<Event> searchedEvents = new ArrayList<Event>();
        for(int i = 0; i < events.size(); ++i) {
            if (checkFilteredEvent(events.get(i).getEventID())) {
                if (events.get(i).getCountry().toLowerCase().contains(searchString)) {
                    searchedEvents.add(events.get(i));
                } else if (events.get(i).getCity().toLowerCase().contains(searchString)) {
                    searchedEvents.add(events.get(i));
                } else if (events.get(i).getEventType().toLowerCase().contains(searchString)) {
                    searchedEvents.add(events.get(i));
                } else if (String.valueOf(events.get(i).getYear()).contains(searchString)) {
                    searchedEvents.add(events.get(i));
                }
            }
        }
        return searchedEvents;
    }

    //Miscellaneous Methods
    private void recursiveAddEvent(String userPersonID, ArrayList<Event> events)
    {
        ArrayList<Event> personEvents = eventsByPerson.get(userPersonID);
        if(personEvents != null) {
            events.addAll(personEvents);
        }
        Person person = getPersonFromID(userPersonID);
        if(person.getMother() != null) {
            recursiveAddEvent(person.getMother(), events);
        }
        if(person.getFather() != null) {
            recursiveAddEvent(person.getFather(), events);
        }
    }

    public HashMap<String, List<String>> getPersonInfo(String personID)
    {
        Person person = PersonsByID.get(personID);

        HashMap<String, List<String>> info = new HashMap<String, List<String>>();
        ArrayList<Event> personEvents = eventsByPerson.get(personID);
        ArrayList<String> lifeEvents = new ArrayList<String>();

        Collections.sort(personEvents);

        for(int i = 0; i < personEvents.size(); ++i)
        {
            if(personEvents.get(i).getEventType().toLowerCase() == "birth")
            {
                Event birth = personEvents.get(i);
                personEvents.remove(i);
                personEvents.add(0,birth);
            }
            if(personEvents.get(i).getEventType().toLowerCase() == "death")
            {
                Event death = personEvents.get(i);
                personEvents.remove(i);
                personEvents.add((personEvents.size()-1), death);
            }
        }

        for(int i = 0; i < personEvents.size(); ++i)
        {
            if(checkFilteredEvent(personEvents.get(i).getEventID())) {
                String eventInfo = personEvents.get(i).getEventType() + ": " + personEvents.get(i).getCity() + ", " +
                                   personEvents.get(i).getCountry() + " (" + personEvents.get(i).getYear() + ")";
                lifeEvents.add(eventInfo);
            }
        }

        info.put("Life Events", lifeEvents);

        familyMemberIDs.clear();
        ArrayList<String> familyMembers = new ArrayList<String>();
        if(person.getFather() != null)
        {
            String father = PersonsByID.get(person.getFather()).getFirstName() + " " +
                            PersonsByID.get(person.getFather()).getLastName() + " (Father)";
            familyMembers.add(father);
            familyMemberIDs.add(person.getFather());
        }
        if(person.getMother() != null)
        {
            String mother = PersonsByID.get(person.getMother()).getFirstName() + " " +
                            PersonsByID.get(person.getMother()).getLastName() + " (Mother)";
            familyMembers.add(mother);
            familyMemberIDs.add(person.getMother());
        }
        if(person.getSpouse() != null)
        {
            String spouse = PersonsByID.get(person.getSpouse()).getFirstName() + " " +
                            PersonsByID.get(person.getSpouse()).getLastName() + " (Spouse)";
            familyMembers.add(spouse);
            familyMemberIDs.add(person.getSpouse());
        }

        ArrayList<String> children = getChildren(person.getPersonID());

        for(int i = 0; i < children.size(); ++i)
        {
            String child = PersonsByID.get(children.get(i)).getFirstName() + " " +
                           PersonsByID.get(children.get(i)).getLastName() + " (Child)";
            familyMembers.add(child);
            familyMemberIDs.add(children.get(i));
        }

        info.put("Family", familyMembers);

        return info;
    }
}
