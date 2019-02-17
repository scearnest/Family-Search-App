package service;
import requests.LoginRequest;
import results.LoginResult;
import DOAs.LoginDOA;
import models.User;

/**
 * Manages the request and DOA process for the login command. Returns the request
 */
public class LoginService
{
    private LoginRequest request;
    private LoginDOA doa;


    public void setRequest(LoginRequest request)
    {
        this.request = request;
    }

    /**
     * Runs the DOA class with the user object
     * @return result: This is the return object for the command
     */
    public LoginResult runDOA()
    {
        doa.setRequest(request);
        return doa.sendToDB();
    }
}
