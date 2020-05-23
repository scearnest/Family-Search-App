package tests;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import DAOs.TokenDAO;
import models.Person;
import models.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.PersonsRequest;
import results.PersonsResult;
import services.PersonsService;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PersonsServiceTest {

    Database db = new Database();
    Person person;
    Person person2;
    Token token;
    String username = "145";
    String authToken = "456";
    PersonsRequest request = new PersonsRequest();
    PersonsResult result = new PersonsResult();
    ArrayList<Person> compList = new ArrayList<Person>();


    @Before
    public void setUp() throws Exception
    {
        db.createTables();
        db.clearTables();

        person = new Person("wer", username,"sam","earnest",
                "M","Chris","Julie","sara");
        person2 = new Person("141", username,"Ben","earnest",
                "M","Chris","Julie","Una");

        compList.add(person);
        compList.add(person2);

        token = new Token(authToken,username);

        try
        {
            TokenDAO dao = new TokenDAO(db.openConnection());
            dao.insert(token);
            db.closeConnection(true);
            PersonDAO personDAO = new PersonDAO(db.openConnection());
            personDAO.insert(person);
            personDAO.insert(person2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
        }

    }

    @After
    public void tearDown() throws Exception
    {
        db.clearTables();
    }

    @Test
    public void getPersons()
    {
        request.setAuthToken(authToken);
        PersonsService personsService = new PersonsService();
        result = personsService.getPersons(request);

        assertEquals(result.getPersons(), compList);
        assertEquals(result.getMessage(), null);
    }

    @Test
    public void getPersonFail()
    {
        request.setAuthToken("WrongToken");
        PersonsService personsService = new PersonsService();
        result = personsService.getPersons(request);

        assertNotEquals(result.getMessage(), null);
        assertEquals(result.getPersons(), null);
    }

}