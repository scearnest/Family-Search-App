package requests;

/**
 * This class contains the data to preform a persons command
 */
public class PersonsRequest
{
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
