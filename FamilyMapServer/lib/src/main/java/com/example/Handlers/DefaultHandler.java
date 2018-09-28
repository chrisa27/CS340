package com.example.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by video on 3/1/2017.
 */

public class DefaultHandler implements HttpHandler{
    private static Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        String uri = httpExchange.getRequestURI().toString();
        String[] parts = uri.split("/");

        if (parts.length == 0){
            byte[] bytes;
            Path path = Paths.get("lib/src/main/java/libs/HTML/index.html");
            bytes = Files.readAllBytes(path);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(bytes);
            outputStream.close();
        }
        else {
            byte[] bytes;
            Path path = Paths.get("lib/src/main/java/libs/HTML/404.html");
            bytes = Files.readAllBytes(path);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(bytes);
            outputStream.close();
        }
    }
}
