package handlers;
import com.google.gson.Gson;
import results.*;
import requests.*;
import services.*;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

public class EventHandler implements HttpHandler
{
    int SLASH_LOCATION = 7;

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String path = exchange.getRequestURI().toString();
        String allEvents = "/event";

        if(path.equals(allEvents))
        {
            try
            {
                if(exchange.getRequestMethod().toLowerCase().equals("get"))
                {
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization"))
                    {
                        String authToken = reqHeaders.getFirst("Authorization");

                        EventsRequest request = new EventsRequest();
                        request.setAuthToken(authToken);

                        EventsResult result;
                        Gson gson = new Gson();

                        EventsService service = new EventsService();

                        result = service.getEvents(request);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
                        gson.toJson(result, writer);

                        writer.close();
                    }
                }
            }
            catch (Exception e)
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
                        String eventID = path.substring(SLASH_LOCATION,strLength);

                        EventRequest request = new EventRequest();
                        request.setEventID(eventID);
                        request.setAuthToken(authToken);

                        EventResult result;
                        Gson gson = new Gson();

                        EventService service = new EventService();

                        result = service.getEvent(request);

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
