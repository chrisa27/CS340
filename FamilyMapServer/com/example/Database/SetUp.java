package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetUp{
    public static Connection initialize(String name) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(name);
        initializeUsers(connection);
        initializePerson(connection);
        initializeEvent(connection);
        initializeTokens(connection);
        return connection;
    }

    public static Connection saveInitialize(String name) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(name);
        return connection;

    }

    private static void initializeUsers(Connection connection){
       try {
           Statement statement = connection.createStatement();
           statement.executeUpdate("DROP TABLE if exists Users;");
           statement.executeUpdate("CREATE TABLE Users(" +
                   "username TEXT PRIMARY KEY," +
                   "password TEXT NOT NULL," +
                   "firstname TEXT NOT NULL," +
                   "lastname TEXT NOT NULL," +
                   "email TEXT NOT NULL," +
                   "gender TEXT NOT NULL);");
       }catch (SQLException e){
           System.err.println("Couldn't create Users table");
       }
    }

    private static void initializePerson(Connection connection){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if exists Persons;");
            statement.executeUpdate("CREATE TABLE Persons(" +
                   "personid TEXT PRIMARY KEY," +
                   "descendant TEXT REFERENCES Users(username)," +
                   "first TEXT," +
                   "last TEXT," +
                   "gender TEXT," +
                   "mother TEXT REFERENCES Person(personid)," +
                   "father TEXT REFERENCES Person(personid)," +
                   "spouse TEXT REFERENCES Person(personid));");
            statement.executeUpdate("ALTER TABLE Users ADD personid TEXT REFERENCES Person(personid);");
        }catch(Exception e){
            System.err.println("Couldn't create Persons table");
        }
    }

    private static void initializeEvent(Connection connection){
        try {
           Statement statement = connection.createStatement();
           statement.executeUpdate("DROP TABLE if exists Events;");
           statement.executeUpdate("CREATE TABLE Events(" +
                   "eventid TEXT PRIMARY KEY NOT NULL," +
                   "descendant TEXT REFERENCES Person(descendant)," +
                   "personid TEXT REFERENCES Person(personid)," +
                   "latitude REAL," +
                   "longitude REAL," +
                   "country TEXT," +
                   "city TEXT," +
                   "type TEXT," +
                   "year INTEGER);");
        }catch(Exception e){
            System.err.println("Couldn't create Event table");
        }
    }

    private static void initializeTokens(Connection connection){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if exists Tokens");
            statement.executeUpdate("CREATE TABLE Tokens(" +
                    "token TEXT PRIMARY KEY," +
                    "username TEXT REFERENCES Users(userid));");
        }catch(SQLException e){
            System.err.println("Couldn't create Tokens table");
        }
    }
}