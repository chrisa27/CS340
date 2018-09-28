package com.example.Handlers;

import com.example.Communicators.ResultHolders.ClearResult;
import com.example.Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by video on 2/13/2017.
 */

public class ClearHandler implements HttpHandler {
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ClearResult response = ClearService.SINGLETON.clearService();

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
        gson.toJson(response, outputStreamWriter);
        outputStreamWriter.close();
    }
}
