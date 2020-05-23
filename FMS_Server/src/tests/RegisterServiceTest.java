package tests;

import DAOs.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.RegisterRequest;
import results.RegisterResult;
import services.RegisterService;

import static org.junit.Assert.*;

public class RegisterServiceTest {

    Database db;
    RegisterRequest rq;
    String userName = "scearnest";

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        db.clearTables();
        rq = new RegisterRequest();
        rq.setUserName(userName);
        rq.setPassword("9assWord");
        rq.setFirstName("sam");
        rq.setLastName("earnest");
        rq.setEmail("email@gmail.com");
        rq.setGender("M");

        db.createTables();

    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }

    @Test
    public void registerUser() {
        RegisterResult result = new RegisterResult();
        RegisterService service = new RegisterService(rq);
        result = service.registerUser();

        assertEquals(result.getUserName(),userName);
        assertEquals(result.getErrorMessage(), null );
    }

    @Test
    public void registerUserFail()
    {
        RegisterResult result = new RegisterResult();
        RegisterService service = new RegisterService(rq);
        service.registerUser();
        result = service.registerUser();

        assertNotEquals(result.getErrorMessage(), null);
    }
}