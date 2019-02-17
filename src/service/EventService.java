package service;
import results.EventResult;
import requests.EventRequest;
import DOAs.EventDOA;

/**
 * Manages the request and DOA process for the event command. Returns the request
 */
public class EventService
{
    EventRequest request;
    EventDOA doa;

    public void setRequest(EventRequest request) {
        this.request = request;
    }

    /**
     * Runs the doa with the request for event
     * @return result: contains the failure message or event object for command
     */
    public EventResult runDOA()
    {
        doa.setRequest(request);
        return  doa.getFromDB();
    }
}
