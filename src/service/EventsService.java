package service;
import DOAs.EventsDOA;
import requests.EventsRequest;
import results.EventsResult;

/**
 * Manages the request and DOA process for the events command. Returns the request
 */
public class EventsService
{
    EventsRequest request;
    EventsDOA doa;

    public void setRequest(EventsRequest request) {
        this.request = request;
    }

    /**
     * Runs the doa with the request for events
     * @return result: contains the failure message or event array for command
     */
    public EventsResult runDOA()
    {
        doa.setRequest(request);
        return  doa.getFromDB();
    }
}
