package DAOs;
import models.Token;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all the interactions with the tokens table in the database
 */
public class TokenDAO
{
    Connection conn;

    public TokenDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * This function inserts the Token into the database
     * @param token object containing Token data
     * @return true of false for the success of operation
     */
    public boolean insert(Token token) throws DataAccessException
    {
        boolean commit = true;

        String sql = "INSERT INTO Tokens (Token, Username) VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,token.getToken());
            stmt.setString(2,token.getUsername());

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
     * This function deletes the requested Token from the database
     * @param token object containing Token data
     * @return true of false for the success of operation
     */
    public boolean delete(String token) throws DataAccessException
    {
        boolean success = true;

        String sql = "DELETE FROM Tokens WHERE Token = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, token);
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
     * This function finds the Token based on their ID
     * @param authToken for requested Token
     * @return object containing the Tokens data
     */
    public Token find(String authToken) throws DataAccessException
    {
        Token token = null;
        ResultSet rs = null;

        String sql = ("SELECT * " + "\n" + "FROM Tokens" + "\n" + "WHERE Token = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                token = new Token(rs.getString("Token"), rs.getString("Username"));
                return token;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return token;
    }
}
