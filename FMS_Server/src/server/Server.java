package server;
import java.io.*;
import java.net.*;
import DAOs.DataAccessException;
import DAOs.Database;
import handlers.*;
import com.sun.net.httpserver.*;


public class Server
{
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run (String portNnumber)
    {
        System.out.println("Initializing HTTP Server");
        try
        {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNnumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/", new FileHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/person" , new PersonHandler());
        server.createContext("/fill", new FillHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args)
    {
        Database db = new Database();
        try
        {
            db.createTables();
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
        }

        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
