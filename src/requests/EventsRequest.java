package requests;

/**
 * Contains the request data for the events command
 */
public class EventsRequest
{
    private String userID;
    private String authToken;

    public String getAuthToke() {
        return authToken;
    }

    public void setAuthToke(String authToke) {
        this.authToken = authToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
