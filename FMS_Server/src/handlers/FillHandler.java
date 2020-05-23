package handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.FillRequest;
import results.FillResult;
import services.FillService;
import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler
{
    int DEFAULT_GEN = 4;
    int SLASH_LOCATION = 6;

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String path = exchange.getRequestURI().toString();
        int strLength = path.length();
        String requestData = path.substring(SLASH_LOCATION,strLength);

        if(requestData.contains("/"))
        {
            try
            {
                if(exchange.getRequestMethod().toLowerCase().equals("post"))
                {
                    int index = requestData.indexOf("/");
                    String userName = requestData.substring(0,(index));
                    int gen = Integer.parseInt(requestData.substring((index + 1), requestData.length()));

                    FillRequest request = new FillRequest();
                    request.setUsername(userName);
                    request.setGenerations(gen);

                    FillResult result;
                    Gson gson = new Gson();

                    FillService service = new FillService();
                    result = service.fill(request);

                    Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(result, writer);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writer.close();
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
                if(exchange.getRequestMethod().toLowerCase().equals("post"))
                {
                    String userName = requestData;

                    FillRequest request = new FillRequest();
                    request.setUsername(userName);
                    request.setGenerations(DEFAULT_GEN);

                    FillResult result;
                    Gson gson = new Gson();

                    FillService service = new FillService();
                    result = service.fill(request);

                    Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(result, writer);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writer.close();
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
