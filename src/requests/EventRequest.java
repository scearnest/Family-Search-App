package requests;

/**
 * This class contains the ID to request an event command
 */
public class EventRequest
{

    private String eventID;
    private String authToken;

    public String getAuthToke() {
        return authToken;
    }

    public void setAuthToke(String authToke) {
        this.authToken = authToken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

}
