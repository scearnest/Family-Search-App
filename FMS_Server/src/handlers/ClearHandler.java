package handlers;
import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import results.ClearResult;
import services.ClearService;

public class ClearHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {

                ClearService service = new ClearService();
                ClearResult result;
                result = service.clear();

                Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();

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
