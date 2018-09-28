package com.example.Handlers;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Services.RegisterService;
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

public class RegisterHandler implements HttpHandler{
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());

        RegisterRequest request = gson.fromJson(inputStreamReader, RegisterRequest.class);
        inputStreamReader.close();

        RegisterResponse response = RegisterService.SINGLETON.register(request);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
        gson.toJson(response, outputStreamWriter);
        outputStreamWriter.close();
    }
}
