package handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoginRequest;
import results.LoginResult;
import services.LoginService;
import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                LoginRequest request;
                LoginResult result;
                Gson gson = new Gson();

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                request = gson.fromJson(reqData,LoginRequest.class);

                LoginService service = new LoginService(request);
                result = service.loginUser();

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
