package com.example.Handlers;

import com.example.Communicators.ClientCommunicator;
import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Services.PersonIDService;
import com.example.Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by video on 2/13/2017.
 */

public class PersonHandler implements HttpHandler {
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers header = httpExchange.getRequestHeaders();
        String token = header.getFirst(ClientCommunicator.AUTHORIZATION);

        String uri = httpExchange.getRequestURI().toString();
        String[] parts = uri.split("/");
        PersonResponse personResponse;

        if (parts.length == 3){
            personResponse = PersonIDService.SINGLETON.personID(token, parts[2]);
        }
        else if (parts.length == 2){
            personResponse = PersonService.SINGLETON.person(token);
        }
        else {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
            return;
        }

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
        gson.toJson(personResponse, outputStreamWriter);
        outputStreamWriter.close();
    }
}
