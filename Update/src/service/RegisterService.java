package service;
import DOAs.UserDOA;
import requests.RegisterRequest;
import results.RegisterResult;
import models.User;

/**
 * Manages the request and DOA process for the register command. Returns the request
 */
public class RegisterService
{
    private UserDOA doa;
    private RegisterRequest request;
    private RegisterResult result;


    public void setRequest(RegisterRequest request)
    {
        this.request = request;
    }

    /**
     * Creates new user, generates 4 generations of ancestors, logs user in, and returns result
     * @return result: This is the result object for the command
     */
    public RegisterResult registerUser()
    {
        return result;
    }
}  
