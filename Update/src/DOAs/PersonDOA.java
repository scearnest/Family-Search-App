package DOAs;
import models.Person;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all the interactions with the persons table in the database
 */
public class PersonDOA
{
    Connection conn;

    public PersonDOA(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * This function inserts the Person into the database
     * @param person object containing Person data
     * @return true of false for the success of operation
     */
    public boolean insert(Person person) throws DataAccessException
    {
        boolean commit = true;

        String sql = "INSERT INTO People (personID, Descendant, First_Name, " +
                "Last_Name, Gender, Father, Mother, Spouse) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,person.getPersonID());
            stmt.setString(2,person.getDecendant());
            stmt.setString(3,person.getFirstName());
            stmt.setString(4,person.getLastName());
            stmt.setString(5,person.getGender());
            stmt.setString(6,person.getFather());
            stmt.setString(7,person.getMother());
            stmt.setString(8,person.getSpouse());
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
     * This function deletes the requested Person from the database
     * @param personID object containing Person data
     * @return true of false for the success of operation
     */
    public boolean delete(String personID) throws DataAccessException
    {
        boolean success = true;

        String sql = "DELETE FROM People WHERE personID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,personID);
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
     * This function finds the Person based on their ID
     * @param personID ID for requested Person
     * @return object containing the Persons data
     */
    public Person find(String personID) throws DataAccessException
    {
        Person person = null;
        ResultSet rs = null;

        String sql = ("SELECT * " + "\n" + "FROM People" + "\n" + "WHERE personID = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                person = new Person(rs.getString("personID"), rs.getString("Descendant"),
                        rs.getString("First_Name"), rs.getString("Last_Name"),
                        rs.getString("Gender"), rs.getString("Father"),
                        rs.getString("Mother"),rs.getString("Spouse"));
                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return person;
    }
}
