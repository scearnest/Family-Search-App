package DOAs;

import models.User;
import java.sql.Connection;
import org.junit.*;
import static org.junit.Assert.*;


public class UserDOATest
{
    Database db;
    User user;

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        user = new User("username", "password", "email@email.com",
                        "firstName", "lastName", "M", "123");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception
    {
        //db.clearTables();
    }

    @Test
    public void insert() throws Exception
    {
        User compUser = null;
        db.clearTables();
        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            doa.insert(user);

            compUser = doa.find(user.getuserID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(user,compUser);
    }

    @Test
    public void insertFail() throws Exception
    {
        db.clearTables();

        boolean success = true;
        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            doa.insert(user);
            doa.insert(user);
            db.closeConnection(success);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;
        }

        assertFalse(success);

        User compUser = user;
        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            compUser = doa.find(user.getuserID());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(compUser, null);
    }

    @Test
    public void delete() throws Exception
    {
        db.clearTables();
        boolean success = true;
        User compUser = null;

        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            doa.insert(user);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            doa.delete(user.getuserID());
            compUser = doa.find(user.getuserID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(null, compUser);
    }

    @Test
    public void deleteFail() throws Exception
    {
        db.clearTables();
        boolean success = true;

        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            doa.delete(user.getuserID());

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
    public void find() throws Exception
    {
        db.clearTables();
        boolean success = true;
        User compUser = null;

        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            doa.insert(user);
            compUser = doa.find(user.getuserID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        assertNotEquals(null, compUser);
    }

    @Test
    public void findFail() throws Exception
    {
        db.clearTables();
        boolean success = true;
        User compUser = null;

        try
        {
            Connection conn = db.openConnection();
            UserDOA doa = new UserDOA(conn);

            compUser = doa.find(user.getuserID());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        assertEquals(null, compUser);
    }
}