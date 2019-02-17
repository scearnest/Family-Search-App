package DOAs;
import requests.PersonRequest;
import results.PersonResult;

/**
 * Manages the request to database process for person command
 */
public class PersonDOA
{
    private PersonRequest request;
    private PersonResult result;

    public void setRequest(PersonRequest request) {
        this.request = request;
    }

    /**
     * This function interacts with the database to get the requested person
     * @return result: This object contains the person object
     */
    public PersonResult getFromDB()
    {
        return result;
    }


}
