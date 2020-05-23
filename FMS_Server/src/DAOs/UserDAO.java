package DAOs;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all the interactions with the users table in the database
 */
public class UserDAO
{
    Connection conn;

    public UserDAO(Connection conn)
    {
        this.conn = conn;
    }


    /**
     * This function inserts the user into the database
     * @param user object containing user data
     * @return true of false for the success of operation
     */
    public boolean insert(User user) throws DataAccessException
    {
        boolean commit = true;

        String sql = "INSERT INTO Users (Username, Password, Email, First_Name, " +
                "Last_Name, Gender, PersonID) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getFirstName());
            stmt.setString(5,user.getLastName());
            stmt.setString(6,user.getGender());
            stmt.setString(7,user.getPersonID());


            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        return commit;
    }

    /**
     * This function deletes the requested user from the database
     * @param userName object containing user's ID
     * @return true of false for the success of operation
     */
    public boolean delete(String userName) throws DataAccessException
    {
        boolean success = true;

        String sql = "DELETE FROM Users WHERE Username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            success = false;
            throw new DataAccessException("Error encountered while deleting from the database");
        }

        return success;
    }


    /**
     * This function finds the user based on their ID
     * @param userName ID for requested user
     * @return object containing the users data
     */
    public User find(String userName) throws DataAccessException
    {
        User user = null;
        ResultSet rs = null;

        String sql = ("SELECT * " + "\n" + "FROM Users" + "\n" + "WHERE Username = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next() == true)
            {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("First_Name"),
                        rs.getString("Last_Name"), rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return user;
    }


    //Compares a userName and Password. If they dont match returns a null user
    public User login(String userName, String password) throws DataAccessException
    {
        User user = null;
        ResultSet rs;

        String sql = ("SELECT * " + "\n" + "FROM Users" + "\n" + "WHERE Username= ? AND Password = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("First_Name"),
                        rs.getString("Last_Name"), rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return user;
    }

}
