package services;
import DAOs.DataAccessException;
import DAOs.Database;
import results.ClearResult;

/**
 * Manages the request process for the clear command. Returns the request
 */
public class ClearService
{
    private String message;

    /**
     * Deletes all data from the database
     * @return result: This is the result object for the clear command
     */
    public ClearResult clear()
    {
        Database db = new Database();
        try
        {
            db.clearTables();
        }
        catch (DataAccessException e)
        {
            message = "Error clearing table";
            ClearResult result = new ClearResult(message);
            return result;
        }

        message = "Clear succeeded.";
        ClearResult result = new ClearResult(message);
        return result;
    }
}
