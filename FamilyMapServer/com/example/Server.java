package com.example;

import com.example.Communicators.ClientCommunicator;
import com.example.Communicators.ProxyServer;
import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ServerCommunicator;
import com.example.Database.SetUp;

import java.sql.Connection;
import java.sql.SQLException;

public class Server {
    public static Connection connection;

    public static void initializeDatabase(){
        SetUp setup = new SetUp();
        try{
            connection = setup.saveInitialize("jdbc:sqlite:familymap.db");
        }catch(Exception e){
            System.err.print("Couldn't initialize the database");
        }
    }

    public static void clearInitialize(){
        SetUp setup = new SetUp();
        try{
            connection = setup.initialize("jdbc:sqlite:familymap.db");
        }catch(Exception e){
            System.err.print("Couldn't initialize the database");
        }
    }

    public static void close(){
        try {
            connection.close();
        }catch (SQLException e){
            System.err.println("Couldn't close the connection");
        }
    }

    public static void closeServer(){
        ServerCommunicator.SINGLETON.closeServer();
    }

    public static void main(String[] args) {
        initializeDatabase();
        if (args == null){
            ServerCommunicator.SINGLETON.run("8080");
        }
        else if (args.length == 1) {
            ServerCommunicator.SINGLETON.run(args[0]);
        }
        else {
            ServerCommunicator.SINGLETON.run(args[1]);
        }
    }
}
