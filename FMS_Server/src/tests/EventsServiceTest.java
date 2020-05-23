package tests;
import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.EventDAO;
import DAOs.TokenDAO;
import models.Event;
import models.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.EventsRequest;
import results.EventsResult;
import services.EventsService;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class EventsServiceTest 
{
    Database db = new Database();
    Event event;
    Event event2;
    Token token;
    String username = "145";
    String authToken = "456";
    EventsRequest request = new EventsRequest();
    EventsResult result = new EventsResult();
    ArrayList<Event> compList = new ArrayList<Event>();

    @Before
    public void setUp() throws Exception
    {
        db.createTables();
        db.clearTables();

        event = new Event("event123",username,"456", 12.6f,
                163.7f,"Italy","Rome","Birth", 1956);
        event2 = new Event("e143",username,"456", 12.6f,
                163.7f,"Italy","Rome","Death", 1956);

        compList.add(event);
        compList.add(event2);

        token = new Token(authToken,username);

        try
        {
            TokenDAO dao = new TokenDAO(db.openConnection());
            dao.insert(token);
            db.closeConnection(true);
            EventDAO eventDAO = new EventDAO(db.openConnection());
            eventDAO.insert(event);
            db.closeConnection(true);
            EventDAO eventDAO2 = new EventDAO(db.openConnection());
            eventDAO2.insert(event2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
        }

    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }

    @Test
    public void getEvents()
    {
        request.setAuthToken(authToken);
        EventsService eventsService = new EventsService();
        result = eventsService.getEvents(request);

        assertEquals(result.getEvents(), compList);
        assertEquals(result.getMessage(), null);
    }

    @Test
    public void getEventFail()
    {
        request.setAuthToken("WrongEvent");
        EventsService eventsService = new EventsService();
        result = eventsService.getEvents(request);

        assertNotEquals(result.getMessage(), null);
        assertEquals(result.getEvents(), null);
    }

}