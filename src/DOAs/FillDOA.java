package DOAs;
import requests.FillRequest;
import results.FillResult;

/**
 * This class interacts with the database to preform the fill command
 */
public class FillDOA
{
    private FillRequest request;
    private FillResult result;

    public void setRequest(FillRequest request) {
        this.request = request;
    }

    /**
     * This function interacts with the database to preform the fill command
     * @return result: This is the result object for the fill command
     */
    public FillResult fill()
    {
     return result;
    }



}
