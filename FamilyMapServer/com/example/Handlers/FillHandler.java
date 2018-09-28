package com.example.Handlers;

import com.example.Communicators.ResultHolders.FillResult;
import com.example.Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by video on 2/13/2017.
 */

public class FillHandler implements HttpHandler{
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String uri = httpExchange.getRequestURI().toString();
        String[] parts = uri.split("/");

        FillResult fillResult;
        if (parts[2].equals("null")){
            parts[2] = null;
        }

        if (parts.length == 4){
            fillResult = FillService.SINGLETON.fill(parts[2], Integer.parseInt(parts[3]));

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
            gson.toJson(fillResult, outputStreamWriter);
            outputStreamWriter.close();
        }
        else if(parts.length == 3){
            fillResult = FillService.SINGLETON.fill(parts[2], 4);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
            gson.toJson(fillResult, outputStreamWriter);
            outputStreamWriter.close();
        }
        else{
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
        }
    }
}
