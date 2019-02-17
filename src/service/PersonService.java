package service;
import requests.PersonRequest;
import results.PersonResult;
import DOAs.PersonDOA;

/**
 * Manages the request and DOA process for the person command. Returns the request
 */
public class PersonService
{
    private PersonRequest request;
    private PersonDOA doa;

    public void setRequest(PersonRequest request) {
        this.request = request;
    }

    /**
     * Runs the doa with the request for person
     * @return result: contains the failure message or person object for command
     */
    public PersonResult runDOA()
    {
        doa.setRequest(request);
        return doa.getFromDB();
    }
}
