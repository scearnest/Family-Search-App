package handlers;
import java.io.*;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                String path = exchange.getRequestURI().toString();
                String fullPath = "web";
                if(path.equals("/"))
                {
                    path = "/index.html";
                }

                path = fullPath + path;
                File file = new File(path);

                if(file.exists())
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    path =  "web/HTML/404.html";
                }

                Path filePath = FileSystems.getDefault().getPath(path);
                Files.copy(filePath, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
