package com.example.Handlers;

import com.example.Communicators.ClientCommunicator;
import com.example.Communicators.ResultHolders.EventResponse;
import com.example.Services.EventIDService;
import com.example.Services.EventService;
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

public class EventHandler implements HttpHandler{
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        Headers headers = httpExchange.getRequestHeaders();
        String token = headers.getFirst(ClientCommunicator.AUTHORIZATION);

        String uri = httpExchange.getRequestURI().toString();
        String[] parts = uri.split("/");
        EventResponse eventResponse;

        if (parts.length == 3){
            eventResponse = EventIDService.SINGLETON.eventID(token, parts[2]);
        }
        else if (parts.length == 2){
            eventResponse = EventService.SINGLETON.events(token);
        }
        else {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return;
        }

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
        gson.toJson(eventResponse, outputStreamWriter);
        outputStreamWriter.close();
    }
}
