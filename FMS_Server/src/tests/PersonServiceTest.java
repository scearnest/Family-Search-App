package tests;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import DAOs.TokenDAO;
import models.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.Person;
import requests.PersonRequest;
import results.PersonResult;
import services.PersonService;


import static org.junit.Assert.*;

public class PersonServiceTest {

    Database db = new Database();
    Person person;
    Token token;
    String username = "145";
    String authToken = "456";
    PersonRequest request = new PersonRequest();
    PersonResult result = new PersonResult();


    @Before
    public void setUp() throws Exception
    {
        db.createTables();
        db.clearTables();

        person = new Person("wer", username,"sam","earnest",
                "M","Chris","Julie","sara");
        token = new Token(authToken,username);

        try
        {
            TokenDAO dao = new TokenDAO(db.openConnection());
            dao.insert(token);
            db.closeConnection(true);
            PersonDAO personDAO = new PersonDAO(db.openConnection());
            personDAO.insert(person);
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
    public void getperson()
    {
        request.setPersonID(person.getPersonID());
        request.setAuthToken(authToken);
        PersonService personService = new PersonService();
        result = personService.getPerson(request);

        assertEquals(result.getPerson(), person);
        assertEquals(result.getMessage(), null);
    }

    @Test
    public void getpersonFail()
    {
        request.setPersonID(person.getPersonID());
        request.setAuthToken("123");
        PersonService personService = new PersonService();
        result = personService.getPerson(request);

        assertNotEquals(result.getMessage(), null);
        assertEquals(result.getPerson(), null);
    }
}