package services;
import DAOs.*;
import results.PersonResult;
import requests.PersonRequest;
import models.*;

/**
 * Manages the request and DOA process for the person command. Returns the request
 */
public class PersonService
{

    /**
     * Uses DOA's to retrieve the person from the request
     * @return result: contains the failure message or person object for command
     */

    private boolean success = true;
    private PersonResult result = new PersonResult();
    private String message;
    private Database db = new Database();
    private Person person;

    private boolean authorized = false;

    public PersonResult getPerson(PersonRequest request)
    {
        PersonResult result = new PersonResult();
        try
        {
            person = findPerson(request.getPersonID());
            if(person != null)
            {
                authorized = checkAuth(person, request.getAuthToken());
            }
        }
        catch (DataAccessException e)
        {
            result.setMessage(message);
            return result;
        }

        if(authorized)
        {
            result.setPerson(person);
        }
        else
        {
            result.setMessage("person not authorized for this user or does not exist");
        }

        return  result;
    }

    private Person findPerson(String personID) throws DataAccessException
    {
        try
        {
            PersonDAO dao = new PersonDAO(db.openConnection());
            person = dao.find(personID);
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
            person = null;
            message = "could not find person with matching ID";
        }
        finally
        {
            db.closeConnection(success);
        }

        return person;
    }

    private boolean checkAuth(Person person, String authToken) throws DataAccessException
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
            message = "Auth token not assigned to this person";
            success = false;
            return false;
        }
        finally
        {
            db.closeConnection(success);
        }

        if(token == null)
        {
            return false;
        }

        if(token.getUsername().equals(person.getDecendant()))
        {
            return true;
        }

        return false;
    }
}
