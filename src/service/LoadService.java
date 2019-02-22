package service;
import requests.LoadRequest;
import results.LoadResult;

/**
 *  Manages the request and DOA process for the load command. Returns the result
 */
public class LoadService
{
    private LoadRequest request;
    private LoadResult result;

    public void setRequest(LoadRequest request) {
        this.request = request;
    }

    /**
     * Clears all data from the database and then loads the request's data into the database
     * @return result: returns the result object for the load command
     */
    public LoadResult load()
    {
        return result;
    }

}
