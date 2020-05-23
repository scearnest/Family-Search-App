package tests;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import models.Person;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

public class PersonDAOTest {

    Database db;
    Person person;
    Person person2;
    String descendant = "sam";

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        person = new Person("123", descendant,"sam","earnest",
                            "M","Chris","Julie","sara");
        person2 = new Person("141", descendant,"Ben","earnest",
                "M","Chris","Julie","Una");
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
            PersonDAO doa = new PersonDAO(conn);

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
            PersonDAO doa = new PersonDAO(conn);

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
            PersonDAO doa = new PersonDAO(conn);

            compPerson = doa.find(person.getPersonID());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(compPerson, null);
    }

    //Deletes all with a successful descendant username
    @Test
    public void delete() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Person compPerson = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

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
            PersonDAO doa = new PersonDAO(conn);

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
            PersonDAO doa = new PersonDAO(conn);

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
            PersonDAO doa = new PersonDAO(conn);

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
            PersonDAO doa = new PersonDAO(conn);

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

    //finds all on successful username(descendant)
    @Test
    public void findAll() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        ArrayList<Person> list = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

            doa.insert(person);
            doa.insert(person2);
            list = doa.findAll(person.getDecendant());

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
        ArrayList<Person> list = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

            doa.insert(person);
            doa.insert(person2);
            list = doa.findAll("wrongDescendant");

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        ArrayList<Person> emptyList = new ArrayList<Person>();
        assertEquals(emptyList, list);
    }

    //Deletes all with a successful descendant username
    @Test
    public void deleteAll() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Person compPerson = null;
        Person compPerson2 = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

            doa.insert(person);
            doa.insert(person2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

            doa.deleteAll(descendant);
            compPerson = doa.find(person.getPersonID());
            compPerson2 = doa.find(person2.getPersonID());
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
        Person compPerson = null;
        Person compPerson2 = null;

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

            doa.insert(person);
            doa.insert(person2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            PersonDAO doa = new PersonDAO(conn);

            doa.deleteAll("wrong");
            compPerson = doa.find(person.getPersonID());
            compPerson2 = doa.find(person2.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertNotEquals(null, compPerson);
        assertNotEquals(null, compPerson2);
    }
}