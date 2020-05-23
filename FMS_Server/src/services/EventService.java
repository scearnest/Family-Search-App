package services;
import DAOs.*;
import results.EventResult;
import requests.EventRequest;
import models.*;

/**
 * Manages the request and DOA process for the event command. Returns the request
 */
public class EventService
{

    /**
     * Uses DOA's to retrieve the event from the request
     * @return result: contains the failure message or event object for command
     */

    private boolean success = true;
    private EventResult result = new EventResult();
    private String message;
    private Database db = new Database();
    private Event event;
    private boolean authorized = false;

    public EventResult getEvent(EventRequest request)
    {
        EventResult result = new EventResult();
        try
        {
            event = findEvent(request.getEventID());
            if(event != null)
            {
                authorized = checkAuth(event, request.getAuthToken());
            }
        }
        catch (DataAccessException e)
        {
            result.setMessage(message);
            return result;
        }
        if(authorized)
        {
            result.setEvent(event);
        }
        else
        {
            result.setMessage("Event not authorized for this user or does not exist");
        }
        return  result;
    }

    private Event findEvent(String eventID) throws DataAccessException
    {
        try
        {
            EventDAO dao = new EventDAO(db.openConnection());
            event = dao.find(eventID);
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
            event = null;
            message = "could not find Event with matching ID";
        }
        finally
        {
            db.closeConnection(success);
        }

        return event;
    }

    private boolean checkAuth(Event event, String authToken) throws DataAccessException
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
            message = "Auth token not assigned to this event";
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

        if(token.getUsername().equals(event.getDescendant()))
        {
            return true;
        }

        return false;
    }
}
