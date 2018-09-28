package com.example.Handlers;

import com.example.Communicators.RequestHolders.LoginRequest;
import com.example.Communicators.ResultHolders.LoginResult;
import com.example.Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by video on 2/13/2017.
 */

public class LoginHandler implements HttpHandler{
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());

        LoginRequest request = gson.fromJson(inputStreamReader, LoginRequest.class);
        inputStreamReader.close();

        LoginResult response = LoginService.SINGLETON.login(request);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
        gson.toJson(response, outputStreamWriter);
        outputStreamWriter.close();
    }
}
