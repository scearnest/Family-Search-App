package DAOs;
import models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * This class handles all the interactions with the events table in the database
 */
public class EventDAO
{
    Connection conn;

    public EventDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * This function inserts the Event into the database
     * @param event object containing Event data
     * @return true of false for the success of operation
     */
    public boolean insert(Event event) throws DataAccessException
    {
        boolean commit = true;

        String sql = "INSERT INTO Events (EventID, Descendant, PersonID, Latitude, Longitude," +
                      "Country, City, EventType, Year) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,event.getEventID());
            stmt.setString(2,event.getDescendant());
            stmt.setString(3,event.getPersonID());
            stmt.setDouble( 4,event.getLatitude());
            stmt.setDouble(5,event.getLongitude());
            stmt.setString(6,event.getCountry());
            stmt.setString(7,event.getCity());
            stmt.setString(8,event.getEventType());
            stmt.setInt(   9,event.getYear());


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
     * This function deletes the requested Event from the database
     * @param eventID String ID of object to delete
     * @return true of false for the success of operation
     */
    public boolean delete(String eventID) throws DataAccessException
    {
        boolean success = true;

        String sql = "DELETE FROM Events WHERE EventID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,eventID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            success = false;
            throw new DataAccessException("Error encountered while deleting from the database");
        }

        return success;
    }

    //Deletes all events associated with a specific descendant
    public boolean deleteAll(String descendant) throws DataAccessException
    {
        boolean success = true;

        String sql = "DELETE FROM Events WHERE Descendant = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,descendant);
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
     * This function finds the Event based on their ID
     * @param eventID ID for requested Event
     * @return object containing the Events data
     */
    public Event find(String eventID) throws DataAccessException
    {
        Event event = null;
        ResultSet rs = null;

        String sql = ("SELECT * " + "\n" + "FROM Events" + "\n" + "WHERE eventID = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                event = new Event(rs.getString("EventID"), rs.getString("Descendant"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"),
                        rs.getFloat("Longitude"), rs.getString("Country"),
                        rs.getString("City"),rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return event;
    }

    //Finds all events associated with a specific descendant and returns them in an arraylist
    public ArrayList<Event> findAll(String descendant) throws DataAccessException
    {
        Event event = null;
        ResultSet rs = null;
        ArrayList<Event> list = new ArrayList<Event>();

        String sql = ("SELECT * " + "\n" + "FROM Events" + "\n" + "WHERE Descendant = ?");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descendant);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                event = new Event(rs.getString("EventID"), rs.getString("Descendant"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"),
                        rs.getFloat("Longitude"), rs.getString("Country"),
                        rs.getString("City"),rs.getString("EventType"),
                        rs.getInt("Year"));
                list.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }

        return list;
    }
}



