package tests;
import DAOs.*;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoadRequest;
import results.LoadResult;
import services.LoadService;

import static org.junit.Assert.*;

public class LoadServiceTest {

    Database db;
    LoadRequest request = new LoadRequest();

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        db.clearTables();

        Event event = new Event("event123","James","456", 12.6f,
                163.7f,"Italy","Rome","Birth", 1956);
        Person person1 = new Person("123", "personname","sam","earnest",
                "M","Chris","Julie","sara");
        Person person2 = new Person("456", "sceanrest","jan","earnest",
                "F","Chuck","Becky","Bert");

        User user = new User("username", "password", "email@email.com",
                "firstName", "lastName", "M", "123");

        User[] users = new User[1];
        users[0] = user;

        Event[] events = new Event[1];
        events[0] = event;

        Person[] persons = new Person[2];
        persons[0] = person1;
        persons[1] = person2;

        request.setUser(users);
        request.setEvents(events);
        request.setPersons(persons);


        db.createTables();

    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }
    @Test
    public void load()
    {
        LoadResult result;
        LoadService service = new LoadService(request);
        result = service.load();

        String compMessage = "Successfully added 1 users, 2 persons, and 1 events to the database.";

        assertEquals(result.getMessage(), compMessage);
    }

    @Test
    public void loadFail()
    {
        LoadResult result;
        LoadService service = new LoadService(request);
        service.load();
        result = service.load();
        String compMessage = "Could not add user at 0";

        assertEquals(result.getMessage(), compMessage);
    }
}