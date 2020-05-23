package tests;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.TokenDAO;
import models.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import static org.junit.Assert.*;

public class TokenDAOTest
{
    Database db;
    Token token;

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        token = new Token("123","465");
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
        Token compToken = null;
        db.clearTables();
        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            doa.insert(token);

            compToken = doa.find(token.getToken());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(token,compToken);
    }

    @Test
    public void insertFail() throws Exception
    {
        db.clearTables();

        boolean success = true;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            doa.insert(token);
            doa.insert(token);
            db.closeConnection(success);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;
        }

        assertFalse(success);

        Token compToken = token;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            compToken = doa.find(token.getToken());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(compToken, null);
    }

    @Test
    public void delete() throws DataAccessException
    {
        db.clearTables();
        boolean success = true;
        Token compToken = null;

        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            doa.insert(token);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            doa.delete(token.getToken());
            compToken = doa.find(token.getToken());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }

        assertEquals(null, compToken);
    }


    @Test
    public void deleteFail() throws Exception
    {
        db.clearTables();
        boolean success = true;

        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            doa.delete(token.getToken());

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
        Token compToken = null;

        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            doa.insert(token);
            compToken = doa.find(token.getToken());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }
        assertNotEquals(null, compToken);
    }

    @Test
    public void findFail() throws Exception
    {
        db.clearTables();
        boolean success = true;
        Token compToken = null;

        try
        {
            Connection conn = db.openConnection();
            TokenDAO doa = new TokenDAO(conn);

            compToken = doa.find(token.getToken());

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            success = false;

        }

        assertEquals(null, compToken);
    }
}