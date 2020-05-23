package requests;

/**
 * Contains the request data for the events command
 */
public class EventsRequest
{
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
