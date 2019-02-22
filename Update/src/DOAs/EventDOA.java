package DOAs;
import models.Event;

import java.sql.Connection;

/**
 * This class handles all the interactions with the events table in the database
 */
public class EventDOA
{
    Event event;

    public EventDOA()
    {

    }

    /**
     * This function inserts the Event into the database
     * @param event object containing Event data
     * @return true of false for the success of operation
     */
    public boolean insert(Event event)
    {
        boolean success = false;
        return success;
    }

    /**
     * This function deletes the requested Event from the database
     * @param event object containing Event data
     * @return true of false for the success of operation
     */
    public boolean delete(Event event)
    {
        boolean success = false;
        return success;
    }


    /**
     * This function finds the Event based on their ID
     * @param EventID ID for requested Event
     * @return object containing the Events data
     */
    public Event find(String EventID)
    {
        return event;
    }

}
