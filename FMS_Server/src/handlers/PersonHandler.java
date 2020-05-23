package handlers;
import com.google.gson.Gson;
import results.*;
import requests.*;
import services.*;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class PersonHandler implements HttpHandler
{
    int SLASH_LOCATION = 8;

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String path = exchange.getRequestURI().toString();
        String allPeople = "/person";

        if(path.equals(allPeople))
        {
            try
            {
                if(exchange.getRequestMethod().toLowerCase().equals("get"))
                {
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization"))
                    {
                        String authToken = reqHeaders.getFirst("Authorization");

                        PersonsRequest request = new PersonsRequest();
                        request.setAuthToken(authToken);

                        PersonsResult result;
                        Gson gson = new Gson();

                        PersonsService service = new PersonsService();

                        result = service.getPersons(request);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
                        gson.toJson(result, writer);

                        writer.close();
                    }
                }
            }
            catch (IOException e)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                exchange.getResponseBody().close();
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                if(exchange.getRequestMethod().toLowerCase().equals("get"))
                {
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization"))
                    {
                        String authToken = reqHeaders.getFirst("Authorization");
                        int strLength = path.length();
                        String personID = path.substring(SLASH_LOCATION,strLength);

                        PersonRequest request = new PersonRequest();
                        request.setPersonID(personID);
                        request.setAuthToken(authToken);

                        PersonResult result;
                        Gson gson = new Gson();

                        PersonService service = new PersonService();

                        result = service.getPerson(request);

                        Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                        gson.toJson(result, writer);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        writer.close();
                    }
                }
            }
            catch (IOException e)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                exchange.getResponseBody().close();
                e.printStackTrace();
            }
        }
    }
}
