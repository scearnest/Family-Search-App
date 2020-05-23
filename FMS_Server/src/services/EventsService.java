package services;
import DAOs.*;
import requests.EventsRequest;
import results.EventsResult;
import models.*;
import java.util.ArrayList;

/**
 * Manages the request and DOA process for the events command. Returns the request
 */
public class EventsService
{
    /**
     * Uses DOA's to retrieve all events for the user specified in the request
     * @return result: contains the failure message or event array for command
     */
    private boolean success = true;
    private EventsResult result = new EventsResult();
    private String message;
    private Database db = new Database();
    private ArrayList<Event> events = null;
    private Token token = null;

    private boolean authorized = false;

    public EventsResult getEvents(EventsRequest request)
    {
        EventsResult result = new EventsResult();

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
            events = findEvents(token.getUsername());
        }
        catch (DataAccessException e) {}

        if(events.isEmpty())
        {
            result.setEvents(null);
            result.setMessage("No events associated with user");
            return result;
        }

        result.setEvents(events);

        return  result;
    }

    private ArrayList<Event> findEvents(String descendant) throws DataAccessException
    {
        ArrayList<Event> events;
        try
        {
            EventDAO dao = new EventDAO(db.openConnection());
            events = dao.findAll(descendant);
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
            events = null;
        }
        finally
        {
            db.closeConnection(success);
        }

        return events;
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
