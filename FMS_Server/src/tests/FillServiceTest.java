package tests;
import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.FillRequest;
import services.FillService;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class FillServiceTest {

    private Database db = new Database();
    private User user = null;

    @Before
    public void setUp() throws Exception
    {
        db.createTables();
        db.clearTables();
        user = new User("scearnest","pass", "easm", "Sam", "Earnest",
                "M", "123");

        UserDAO dao = new UserDAO(db.openConnection());
        dao.insert(user);
        db.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }


    @Test
    public void fill()
    {
        FillRequest request = new FillRequest();
        request.setGenerations(2);
        request.setUsername(user.getUsername());
        FillService service = new FillService();
        service.fill(request);

        ArrayList<Person> personArrayList = new ArrayList<Person>();

        try
        {
            PersonDAO dao = new PersonDAO(db.openConnection());
            personArrayList = dao.findAll(user.getUsername());
            db.closeConnection(true);
        }
        catch (DataAccessException e){}

        assertEquals(personArrayList.size(), 7);
    }

    @Test
    public void fillFail()
    {
        FillRequest request = new FillRequest();
        request.setGenerations(2);
        request.setUsername("wrong_userName");
        FillService service = new FillService();
        service.fill(request);

        ArrayList<Person> personArrayList = null;

        try
        {
            PersonDAO dao = new PersonDAO(db.openConnection());
            personArrayList = dao.findAll(user.getUsername());
            db.closeConnection(true);
        }
        catch (DataAccessException e){}

        assertEquals(personArrayList.size(), 0 );
    }
}