package com.example.video.familymapclient.Communication;

import android.util.Log;

import com.example.video.familymapclient.Communication.RequestHolders.LoadRequest;
import com.example.video.familymapclient.Communication.RequestHolders.LoginRequest;
import com.example.video.familymapclient.Communication.RequestHolders.RegisterRequest;
import com.example.video.familymapclient.Communication.ResultHolders.ClearResult;
import com.example.video.familymapclient.Communication.ResultHolders.EventResponse;
import com.example.video.familymapclient.Communication.ResultHolders.FillResult;
import com.example.video.familymapclient.Communication.ResultHolders.LoadResult;
import com.example.video.familymapclient.Communication.ResultHolders.LoginResult;
import com.example.video.familymapclient.Communication.ResultHolders.PersonResponse;
import com.example.video.familymapclient.Communication.ResultHolders.RegisterResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientCommunicator {
    private static final String TAG = "cc";

    private static final String REGISTER_DESIGNATOR = "/user/register";
    private static final String LOGIN_DESIGNATOR = "/user/login";
    private static final String CLEAR_DESIGNATOR = "/clear";
    private static final String FILL_DESIGNATOR = "/fill";
    private static final String LOAD_DESIGNATOR = "/load";
    private static final String PERSON_DESIGNATOR = "/person";
    private static final String EVENT_DESIGNATOR = "/event";

    public static ClientCommunicator SINGLETON = new ClientCommunicator();
    private static Gson gson = new Gson();
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static String URL_PREFIX;
    public static final String AUTHORIZATION = "authorization";

    private ClientCommunicator(){};

    public RegisterResponse register(RegisterRequest request){
        HttpURLConnection httpURLConnection = openConnection(REGISTER_DESIGNATOR, null, HTTP_POST);
        if (httpURLConnection != null) {
            sendToServerCommunicator(httpURLConnection, request);
            return (RegisterResponse) getResponse(httpURLConnection, RegisterResponse.class);
        }
        else {
            return null;
        }
    }

    public LoginResult login(LoginRequest request){
        HttpURLConnection httpURLConnection = openConnection(LOGIN_DESIGNATOR, null, HTTP_POST);
        if (httpURLConnection != null) {
            sendToServerCommunicator(httpURLConnection, request);
            return (LoginResult) getResponse(httpURLConnection, LoginResult.class);
        }
        else{
            return null;
        }
    }

    public ClearResult clear(){
        HttpURLConnection httpURLConnection = openConnection(CLEAR_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, null);
        return (ClearResult)getResponse(httpURLConnection, ClearResult.class);
    }

    public FillResult fill(String username, int generations){
        HttpURLConnection httpURLConnection;
        if (generations != -1) {
            httpURLConnection = openConnection(FILL_DESIGNATOR + "/" + username + "/" + generations, null, HTTP_POST);
        }
        else{
            httpURLConnection = openConnection(FILL_DESIGNATOR + "/" + username, null, HTTP_POST);
        }
        sendToServerCommunicator(httpURLConnection, null);
        return (FillResult)getResponse(httpURLConnection, FillResult.class);
    }

    public LoadResult load(LoadRequest request){
        HttpURLConnection httpURLConnection = openConnection(LOAD_DESIGNATOR, null, HTTP_POST);
        sendToServerCommunicator(httpURLConnection, request);
        return (LoadResult)getResponse(httpURLConnection, LoadResult.class);
    }

    //for person and event, we need to sent the auth token.
    public PersonResponse person(String id, String token){
        HttpURLConnection httpURLConnection;
        if(id != null) {
            httpURLConnection = openConnection(PERSON_DESIGNATOR + "/" + id, token, HTTP_GET);
        }
        else {
            httpURLConnection = openConnection(PERSON_DESIGNATOR, token, HTTP_GET);
        }
        sendToServerCommunicator(httpURLConnection, null);
        return (PersonResponse)getResponse(httpURLConnection, PersonResponse.class);
    }

    public EventResponse event(String id, String token){
        HttpURLConnection httpURLConnection;
        if (id != null) {
            httpURLConnection = openConnection(EVENT_DESIGNATOR + "/" + id, token, HTTP_GET);
        }
        else{
            httpURLConnection = openConnection(EVENT_DESIGNATOR, token, HTTP_GET);
        }
        sendToServerCommunicator(httpURLConnection, null);
        return (EventResponse)getResponse(httpURLConnection, EventResponse.class);
    }

    private HttpURLConnection openConnection(String contextIdentifier, String authToken, String method){
        HttpURLConnection result = null;

        try{
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setConnectTimeout(5000);
            result.setRequestMethod(method);
            result.setDoOutput(true);
            result.setRequestProperty(AUTHORIZATION, authToken);
            result.connect();
        }catch(MalformedURLException e){
            System.err.println("The URL was bad");
        }catch(IOException e){
            Log.d(TAG, e.toString());
            e.printStackTrace();
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

    public void setLocalHost(String hostName, String portNumber){
        URL_PREFIX = "http://" + hostName + ":" + portNumber;
    }
}
