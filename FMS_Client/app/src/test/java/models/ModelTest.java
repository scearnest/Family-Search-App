package models;

import android.media.midi.MidiOutputPort;
import android.view.Display;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class ModelTest {

    Person person1 = new Person();
    Person child = new Person();
    Person father = new Person();
    Person mother = new Person();
    Person spouse = new Person();

    Event event1;
    Event event2;
    Event event3;

    @Before
    public void setUp() throws Exception {

        person1.setPersonID("1");
        person1.setFirstName("James");
        person1.setLastName("Smith");
        person1.setFather("father");
        person1.setMother("mother");
        person1.setSpouse("spouse");

        child.setPersonID("child");
        child.setFirstName("Baby");
        child.setLastName("Smith");
        child.setFather("1");
        child.setMother("spouse");
        child.setSpouse(null);

        father.setPersonID("father");
        father.setFirstName("Father");
        father.setLastName("Smith");
        father.setFather(null);
        father.setMother(null);
        father.setSpouse("mother");

        mother.setPersonID("mother");
        mother.setFirstName("Mother");
        mother.setLastName("Smith");
        mother.setFather(null);
        mother.setMother(null);
        mother.setSpouse("father");

        spouse.setPersonID("spouse");
        spouse.setFirstName("Spouse");
        spouse.setLastName("Smith");
        spouse.setFather(null);
        spouse.setMother(null);
        spouse.setSpouse("1");

        event1 = new Event("eventID1","1","1", 12.6f,
                163.7f,"Italy","Rome","Birth", 1956);
        event2 = new Event("eventID2","1","1", 12.6f,
                163.7f,"Italy","Venice","Death", 1997);
        event3 = new Event("eventID2","1","1", 12.6f,
                163.7f,"Italy","Pisco","Death", 2001);


        ArrayList<Person> people = new ArrayList<Person>();
        people.add(person1);
        people.add(father);
        people.add(mother);
        people.add(spouse);
        people.add(child);

        ArrayList<Event> events = new ArrayList<Event>();
        events.add(event1);
        events.add(event2);

        Model.getInstance().setUserPersonID("1");
        Model.getInstance().setPersons(people);
        Model.getInstance().setEvents(events);


    }

    @After
    public void tearDown() throws Exception {
    }

    //Tests ability to search for specific people
    @Test
    public void getPersonFromID() {

        Person result = Model.getInstance().getPersonFromID("1");

        assertEquals(person1.getPersonID(), result.getPersonID());
    }

    //Tests ability to search for spouse (Family Relationships)
    @Test
    public void getSpouse() {

        Person result = Model.getInstance().getPersonFromID(person1.getSpouse());

        assertEquals(person1.getSpouse(), result.getPersonID());
    }

    //Tests searching for a spouse that doesn't exist
    @Test
    public void getSpouseFail() {

        Person result = Model.getInstance().getPersonFromID(child.getSpouse());

        assertEquals(null, result);
    }


    //Tests ability to search for father (Family Relationships)
    @Test
    public void getFather() {

        Person result = Model.getInstance().getFather(person1.getPersonID());

        assertEquals(person1.getFather(), result.getPersonID());
    }

    //Tests someone that doesn't have a father
    @Test
    public void getFatherFail() {

        Person result = Model.getInstance().getFather(father.getPersonID());

        assertEquals(result, null);
    }

    //Tests ability to search for mother (Family Relationships)
    @Test
    public void getMother() {

        Person result = Model.getInstance().getMother(person1.getPersonID());

        assertEquals(person1.getMother(), result.getPersonID());
    }

    //Tests someone that doesn't have a mother
    @Test
    public void getMotherFail() {

        Person result = Model.getInstance().getMother(father.getPersonID());

        assertEquals(result, null);
    }

    //Tests ability to search for children (Family Relationships)
    @Test
    public void getChildren() {

        ArrayList<String> result = Model.getInstance().getChildren(person1.getPersonID());

        assertEquals(result.get(0), child.getPersonID());

    }

    //Tests someone that doesn't have a child
    @Test
    public void getChildrenFail() {

        ArrayList<String> result = Model.getInstance().getChildren(child.getPersonID());

        assertEquals(result.isEmpty(), true);

    }

    //Tests filtering abilities based on settings
    @Test
    public void checkFilteredEvent() {

        Model.getInstance().filterEventType("Birth", false);

        //Now Birth should be filtered and Death should not

        assertEquals(Model.getInstance().checkFilteredEvent(event1.getEventID()), false);
        assertEquals(Model.getInstance().checkFilteredEvent(event2.getEventID()), true);
    }

    //Tests ability to search for people success and fail
    @Test
    public void searchPeople() {

        //searches for the first few letters of James
        assertEquals(Model.getInstance().searchPeople("jam").get(0).getPersonID(), person1.getPersonID());

        //searches for a string that doesn't exist in the person set
        assertEquals(Model.getInstance().searchPeople("qu").isEmpty(), true);
    }

    //Tests ability to search for events success and fail
    @Test
    public void searchEvents() {

        //searches for the event that happened in Rome
        assertEquals(Model.getInstance().searchEvents("rome").get(0).getEventID(), event1.getEventID());

        //searches for a string that doesn't exist in the event set
        assertEquals(Model.getInstance().searchEvents("qu").isEmpty(), true);

    }

    //Tests ability to chronologically sort events
    @Test
    public void sortEvents() {

        ArrayList<Event> unsortedEvents = new ArrayList<Event>();

        //First we'll place the events in out of order
        unsortedEvents.add(event3);
        unsortedEvents.add(event1);
        unsortedEvents.add(event2);

        //Now we'll call Collections sort which calls the event compareTo method which I've overwritten to compare years
        Collections.sort(unsortedEvents);

        //Now we'll check to make sure the events are ordered correctly in our ArrayList
        assertEquals(unsortedEvents.get(0).getEventID(), event1.getEventID());
        assertEquals(unsortedEvents.get(1).getEventID(), event2.getEventID());
        assertEquals(unsortedEvents.get(2).getEventID(), event3.getEventID());
    }
}