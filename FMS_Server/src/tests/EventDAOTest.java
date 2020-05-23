package tests;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.EventDAO;
import models.Event;

import java.sql.Connection;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;


public class EventDAOTest {

    Event event;
    Event event2;
    Database db;
    String descendant = "james";

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        event = new Event("event123",descendant,"456", 12.6f,
                            163.7f,"Italy","Rome","Birth", 1956);
        event2 = new Event("ev123",descendant,"456", 12.6f,
                163.7f,"Italy","Rome","Birth", 1956);
        db.createTables();
    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }

    @Test
    public void insert() throws DataAccessException
    {
        Event compEvent = null;
        db.clearTables();
        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);

            compEvent = doa.find(event.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(event,compEvent);
    }

    @Test
    public void insertFail() throws Exception
    {
        db.clearTables();

        boolean success = true;
        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            doa.insert(event);
            db.closeConnection(success);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;
        }

        assertFalse(success);

        Event compEvent = event;
        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            compEvent = doa.find(event.getEventID());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(compEvent, null);
    }

    @Test
    public void delete() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Event compevent = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.delete(event.getEventID());
            compevent = doa.find(event.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(null, compevent);
    }


    @Test
    public void deleteFail() throws Exception
    {
        db.clearTables();
        boolean success = true;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.delete(event.getEventID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void find() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Event compEvent = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            compEvent = doa.find(event.getEventID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }
        assertNotEquals(null, compEvent);
    }

    @Test
    public void findFail() throws Exception
    {
        db.clearTables();
        boolean success = true;
        Event compEvent = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            compEvent = doa.find(event.getEventID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        assertEquals(null, compEvent);
    }

    //Deletes all with a successful descendant username
    @Test
    public void deleteAll() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Event compPerson = null;
        Event compPerson2 = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            doa.insert(event2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.deleteAll(descendant);
            compPerson = doa.find(event.getDescendant());
            compPerson2 = doa.find(event2.getDescendant());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(null, compPerson);
        assertEquals(null, compPerson2);
    }

    //Attempts to delete all with an invalid descendant
    @Test
    public void deleteAllFail() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Event compPerson = null;
        Event compPerson2 = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            doa.insert(event2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.deleteAll("wrongDescendant");
            compPerson = doa.find(event.getEventID());
            compPerson2 = doa.find(event2.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertNotEquals(null, compPerson);
        assertNotEquals(null, compPerson2);
    }

    //finds all on successful username(descendant)
    @Test
    public void findAll() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        ArrayList<Event> list = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            doa.insert(event2);
            list = doa.findAll(event.getDescendant());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }
        assertNotEquals(null, list);
    }

    //Finds all with a false username(Descendant)
    @Test
    public void findAllFail() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        ArrayList<Event> list = null;

        try
        {
            Connection conn = db.openConnection();
            EventDAO doa = new EventDAO(conn);

            doa.insert(event);
            doa.insert(event2);
            list = doa.findAll("wrongDescendant");

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        ArrayList<Event> emptyList = new ArrayList<Event>();
        assertEquals(emptyList, list);
    }
}