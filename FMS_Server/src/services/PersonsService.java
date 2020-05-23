package services;
import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import DAOs.TokenDAO;
import models.*;
import requests.PersonsRequest;
import results.PersonsResult;
import java.util.ArrayList;

/**
 * Manages the request and DOA process for the persons command. Returns the request
 */
public class PersonsService
{
    /**
     * Uses DOA's to retrieve all family members for the specified user in the request
     * @return result: contains the failure message or persons array for command
     */
    private boolean success = true;
    private PersonsResult result = new PersonsResult();
    private String message;
    private Database db = new Database();
    private ArrayList<Person> persons = null;
    private Token token = null;

    private boolean authorized = false;

    public PersonsResult getPersons(PersonsRequest request)
    {
        PersonsResult result = new PersonsResult();

        try
        {
            token = getToken(request.getAuthToken());
        }
        catch (DataAccessException e) {}

        if(token == null)
        {
            result.setMessage("Could not find user with given authToken");
            return result;
        }

        try
        {
            persons = findPersons(token.getUsername());
        }
        catch (DataAccessException e) {}

        if(persons.isEmpty())
        {
            result.setPersons(null);
            result.setMessage("No persons associated with user");
            return result;
        }

        result.setPersons(persons);

        return  result;
    }

    private ArrayList<Person> findPersons(String descendant) throws DataAccessException
    {
        ArrayList<Person> persons;
        try
        {
            PersonDAO dao = new PersonDAO(db.openConnection());
            persons = dao.findAll(descendant);
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
            persons = null;
        }
        finally
        {
            db.closeConnection(success);
        }

        return persons;
    }

    private Token getToken(String authToken) throws DataAccessException
    {
        Token token = null;
        try
        {
            TokenDAO dao = new TokenDAO(db.openConnection());
            token = dao.find(authToken);
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        finally
        {
            db.closeConnection(success);
        }

        return token;
    }
}
