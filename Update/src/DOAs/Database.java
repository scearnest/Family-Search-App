package DOAs;

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
        String sqlUser = "CREATE TABLE IF NOT EXISTS Users " +
                            "(" +
                            "Username text not null, " +
                            "Password text not null, " +
                            "Email text not null, " +
                            "First_Name text not null, " +
                            "Last_Name text not null, " +
                            "Gender text not null, " +
                            "userID text not null unique, " +
                            "primary key (userID)" +
                            ")";
        createTable(sqlUser);

        String sqlPeople = "CREATE TABLE IF NOT EXISTS People " +
                            "(" +
                            "personID text not null unique, " +
                            "Descendant text not null, " +
                            "First_Name text not null, " +
                            "Last_Name text not null, " +
                            "Gender text not null, " +
                            "Father text not null, " +
                            "Mother text not null, " +
                            "Spouse text not null," +
                            "primary key (personID)" +
                            ")";
        createTable(sqlPeople);
    }

    public void createTable(String sql) throws DataAccessException
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
        String people = "People";
        clearTable(people);
    }

    public void clearTable(String table) throws DataAccessException
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
