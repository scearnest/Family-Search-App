package service;
import results.EventResult;
import requests.EventRequest;

/**
 * Manages the request and DOA process for the event command. Returns the request
 */
public class EventService
{

    /**
     * Uses DOA's to retrieve the event from the request
     * @return result: contains the failure message or event object for command
     */
    static public EventResult getEvent(String authToken, EventRequest request)
    {
        EventResult result = new EventResult();
        return  result;
    }
}
