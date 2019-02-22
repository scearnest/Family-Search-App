package service;
import requests.LoginRequest;
import results.LoginResult;
import DOAs.UserDOA;
import models.User;

/**
 * Manages the request and DOA process for the login command. Returns the request
 */
public class LoginService
{
    private LoginRequest request;
    private UserDOA doa;
    private LoginResult result;


    public void setRequest(LoginRequest request)
    {
        this.request = request;
    }

    /**
     * Logs in the user and returns the result
     * @return result: This is the return object for the command
     */
    public LoginResult loginUser()
    {
        return result;
    }
}
