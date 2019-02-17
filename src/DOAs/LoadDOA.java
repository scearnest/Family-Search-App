package DOAs;
import requests.LoadRequest;
import results.LoadResult;


/**
 * Manages the user object to database connection
 */
public class LoadDOA
{
    private LoadRequest request;
    private LoadResult result;

    public void setRequest(LoadRequest request) {
        this.request = request;
    }

    /**
     * Takes the data from the request and send it to the DB
     * @return result: This is the result object for the load command
     */
    public LoadResult sendToDB()
    {
        return result;
    }


}
