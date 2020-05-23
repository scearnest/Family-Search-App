package services;
import DAOs.*;
import models.*;
import requests.LoadRequest;
import results.LoadResult;

/**
 *  Manages the request and DOA process for the load command. Returns the result
 */
public class LoadService
{
    private LoadRequest request;
    private LoadResult result = new LoadResult();
    private String message;

    private int numUsers;
    private int numPersons;
    private int numEvents;

    private boolean success = true;

    public LoadService(LoadRequest request) {
        this.request = request;
    }

    /**
     * Clears all data from the database and then loads the request's data into the database
     * @return result: returns the result object for the load command
     */
    public LoadResult load()
    {
        ClearService clearService = new ClearService();
        clearService.clear();

        try
        {
            addUsers(request.getUser());
            addPersons(request.getPersons());
            addEvents(request.getEvents());
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            result.setMessage(message);
        }

        if(success)
        {
            message = "Successfully added " + numUsers + " users, " + numPersons + " persons, and " +
                    numEvents + " events to the database.";
            result.setMessage(message);
        }

        return result;
    }

    private void addUsers(User[] users) throws DataAccessException
    {
        Database db = new Database();

        System.out.println(users.length);

        for(int i = 0; i < users.length; ++i)
        {
            try
            {
                UserDAO dao = new UserDAO(db.openConnection());
                dao.insert(users[i]);
                success = true;
            }
            catch (DataAccessException e)
            {
                success = false;
                message = "Could not add user at " + i;
                throw e;
            }
            finally
            {
                db.closeConnection(success);
            }
        }

        numUsers = users.length;
    }

    private void addPersons(Person[] persons) throws DataAccessException
    {
        Database db = new Database();
        for(int i = 0; i < persons.length; ++i)
        {
            try
            {
                PersonDAO dao = new PersonDAO(db.openConnection());
                dao.insert(persons[i]);
                success = true;
            }
            catch (DataAccessException e)
            {
                success = false;
                message = "Could not add person at " + i;
                throw e;
            }
            finally
            {
                db.closeConnection(success);
            }
        }

        numPersons = persons.length;
    }

    private void addEvents(Event[] events) throws DataAccessException
    {
        Database db = new Database();
        for(int i = 0; i < events.length; ++i)
        {
            try
            {
                EventDAO dao = new EventDAO(db.openConnection());
                dao.insert(events[i]);
                success = true;
            }
            catch (DataAccessException e)
            {
                success = false;
                message = "Could not add user at " + i;
                throw e;
            }
            finally
            {
                db.closeConnection(success);
            }
        }

        numEvents = events.length;
    }
}
