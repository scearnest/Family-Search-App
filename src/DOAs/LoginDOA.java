package DOAs;
import models.User;
import results.LoginResult;
import requests.LoginRequest;

/**
 * Manages the user object to database connection for the login command
 */
public class LoginDOA
{
    private LoginRequest request;
    private LoginResult result;

    public void setRequest(LoginRequest request) {
        this.request = request;
    }


    /**
     * parses user info and sends it to the data base
     * @return result: contains the token, username, and person ID
     */
    public LoginResult sendToDB()
    {
        return result;
    }

}
