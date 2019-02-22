package service;
import requests.EventsRequest;
import results.EventsResult;

/**
 * Manages the request and DOA process for the events command. Returns the request
 */
public class EventsService
{
    /**
     * Uses DOA's to retrieve all events for the user specified in the request
     * @return result: contains the failure message or event array for command
     */
    public EventsResult getEvents(String authToken, EventsRequest request)
    {
        EventsResult result = new EventsResult();
        return  result;
    }
}
