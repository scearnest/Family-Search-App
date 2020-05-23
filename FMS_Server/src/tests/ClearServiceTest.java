package tests;
import DAOs.*;
import models.Event;
import models.Person;
import models.Token;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import results.ClearResult;
import services.ClearService;

import java.sql.Connection;
import static org.junit.Assert.*;

public class ClearServiceTest {

    Database db;
    Event event;
    Person person;
    Token token;
    User user;

    UserDAO userDAO;
    EventDAO eventDAO;
    PersonDAO personDAO;
    TokenDAO tokenDAO;

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        event = new Event("event123","James","456", 12.6f,
                163.7f,"Italy","Rome","Birth", 1956);
        person = new Person("123", "personname","sam","earnest",
                "M","Chris","Julie","sara");
        token = new Token("123","465");
        user = new User("username", "password", "email@email.com",
                "firstName", "lastName", "M", "123");
        db.createTables();

        try
        {
            Connection conn = db.openConnection();
            userDAO = new UserDAO(conn);
            userDAO.insert(user);
            eventDAO = new EventDAO(conn);
            eventDAO.insert(event);
            personDAO = new PersonDAO(conn);
            personDAO.insert(person);
            tokenDAO = new TokenDAO(conn);
            tokenDAO.insert(token);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }

    @Test
    public void clear()
    {
        ClearResult result;
        ClearService service = new ClearService();
        result = service.clear();

        Event compEvent = null;
        Person compPerson = null;
        Token compToken = null;
        User compUser = null;

        try
        {
            Connection conn = db.openConnection();
            userDAO = new UserDAO(conn);
            compUser = userDAO.find(user.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(user, compUser);

        try
        {
            Connection conn = db.openConnection();
            personDAO = new PersonDAO(conn);
            compPerson = personDAO.find(person.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(person, compPerson);

        try
        {
            Connection conn = db.openConnection();
            eventDAO = new EventDAO(conn);
            compEvent = eventDAO.find(event.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(event, compEvent);

        try
        {
            Connection conn = db.openConnection();
            tokenDAO = new TokenDAO(conn);
            compToken = tokenDAO.find(token.getToken());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(token, compToken);
    }

    //Attempts to clear an empty table
    @Test
    public void clearFail()
    {
        try
        {
            db.clearTables();
        }
        catch (DataAccessException e){}

        ClearResult result;
        ClearService service = new ClearService();
        result = service.clear();

        Event compEvent = null;
        Person compPerson = null;
        Token compToken = null;
        User compUser = null;

        try
        {
            Connection conn = db.openConnection();
            userDAO = new UserDAO(conn);
            compUser = userDAO.find(user.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(user, compUser);

        try
        {
            Connection conn = db.openConnection();
            personDAO = new PersonDAO(conn);
            compPerson = personDAO.find(person.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(person, compPerson);

        try
        {
            Connection conn = db.openConnection();
            eventDAO = new EventDAO(conn);
            compEvent = eventDAO.find(event.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(event, compEvent);

        try
        {
            Connection conn = db.openConnection();
            tokenDAO = new TokenDAO(conn);
            compToken = tokenDAO.find(token.getToken());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        assertNotEquals(token, compToken);
    }
}