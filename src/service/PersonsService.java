package service;
import requests.PersonsRequest;
import results.PersonsResults;
import DOAs.PersonsDOA;

/**
 * Manages the request and DOA process for the persons command. Returns the request
 */
public class PersonsService
{
    private PersonsRequest request;
    private PersonsDOA doa;

    public void setRequest(PersonsRequest request) {
        this.request = request;
    }

    /**
     * Runs the doa with the request for persons
     * @return result: contains the failure message or persons array for command
     */
    public PersonsResults runDOA()
    {
        doa.setRequest(request);
        return doa.getFromDB();
    }
}
