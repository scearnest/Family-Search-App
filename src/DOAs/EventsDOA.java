package DOAs;
import requests.EventsRequest;
import results.EventsResult;

/**
 * This class interacts with the database to preform the events command
 */
public class EventsDOA
{
    EventsRequest request;
    EventsResult result;

    public void setRequest(EventsRequest request) {
        this.request = request;
    }

    /**
     * Interacts with database and populates the result object
     * @return result: Contains the events array or failure message
     */
    public EventsResult getFromDB()
    {
        return result;
    }
}
