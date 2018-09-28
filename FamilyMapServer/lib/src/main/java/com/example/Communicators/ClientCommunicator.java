package com.example.Communicators;

import com.example.Communicators.RequestHolders.LoadRequest;
import com.example.Communicators.RequestHolders.LoginRequest;
import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.ClearResult;
import com.example.Communicators.ResultHolders.EventResponse;
import com.example.Communicators.ResultHolders.FillResult;
import com.example.Communicators.ResultHolders.LoadResult;
import com.example.Communicators.ResultHolders.LoginResult;
import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientCommunicator {
    public static ClientCommunicator SINGLETON = new ClientCommunicator();
    private static Gson gson = new Gson();
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static String HOST_NAME;
    private static String URL_PREFIX;
    public static final String AUTHORIZATION = "authorization";

    private ClientCommunicator(){};

    public RegisterResponse register(RegisterRequest request){
        HttpURLConnection httpURLConnection = openConnection(ServerCommunicator.REGISTER_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, request);
        return (RegisterResponse)getResponse(httpURLConnection, RegisterResponse.class);
    }

    public LoginResult login(LoginRequest request){
        HttpURLConnection httpURLConnection = openConnection(ServerCommunicator.LOGIN_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, request);
        return (LoginResult)getResponse(httpURLConnection, LoginResult.class);
    }

    public ClearResult clear(){
        HttpURLConnection httpURLConnection = openConnection(ServerCommunicator.CLEAR_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, null);
        return (ClearResult)getResponse(httpURLConnection, ClearResult.class);
    }

    public FillResult fill(String username, int generations){
        HttpURLConnection httpURLConnection;
        if (generations != -1) {
            httpURLConnection = openConnection(ServerCommunicator.FILL_DESIGNATOR + "/" + username + "/" + generations, null, HTTP_POST);
        }
        else{
            httpURLConnection = openConnection(ServerCommunicator.FILL_DESIGNATOR + "/" + username, null, HTTP_POST);
        }
        sendToServerCommunicator(httpURLConnection, null);
        return (FillResult)getResponse(httpURLConnection, FillResult.class);
    }

    public LoadResult load(LoadRequest request){
        HttpURLConnection httpURLConnection = openConnection(ServerCommunicator.LOAD_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, request);
        return (LoadResult)getResponse(httpURLConnection, LoadResult.class);
    }

    //for person and event, we need to sent the auth token.
    public PersonResponse person(String id, String token){
        HttpURLConnection httpURLConnection;
        if(id != null) {
            httpURLConnection = openConnection(ServerCommunicator.PERSON_DESIGNATOR + "/" + id, token, HTTP_GET);
        }
        else {
            httpURLConnection = openConnection(ServerCommunicator.PERSON_DESIGNATOR, token, HTTP_GET);
        }
        sendToServerCommunicator(httpURLConnection, null);
        return (PersonResponse)getResponse(httpURLConnection, PersonResponse.class);
    }

    public EventResponse event(String id, String token){
        HttpURLConnection httpURLConnection;
        if (id != null) {
            httpURLConnection = openConnection(ServerCommunicator.EVENT_DESIGNATOR + "/" + id, token, HTTP_GET);
        }
        else{
            httpURLConnection = openConnection(ServerCommunicator.EVENT_DESIGNATOR, token, HTTP_GET);
        }
        sendToServerCommunicator(httpURLConnection, null);
        return (EventResponse)getResponse(httpURLConnection, EventResponse.class);
    }

    /*
    public void defaultCall(){
        HttpURLConnection httpURLConnection = openConnection(ServerCommunicator.DEFAULT_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, null);
        getResponse(httpURLConnection, LoadResult.class);
    }*/

    private HttpURLConnection openConnection(String contextIdentifier, String authToken, String method){
        HttpURLConnection result = null;

        try{
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod(method);
            result.setDoOutput(true);
            result.setRequestProperty(AUTHORIZATION, authToken);
            result.connect();
        }catch(MalformedURLException e){
            System.err.println("The URL was bad");
        }catch(IOException e){
            System.err.println("There was an error casting the URL to a connection");
        }

        return result;
    }

    private void sendToServerCommunicator(HttpURLConnection httpURLConnection, Object value){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            gson.toJson(value, outputStreamWriter);
            outputStreamWriter.close();
        }catch(IOException e){
            System.err.println("Couldn't open the output stream");
        }
    }

    private Object getResponse(HttpURLConnection httpURLConnection, Class<?> klass){
        Object result = null;

        try{
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                if(httpURLConnection.getContentLength() == 0){
                    System.out.println("empty");
                }
                else{
                    InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                    result = gson.fromJson(inputStreamReader, klass);
                    inputStreamReader.close();
                }
            }
            else{
                throw new Exception(String.format("Error code %d", httpURLConnection.getResponseCode()));
            }
        }catch(IOException e){
            System.err.println("There was an error getting the response");
        }catch(Exception e){
            System.err.println("There was an HTTP error");
        }

        return result;
    }

    public void setLocalHost(String hostName){
        HOST_NAME = hostName;
        URL_PREFIX = "http://" + HOST_NAME + ":" + ServerCommunicator.SERVER_PORT_NUMBER;
    }
}
