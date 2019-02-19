package DOAs;
import models.Token;

/**
 * This class handles all the interactions with the tokens table in the database
 */
public class TokenDOA
{
    Token token;

    public TokenDOA()
    {
    }

    /**
     * This function inserts the Token into the database
     * @param token object containing Token data
     * @return true of false for the success of operation
     */
    public boolean insert(Token token)
    {
        boolean success = false;
        return success;
    }

    /**
     * This function deletes the requested Token from the database
     * @param token object containing Token data
     * @return true of false for the success of operation
     */
    public boolean delete(Token token)
    {
        boolean success = false;
        return success;
    }


    /**
     * This function finds the Token based on their ID
     * @param tokenID ID for requested Token
     * @return object containing the Tokens data
     */
    public Token find(String tokenID)
    {
        return token;
    }
}
