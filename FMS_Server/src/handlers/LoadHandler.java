package handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoadRequest;
import results.LoadResult;
import services.LoadService;
import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                LoadRequest request;
                LoadResult result;
                Gson gson = new Gson();

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                request = gson.fromJson(reqData,LoadRequest.class);

                LoadService service = new LoadService(request);
                result = service.load();

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

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}


