package DOAs;
import models.User;

/**
 * This class handles all the interactions with the users table in the database
 */
public class UserDOA
{
    User user;
    public UserDOA()
    {
    }

    /**
     * This function inserts the user into the database
     * @param user object containing user data
     * @return true of false for the success of operation
     */
    public boolean insert(User user)
    {
        boolean success = false;
        return success;
    }

    /**
     * This function deletes the requested user from the database
     * @param user object containing user data
     * @return true of false for the success of operation
     */
    public boolean delete(User user)
    {
        boolean success = false;
        return success;
    }


    /**
     * This function finds the user based on their ID
     * @param userID ID for requested user
     * @return object containing the users data
     */
    public User find(String userID)
    {
        return user;
    }




}
