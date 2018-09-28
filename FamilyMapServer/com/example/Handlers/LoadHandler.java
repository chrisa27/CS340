package com.example.Handlers;

import com.example.Communicators.RequestHolders.LoadRequest;
import com.example.Communicators.ResultHolders.LoadResult;
import com.example.ServerModel.Person;
import com.example.Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by video on 2/13/2017.
 */

public class LoadHandler implements HttpHandler{
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        LoadRequest loadRequest;
        LoadResult loadResult;
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());
        loadRequest = gson.fromJson(inputStreamReader, LoadRequest.class);
        inputStreamReader.close();

        loadResult = LoadService.SINGLETON.load(loadRequest);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody());
        gson.toJson(loadResult, outputStreamWriter);
        outputStreamWriter.close();
    }
}
