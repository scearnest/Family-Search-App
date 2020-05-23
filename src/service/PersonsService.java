package service;
import requests.PersonRequest;
import requests.PersonsRequest;
import results.PersonResult;
import results.PersonsResults;

/**
 * Manages the request and DOA process for the persons command. Returns the request
 */
public class PersonsService
{
    /**
     * Uses DOA's to retrieve all family members for the specified user in the request
     * @param authToken: this is the authorization token for the command
     * @param request: this is the rest object for the command
     * @return result: contains the failure message or persons array for command
     */
    static public PersonsResults getPersons(String authToken, PersonRequest request)
    {
        PersonsResults result = new PersonsResults();
        return result;
    }
}
