package service;
import DOAs.RegisterDOA;
import requests.RegisterRequest;
import results.RegisterResult;
import models.User;

/**
 * Manages the request and DOA process for the register command. Returns the request
 */
public class RegisterService
{
    private RegisterDOA doa = new RegisterDOA();
    private RegisterRequest request;
    private RegisterResult result;


    public void setRequest(RegisterRequest request)
    {
        this.request = request;
    }


    /**
     * Runs the DOA class and returns the result
     * @return result: This is the result object for the command
     */
    public RegisterResult runDOA()
    {
        doa.getUser(request.getUser());
        return doa.sendToDB();
    }
}
