package DOAs;
import requests.PersonsRequest;
import results.PersonsResults;

/**
 * This class interacts with the database to get the requested persons data
 */
public class PersonsDOA
{
    PersonsRequest request;
    PersonsResults result;

    public void setRequest(PersonsRequest request) {
        this.request = request;
    }

    /**
     * This function interacts with the database to get the persons array
     * @return result: The result object will contain the persons array
     */
    public PersonsResults getFromDB()
    {
        return result;
    }



}
