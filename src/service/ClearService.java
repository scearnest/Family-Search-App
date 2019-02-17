package service;
import results.ClearResult;
import DOAs.ClearDOA;

/**
 * Manages the request process for the clear command. Returns the request
 */
public class ClearService
{
    private ClearDOA doa = new ClearDOA();


    /**
     * Runs the clear function for the DOA
     * @return result: This is the result object for the clear command
     */
    public ClearResult runDOA()
    {
        return doa.clear();
    }

}
