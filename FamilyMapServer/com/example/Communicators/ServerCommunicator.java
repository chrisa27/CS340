package com.example.Communicators;

import com.example.Handlers.ClearHandler;
import com.example.Handlers.DefaultHandler;
import com.example.Handlers.EventHandler;
import com.example.Handlers.FillHandler;
import com.example.Handlers.LoadHandler;
import com.example.Handlers.LoginHandler;
import com.example.Handlers.PersonHandler;
import com.example.Handlers.RegisterHandler;
import com.google.gson.Gson;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.sql.Connection;

public class ServerCommunicator {
    public static ServerCommunicator SINGLETON = new ServerCommunicator();
    private static Gson gson = new Gson();
    public static int SERVER_PORT_NUMBER;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    public static final String REGISTER_DESIGNATOR = "/user/register";
    public static final String LOGIN_DESIGNATOR = "/user/login";
    public static final String CLEAR_DESIGNATOR = "/clear";
    public static final String FILL_DESIGNATOR = "/fill";
    public static final String LOAD_DESIGNATOR = "/load";
    public static final String PERSON_DESIGNATOR = "/person";
    public static final String EVENT_DESIGNATOR = "/event";
    public static final String DEFAULT_DESIGNATOR = "/";

    private HttpHandler registerHandler = new RegisterHandler();
    private HttpHandler loginHandler = new LoginHandler();
    private HttpHandler clearHandler = new ClearHandler();
    private HttpHandler fillHandler = new FillHandler();
    private HttpHandler loadHandler = new LoadHandler();
    private HttpHandler personHandler = new PersonHandler();
    private HttpHandler eventHandler = new EventHandler();
    private HttpHandler defaultHandler = new DefaultHandler();

    private HttpServer server;

    private ServerCommunicator(){};

    public void run(String port){
        SERVER_PORT_NUMBER = Integer.parseInt(port);
        setUpServer(SERVER_PORT_NUMBER, MAX_WAITING_CONNECTIONS);
        setUpContext();
        server.start();
    }

    private void setUpServer(int portNumber, int maxConnections){
        try {
            server = HttpServer.create(new InetSocketAddress(portNumber), maxConnections);
        }catch(IOException e){
            System.err.println("Couldn't create the server");
        }

        server.setExecutor(null);
    }

    public void closeServer(){
        server.stop(0);
    }

    private void setUpContext(){
        server.createContext(REGISTER_DESIGNATOR, registerHandler);
        server.createContext(LOGIN_DESIGNATOR, loginHandler);
        server.createContext(CLEAR_DESIGNATOR, clearHandler);
        server.createContext(FILL_DESIGNATOR, fillHandler);
        server.createContext(LOAD_DESIGNATOR, loadHandler);
        server.createContext(PERSON_DESIGNATOR, personHandler);
        server.createContext(EVENT_DESIGNATOR, eventHandler);
        server.createContext(DEFAULT_DESIGNATOR, defaultHandler);
    }
}
