package DOAs;
import results.EventResult;
import requests.EventRequest;

/**
 * This class interacts with the database to preform the event command
 */
public class EventDOA
{
    private EventRequest request;
    private EventResult result;

    public void setRequest(EventRequest request) {
        this.request = request;
    }

    /**
     * Uses request to interact with the database and returns the event
     * @return result: Contains the data for the result of the event command
     */
    public EventResult getFromDB()
    {
        return result;
    }

}
