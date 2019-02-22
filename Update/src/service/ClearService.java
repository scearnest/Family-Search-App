package service;
import results.ClearResult;
import DOAs.*;

/**
 * Manages the request process for the clear command. Returns the request
 */
public class ClearService
{
    private ClearResult result;



    /**
     * Deletes all data from the database
     * @return result: This is the result object for the clear command
     */
    public ClearResult clear()
    {
        return result;
    }

}
