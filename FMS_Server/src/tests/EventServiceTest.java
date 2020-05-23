package tests;
import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.EventDAO;
import DAOs.TokenDAO;
import models.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.Event;
import requests.EventRequest;
import results.EventResult;
import services.EventService;

import static org.junit.Assert.*;

public class EventServiceTest {

    Database db = new Database();
    Event event;
    Token token;
    String eventID = "145";
    String username = "xyz";
    String authToken = "456";
    EventRequest request = new EventRequest();
    EventResult result = new EventResult();


    @Before
    public void setUp() throws Exception
    {
        db.createTables();
        db.clearTables();

        event = new Event(eventID,username,"1242", 12.6f,
            163.7f,"Italy","Rome","Birth", 1956);
        token = new Token(authToken,username);

        try
        {
            TokenDAO dao = new TokenDAO(db.openConnection());
            dao.insert(token);
            db.closeConnection(true);
            EventDAO eventDAO = new EventDAO(db.openConnection());
            eventDAO.insert(event);
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
    public void getEvent()
    {
        request.setEventID(event.getEventID());
        request.setAuthToken(authToken);
        EventService eventService = new EventService();
        result = eventService.getEvent(request);

        assertEquals(result.getEvent(), event);
        assertEquals(result.getMessage(), null);
    }

    @Test
    public void getEventFail()
    {
        request.setEventID(event.getEventID());
        request.setAuthToken("WrongToken");
        EventService eventService = new EventService();
        result = eventService.getEvent(request);

        assertNotEquals(result.getMessage(), null);
        assertEquals(result.getEvent(), null);
    }
}