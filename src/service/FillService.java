package service;
import requests.FillRequest;
import results.FillResult;

/**
 * Manages the request and DOA process for the fill command. Returns the request
 */
public class FillService
{
    private FillRequest request;
    private FillResult result;

    public void setRequest(FillRequest request) {
        this.request = request;
    }

    /**
     * Fills the database for the request's user and number of generations
     * @return result: returns the result object for the fill command
     */
    public FillResult fill()
    {
        return result;
    }

}
