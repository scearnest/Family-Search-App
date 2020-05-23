package DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
    private Connection conn;

    static
    {
        try
        {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws DataAccessException
    {
        try
        {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public void closeConnection(boolean commit) throws DataAccessException
    {
        try
        {
            if (commit)
            {
                //This will commit the changes to the database
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException
    {
        String sqlUser =    "CREATE TABLE IF NOT EXISTS Users " +
                            "(" +
                            "Username text not null unique, " +
                            "Password text not null, " +
                            "Email text not null, " +
                            "First_Name text not null, " +
                            "Last_Name text not null, " +
                            "Gender text not null, " +
                            "PersonID text not null unique, " +
                            "primary key (Username)" +
                            "foreign key (PersonID) references Persons(PersonID)" +
                            ")";
        createTable(sqlUser);

        String sqlPersons = "CREATE TABLE IF NOT EXISTS Persons " +
                            "(" +
                            "PersonID text not null unique, " +
                            "Descendant text not null, " +
                            "First_Name text not null, " +
                            "Last_Name text not null, " +
                            "Gender text not null, " +
                            "Father text, " +
                            "Mother text, " +
                            "Spouse text," +
                            "primary key (PersonID)" +
                            ")";
        createTable(sqlPersons);

        String sqlEvents = "CREATE TABLE IF NOT EXISTS Events " +
                            "(" +
                            "EventID text not null unique, " +
                            "Descendant text not null, " +
                            "PersonID text not null, " +
                            "Latitude float not null, " +
                            "Longitude float not null, " +
                            "Country text not null, " +
                            "City text not null, " +
                            "EventType text not null, " +
                            "Year int not null, " +
                            "primary key (EventID), " +
                            "foreign key (Descendant) references Users(Username), " +
                            "foreign key (PersonID) references Persons(PersonID)" +
                            ")";
        createTable(sqlEvents);

        String sqlUsers = "CREATE TABLE IF NOT EXISTS Tokens " +
                            "(" +
                            "Token text not null unique, " +
                            "Username text not null, " +
                            "primary key (Token), " +
                            "foreign key (Username) references Users(Username)" +
                            ")";
        createTable(sqlUsers);
    }

    private void createTable(String sql) throws DataAccessException
    {
        openConnection();

        try (Statement stmt = conn.createStatement())
        {
            stmt.executeUpdate(sql);

            closeConnection(true);
        }
        catch (DataAccessException e)
        {
            closeConnection(false);
            throw e;
        }
        catch (SQLException e)
        {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while creating tables");
        }
    }


    public void clearTables() throws DataAccessException
    {
        String users = "Users";
        clearTable(users);
        String people = "Persons";
        clearTable(people);
        String events = "Events";
        clearTable(events);
        String tokens = "Tokens";
        clearTable(tokens);
    }

    private void clearTable(String table) throws DataAccessException
    {
        openConnection();

        try (Statement stmt = conn.createStatement())
        {
            String sql = "DELETE FROM " + table;
            stmt.executeUpdate(sql);
            closeConnection(true);
        }
        catch (DataAccessException e)
        {
            closeConnection(false);
            throw e;
        }
        catch (SQLException e)
        {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

}
