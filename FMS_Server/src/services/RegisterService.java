package services;
import DAOs.*;
import JsonData.Generator;
import models.*;
import java.util.*;
import requests.RegisterRequest;
import results.RegisterResult;
import java.sql.Connection;

/**
 * Manages the request and DOA process for the register command. Returns the request
 */
public class RegisterService
{
    private RegisterRequest request;
    private RegisterResult result = new RegisterResult();
    private boolean success = true;
    private String message;

    public RegisterService(RegisterRequest request)
    {
        this.request = request;
    }

    /**
     * Creates new user, generates 4 generations of ancestors, logs user in, and returns result
     * @return result: This is the result object for the command
     */
    public RegisterResult registerUser()
    {
        RegisterResult result = new RegisterResult();
        String ID = UUID.randomUUID().toString();
        String tokenID = UUID.randomUUID().toString();

        User user = new User(request.getUserName(), request.getPassword(), request.getEmail(),
                        request.getFirstName(), request.getLastName(), request.getGender(), ID);

        Person person = new Person(ID,user.getUsername(),user.getFirstName(),user.getLastName(),
                user.getGender(),null,null,null);

        Token token = new Token(tokenID,user.getUsername());

        try
        {
            addUser(user);
            addToken(token);
        }
        catch (DataAccessException e)
        {
            success = false;
            result.setErrorMessage(message);
            return result;
        }

        if(success)
        {
            result.setToken(tokenID);
            result.setUserName(user.getUsername());
            result.setPersonID(ID);
        }

        Generator generator = new Generator(5, user);
        generator.fillGenerations(person);

        return result;
    }


    private void addUser(User user) throws DataAccessException
    {
        Database db = new Database();

        try
        {
            Connection conn = db.openConnection();
            UserDAO dao = new UserDAO(conn);
            dao.insert(user);
            success = true;
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            message = "Could not add user";
            success = false;
            throw e;
        }
        finally
        {
            db.closeConnection(success);
        }
    }

    private void addPerson(Person person) throws DataAccessException
    {
        Database db = new Database();

        try
        {
            Connection conn = db.openConnection();
            PersonDAO dao = new PersonDAO(conn);
            dao.insert(person);
            success = true;
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            message = "Could not add person";
            success = false;
            throw e;
        }
        finally
        {
            db.closeConnection(success);
        }
    }

    private void addToken(Token token) throws DataAccessException
    {
        Database db = new Database();

        try
        {
            Connection conn = db.openConnection();
            TokenDAO dao = new TokenDAO(conn);
            dao.insert(token);
            success = true;
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            message = "Could not add token";
            success = false;
            throw e;
        }
        finally
        {
            db.closeConnection(success);
        }
    }
}  
