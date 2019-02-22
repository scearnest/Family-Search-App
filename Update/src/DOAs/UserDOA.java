package DOAs;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all the interactions with the users table in the database
 */
public class UserDOA
{
    Connection conn;

    public UserDOA(Connection conn)
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
                "Last_Name, Gender, userID) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getFirstName());
            stmt.setString(5,user.getLastName());
            stmt.setString(6,user.getGender());
            stmt.setString(7,user.getuserID());


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
     * @param userID object containing user's ID
     * @return true of false for the success of operation
     */
    public boolean delete(String userID) throws DataAccessException
    {
        boolean success = true;

        String sql = "DELETE FROM Users WHERE userID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userID);
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
     * @param userID ID for requested user
     * @return object containing the users data
     */
    public User find(String userID) throws DataAccessException
    {
        User user = null;
        ResultSet rs = null;

        String sql = ("SELECT * " + "\n" + "FROM Users" + "\n" + "WHERE userID = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("First_Name"),
                        rs.getString("Last_Name"), rs.getString("Gender"),
                        rs.getString("userID"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return user;
    }
}
