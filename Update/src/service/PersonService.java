package service;
import requests.PersonRequest;
import results.PersonResult;

/**
 * Manages the request and DOA process for the person command. Returns the request
 */
public class PersonService
{
    /**
     * Uses DOAs to retrieve the person associated with the ID in the request
     * @return result: contains the failure message or person object for command
     */
    static public PersonResult getPerson(String authToken, PersonRequest request)
    {
        PersonResult result = new PersonResult();
        return result;
    }
}
