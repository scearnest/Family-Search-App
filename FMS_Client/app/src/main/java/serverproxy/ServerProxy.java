package serverproxy;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import models.*;
import requests.*;
import results.*;


public class ServerProxy {

    private String serverHost;
    private String serverPort;

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }



    public RegisterResult register(RegisterRequest request)
    {
        RegisterResult result = null;

        try {

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            Gson gson = new Gson();
            OutputStream resData = http.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(resData);

            gson.toJson(request, writer);
            writer.close();
            resData.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("Register successfully sent");

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                result = gson.fromJson(respData, RegisterResult.class);
            }
            else
            {
                System.out.println("ERROR: " + http.getResponseMessage());
            }

            if(result.getToken() != null)
            {
                Model.getInstance().setUserPersonID(result.getPersonID());

                PersonsRequest personsRequest = new PersonsRequest();
                personsRequest.setAuthToken(result.getToken());
                EventsRequest eventsRequest = new EventsRequest();
                eventsRequest.setAuthToken(result.getToken());
                persons(personsRequest);
                events(eventsRequest);
            }

            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public LoginResult login(LoginRequest request)
    {
        LoginResult result = null;

        try {

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            Gson gson = new Gson();
            OutputStream resData = http.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(resData);

            gson.toJson(request, writer);
            writer.close();
            resData.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("Login successfully sent");

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                result = gson.fromJson(respData, LoginResult.class);
            }
            else
            {
                System.out.println("ERROR: " + http.getResponseMessage());
            }

            if(result.getAuthToken() != null)
            {
                Model.getInstance().setUserPersonID(result.getPersonID());
                Model.getInstance().setAuthToken(result.getAuthToken());
                PersonsRequest personsRequest = new PersonsRequest();
                personsRequest.setAuthToken(result.getAuthToken());

                EventsRequest eventsRequest = new EventsRequest();
                eventsRequest.setAuthToken(result.getAuthToken());

                PersonRequest personRequest = new PersonRequest();
                personRequest.setAuthToken(result.getAuthToken());
                personRequest.setPersonID(result.getPersonID());

                PersonResult personResult = person(personRequest);

                persons(personsRequest);
                events(eventsRequest);
            }

            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public PersonResult person(PersonRequest request)
    {
        PersonResult result = null;

        try {

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/" + request.getPersonID());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", request.getAuthToken());
            http.connect();

            Gson gson = new Gson();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                result = gson.fromJson(respData, PersonResult.class);
            }
            else
            {
                System.out.println("ERROR: " + http.getResponseMessage());
            }

            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }


    public PersonsResult persons(PersonsRequest request)
    {
        PersonsResult result = null;

        try {

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", request.getAuthToken());
            http.connect();

            Gson gson = new Gson();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("Grabbed people successfully");

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                result = gson.fromJson(respData, PersonsResult.class);
            }
            else
            {
                System.out.println("ERROR: " + http.getResponseMessage());
                result = new PersonsResult();
                result.setMessage("Could not connect");
            }

            if(result.getMessage() == null) {
                Model.getInstance().setPersons(result.getPersons());
            }

            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = new PersonsResult();
            result.setMessage("Could not connect");
        }

        return result;
    }

    public EventsResult events(EventsRequest request)
    {
        EventsResult result = null;

        try {

            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", request.getAuthToken());
            http.connect();

            Gson gson = new Gson();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("Grabbed events successfully");

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                result = gson.fromJson(respData, EventsResult.class);
            }
            else
            {
                System.out.println("ERROR: " + http.getResponseMessage());
                result = new EventsResult();
                result.setMessage("Could not connect");
            }

            if(result.getMessage() == null) {
                Model.getInstance().setEvents(result.getEvents());
            }

            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = new EventsResult();
            result.setMessage("Could not connect");
        }

        return result;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}