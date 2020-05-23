package services;
import requests.LoginRequest;
import results.LoginResult;
import DAOs.*;
import models.*;
import java.util.UUID;

/**
 * Manages the request and DOA process for the login command. Returns the request
 */
public class LoginService
{
    private LoginRequest request;
    private UserDAO doa;
    private LoginResult result = new LoginResult();

    public LoginService(LoginRequest request)
    {
        this.request = request;
    }

    /**
     * Logs in the user and returns the result
     * @return result: This is the return object for the command
     */
    public LoginResult loginUser()
    {
        User user = null;
        user = validateUser(request.getUserName(),request.getPassword(),user);

        if(user == null)
        {
            result.setErrorMessage("Incorrect username or password");
            return result;
        }

        String tokenID = UUID.randomUUID().toString();
        Token token = new Token(tokenID, user.getUsername());

        addToken(token);

        if(token == null)
        {
            result.setErrorMessage("Could not find token");
            return result;
        }

        result.setAuthToken(token.getToken());
        result.setUserName(user.getUsername());
        result.setPersonID(user.getPersonID());

        return result;
    }

    private User validateUser(String userName, String password, User user)
    {
        Database db = new Database();
        try
        {
            UserDAO dao = new UserDAO(db.openConnection());
            user = dao.login(userName,password);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        return user;
    }

    private void addToken(Token token)
    {
        Database db = new Database();
        try
        {
            TokenDAO dao = new TokenDAO(db.openConnection());
            dao.insert(token);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }
    }
}
