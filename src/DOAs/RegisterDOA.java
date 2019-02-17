package DOAs;
import models.User;
import results.RegisterResult;

/**
 * Manages the user object to database connection
 */
public class RegisterDOA
{
    private User user;
    private RegisterResult result;

    public void getUser(User user)
    {
        this.user = user;
    }

    /**
     * parses user info and sends it to the data base
     */
    public RegisterResult sendToDB()
    {
        return result;
    }

}
