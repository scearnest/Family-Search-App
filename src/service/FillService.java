package service;
import requests.FillRequest;
import results.FillResult;
import DOAs.FillDOA;

/**
 * Manages the request and DOA process for the fill command. Returns the request
 */
public class FillService
{
    private FillRequest request;
    private FillDOA doa = new FillDOA();

    public void setRequest(FillRequest request) {
        this.request = request;
    }

    /**
     * Runs the fill function on the DOA with the request
     * @return result: returns the result object for the fill command
     */
    public FillResult runDOA()
    {
        doa.setRequest(request);
        return doa.fill();
    }

}
