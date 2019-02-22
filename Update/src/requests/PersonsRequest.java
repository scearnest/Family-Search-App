package requests;

/**
 * This class contains the data to preform a persons command
 */
public class PersonsRequest
{
    private String userID;
    private String authToken;

    public String getAuthToken() {
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
