package tests;
import DAOs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.*;
import results.LoginResult;
import services.LoginService;
import services.RegisterService;

import static org.junit.Assert.*;

public class LoginServiceTest {

    Database db;
    LoginRequest loginRQ;
    LoginResult result;
    RegisterRequest registerRQ;
    String userName = "scearnest";
    String passWord = "pass";

    @Before
    public void setUp() throws Exception
    {
        db = new Database();
        db.clearTables();
        loginRQ = new LoginRequest();
        registerRQ = new RegisterRequest();
        registerRQ.setUserName(userName);
        registerRQ.setPassword(passWord);
        registerRQ.setFirstName("Samuel");
        registerRQ.setLastName("Earnest");
        registerRQ.setGender("M");
        registerRQ.setEmail("mail");

        db.createTables();
    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }
    @Test
    public void loginUser()
    {
        RegisterService regService = new RegisterService(registerRQ);
        regService.registerUser();

        loginRQ.setUserName(userName);
        loginRQ.setPassword(passWord);

        LoginService logService = new LoginService(loginRQ);
        result = logService.loginUser();

        assertEquals(result.getUserName(), userName);
        assertEquals(result.getErrorMessage(), null);
    }

    @Test
    public void loginUserFail()
    {
        loginRQ.setUserName(userName);
        loginRQ.setPassword(passWord);

        LoginService logService = new LoginService(loginRQ);
        result = logService.loginUser();

        assertNotEquals(result.getErrorMessage(), null);
    }
}