package service;
import requests.LoadRequest;
import results.LoadResult;
import DOAs.LoadDOA;

/**
 *  Manages the request and DOA process for the load command. Returns the result
 */
public class LoadService
{
    private LoadRequest request;
    private LoadDOA doa = new LoadDOA();

    public void setRequest(LoadRequest request) {
        this.request = request;
    }

    /**
     * Runs the DOA object for the load function with the request object
     * @return result: returns the result object for the load command
     */
    public LoadResult runDOA()
    {
        doa.setRequest(request);
        return doa.sendToDB();
    }

}
