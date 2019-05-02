package serverproxy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import models.Event;
import models.Model;
import models.User;
import requests.*;
import results.*;
import static org.junit.Assert.*;

public class ServerProxyTest {

    RegisterRequest request;
    User user;
    ServerProxy server = new ServerProxy();

    LoginRequest loginRequest = new LoginRequest();
    PersonsRequest personsRequest = new PersonsRequest();
    EventsRequest eventsRequest = new EventsRequest();


    LoginResult loginResult = null;

    String username = "scearnest";
    String password = "password";

    @Before
    public void setUp() throws Exception
    {
        request = new RegisterRequest();
        request.setUserName(username);
        request.setPassword(password);
        request.setEmail("email");
        request.setFirstName("Sam");
        request.setLastName("earnest");
        request.setGender("M");

        server.setServerHost("localhost");
        server.setServerPort("8080");
    }


    @Test
    public void register()
    {
        RegisterResult result = null;
        result = server.register(request);

        assertNotEquals(result, null);
    }

    @Test
    public void registerFail()
    {
        RegisterResult result = null;
        request.setGender(null);
        result = server.register(request);

        result = server.register(request);


        //Error message would not be null if register worked
        assertNotEquals(result.getErrorMessage(), null);
    }

    @Test
    public void login()
    {
        loginRequest.setUserName(username);
        loginRequest.setPassword(password);


        loginResult = server.login(loginRequest);

        assertNotEquals(loginResult, null);
    }

    @Test
    public void loginFail()
    {
        loginRequest.setUserName("wrongName");
        loginRequest.setPassword(password);


        loginResult = server.login(loginRequest);

        //Error message would not be null if register worked
        assertNotEquals(loginResult.getErrorMessage(), null);
    }

    @Test
    public void persons()
    {

        RegisterResult result = null;
        request.setUserName("personsTest");
        result = server.register(request);

        personsRequest.setAuthToken(result.getToken());
        PersonsResult personsResult = null;
        personsResult = server.persons(personsRequest);

        assertEquals(Model.getInstance().getPersons(), personsResult.getPersons());
        assertNotEquals(personsResult, null);
    }

    @Test
    public void personsFail()
    {
        RegisterResult result = null;
        request.setUserName("personsTestFail");
        result = server.register(request);

        //Set a false auth token
        personsRequest.setAuthToken("wrongToken");
        PersonsResult personsResult = null;
        personsResult = server.persons(personsRequest);

        //Error message would not be null if register worked
        assertNotEquals(personsResult.getMessage(), null);
    }

    @Test
    public void events()
    {
        RegisterResult result = null;
        request.setUserName("eventsTest");
        result = server.register(request);

        eventsRequest.setAuthToken(result.getToken());
        EventsResult eventsResult = null;
        eventsResult = server.events(eventsRequest);


        ArrayList<Event> e = Model.getInstance().getEvents();

        assertEquals(Model.getInstance().getEvents(), eventsResult.getEvents());
        assertNotEquals(eventsResult, null);
    }

    @Test
    public void eventsFail()
    {
        RegisterResult result = null;
        request.setUserName("eventsTest");
        result = server.register(request);

        //Set a false authtoken
        eventsRequest.setAuthToken("wrongAuthToken");
        EventsResult eventsResult = null;
        eventsResult = server.events(eventsRequest);


        ArrayList<Event> e = Model.getInstance().getEvents();

        //Error message would not be null if register worked
        assertNotEquals(eventsResult.getMessage(), null);
    }

}