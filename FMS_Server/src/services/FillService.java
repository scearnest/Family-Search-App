package services;
import DAOs.*;
import JsonData.Generator;
import models.*;
import requests.FillRequest;
import results.FillResult;
import java.sql.Connection;
import java.util.UUID;

/**
 * Manages the request and DOA process for the fill command. Returns the request
 */
public class  FillService

{
    private FillResult result = new FillResult();
    private Database db = new Database();
    private String message = null;
    private User user = null;

    /**
     * Fills the database for the request's user and number of generations
     * @return result: returns the result object for the fill command
     */
    public FillResult fill(FillRequest request)
    {
        if(!validateUseName(request.getUsername()))
        {
            result.setMessage(message);
            return result;
        }

        if(!eraseUserHistory())
        {
            result.setMessage(message);
            return result;
        }

        Person rootPerson = makePerson();

        Generator generator = new Generator((request.getGenerations() + 1), user);
        generator.fillGenerations(rootPerson);

        result.setMessage(generator.getMessage());

        return result;
    }

    private boolean validateUseName(String username)
    {
        boolean success = false;
        try
        {
            UserDAO dao = new UserDAO(db.openConnection());
            user = dao.find(username);
            success = true;
        }
        catch (DataAccessException e)
        {
            message = "could not find user";
            success = false;
        }
        finally
        {
            try
            {
                db.closeConnection(success);
            }
            catch (DataAccessException g){}
        }

        if(user == null)
        {
            return false;
        }

        return true;
    }

    private boolean eraseUserHistory()
    {
        boolean success = false;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO personDAO = new PersonDAO(conn);
            personDAO.deleteAll(user.getUsername());
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.deleteAll(user.getUsername());
            success = true;
        }
        catch (DataAccessException e)
        {
            message = "Could not delete data";
            success = false;
            return false;
        }
        finally
        {
            try
            {
                db.closeConnection(success);
            }
            catch (DataAccessException e) {}
        }

        return true;
    }

    private Person makePerson()
    {
        Person person = new Person();
        person.setPersonID(user.getPersonID());
        person.setDescendant(user.getUsername());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        person.setGender(user.getGender());

        return person;
    }
}
