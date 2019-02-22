package DOAs;

import models.Person;
import java.sql.Connection;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonDOATest {

    Database db;
    Person person;

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        person = new Person("123", "personname","sam","earnest",
                            "M","Chris","Julie","sara");
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
        Person compPerson = null;
        db.clearTables();
        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            doa.insert(person);

            compPerson = doa.find(person.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(person,compPerson);
    }

    @Test
    public void insertFail() throws Exception
    {
        db.clearTables();

        boolean success = true;
        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            doa.insert(person);
            doa.insert(person);
            db.closeConnection(success);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;
        }

        assertFalse(success);

        Person compPerson = person;
        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            compPerson = doa.find(person.getPersonID());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(compPerson, null);
    }

    @Test
    public void delete() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Person compPerson = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            doa.insert(person);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            doa.delete(person.getPersonID());
            compPerson = doa.find(person.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(null, compPerson);
    }


    @Test
    public void deleteFail() throws Exception
    {
        db.clearTables();
        boolean success = true;

        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            doa.delete(person.getPersonID());

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
        Person compPerson = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            doa.insert(person);
            compPerson = doa.find(person.getPersonID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }
        assertNotEquals(null, compPerson);
    }

    @Test
    public void findFail() throws Exception
    {
        db.clearTables();
        boolean success = true;
        Person compPerson = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDOA doa = new PersonDOA(conn);

            compPerson = doa.find(person.getPersonID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        assertEquals(null, compPerson);
    }
}