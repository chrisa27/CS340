package com.example.Test.DAOTest;

import com.example.Database.DataAccess.EventDAO;
import com.example.Server;
import com.example.ServerModel.Event;
import com.example.ServerModel.Location;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/1/2017.
 */

public class EventDAOTest {
    private static Connection connection;
    private static Event event1;
    private static Event event2;
    private static Event event3;
    private static Server server;



    @BeforeClass
    public static void initialize() throws Exception{
        server = new Server();
        server.initializeDatabase();
        Location location = new Location(55.55, 66.66, "Rockville", "USA");
        event1 = new Event("555", "555", location, "Marriage", 2017);
        location = new Location(33.45, 12.34, "Hong Kong", "China");
        event2 = new Event("555", "666", location, "Death", 1995);
        event3 = new Event("557", "666", location, "Death", 1995);
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:EventDummy.db");

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

    @Before
    public void reset(){
        try{
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("DELETE FROM Events");
            statement1.executeUpdate("INSERT INTO Events Values('" +
                    event1.getEventID() + "'," +
                    "'555'," +
                    "'555'," +
                    "55.55," +
                    "66.66," +
                    "'USA'," +
                    "'Rockville'," +
                    "'Marriage'," +
                    "2017);");
            statement1.executeUpdate("INSERT INTO Events Values('" +
                    event2.getEventID() + "'," +
                    "'555'," +
                    "'666'," +
                    "33.45," +
                    "12.34," +
                    "'China'," +
                    "'Hong Kong'," +
                    "'Death'," +
                    "1995);");

            Statement statement2 = EventDAO.SINGLETON.getConnection().createStatement();
            statement2.executeUpdate("DELETE FROM Events");
        }catch(SQLException e){
            System.err.println("Couldn't delete content");
        }
        EventDAO.SINGLETON.addEvent(event1);
        EventDAO.SINGLETON.addEvent(event2);
    }

    @Test
    public void addTest(){
        ResultSet rs1;
        ResultSet rs2;

        try {
            Statement statement1 = connection.createStatement();
            Statement statement2 = EventDAO.SINGLETON.getConnection().createStatement();
            rs1 = statement1.executeQuery("SELECT * FROM Events");
            rs2 = statement2.executeQuery("SELECT * FROM Events");

            rs1.next();
            rs2.next();

            String eventid = rs1.getString("eventid");
            String Eventid = rs2.getString("eventid");
            String descendant = rs1.getString("descendant");
            String Descendant = rs2.getString("descendant");
            String personid = rs1.getString("personid");
            String Personid = rs2.getString("personid");
            double latitude = rs1.getDouble("latitude");
            double Latitute = rs2.getDouble("latitude");
            double longitude = rs1.getDouble("longitude");
            double Longitude = rs2.getDouble("longitude");
            String country = rs1.getString("country");
            String Country = rs2.getString("country");
            String city = rs1.getString("city");
            String City = rs2.getString("city");
            String type = rs1.getString("type");
            String Type = rs2.getString("type");
            int year = rs1.getInt("year");
            int Year = rs2.getInt("year");

            assertEquals(eventid, Eventid);
            assertEquals(descendant, Descendant);
            assertEquals(personid, Personid);
            assertEquals(latitude, Latitute, 0);
            assertEquals(longitude, Longitude, 0);
            assertEquals(country, Country);
            assertEquals(city, City);
            assertEquals(type, Type);
            assertEquals(year, Year);

            rs1.next();
            rs2.next();

            eventid = rs1.getString("eventid");
            Eventid = rs2.getString("eventid");
            descendant = rs1.getString("descendant");
            Descendant = rs2.getString("descendant");
            personid = rs1.getString("personid");
            Personid = rs2.getString("personid");
            latitude = rs1.getDouble("latitude");
            Latitute = rs2.getDouble("latitude");
            longitude = rs1.getDouble("longitude");
            Longitude = rs2.getDouble("longitude");
            country = rs1.getString("country");
            Country = rs2.getString("country");
            city = rs1.getString("city");
            City = rs2.getString("city");
            type = rs1.getString("type");
            Type = rs2.getString("type");
            year = rs1.getInt("year");
            Year = rs2.getInt("year");

            assertEquals(eventid, Eventid);
            assertEquals(descendant, Descendant);
            assertEquals(personid, Personid);
            assertEquals(latitude, Latitute, 0);
            assertEquals(longitude, Longitude, 0);
            assertEquals(country, Country);
            assertEquals(city, City);
            assertEquals(type, Type);
            assertEquals(year, Year);
        }catch(SQLException e){
            System.err.println("Error");
            assertTrue(false);
        }
    }

    @Test
    public void getTest(){
        Event test = EventDAO.SINGLETON.getEvent(event1.getEventID());
        assertEquals(test.getEventID(), event1.getEventID());
        assertEquals(test.getDescendantID(), event1.getDescendantID());
        assertEquals(test.getPersonID(), event1.getPersonID());
        assertEquals(test.getLatitude(), event1.getLatitude(), 0);
        assertEquals(test.getLongitude(), event1.getLongitude(), 0);
        assertEquals(test.getCountry(), event1.getCountry());
        assertEquals(test.getCity(), event1.getCity());
        assertEquals(test.getEventType(), event1.getEventType());
        assertEquals(test.getYear(), event1.getYear());

        test = EventDAO.SINGLETON.getEvent(event2.getEventID());
        assertEquals(test.getEventID(), event2.getEventID());
        assertEquals(test.getDescendantID(), event2.getDescendantID());
        assertEquals(test.getPersonID(), event2.getPersonID());
        assertEquals(test.getLatitude(), event2.getLatitude(), 0);
        assertEquals(test.getLongitude(), event2.getLongitude(), 0);
        assertEquals(test.getCountry(), event2.getCountry());
        assertEquals(test.getCity(), event2.getCity());
        assertEquals(test.getEventType(), event2.getEventType());
        assertEquals(test.getYear(), event2.getYear());

        test = EventDAO.SINGLETON.getEvent("2");
        assertEquals(test, null);
    }

    @Test
    public void getIDTest(){
        Event test = EventDAO.SINGLETON.getIDEvent(event1.getEventID(), event1.getDescendantID());
        assertEquals(test.getEventID(), event1.getEventID());
        assertEquals(test.getDescendantID(), event1.getDescendantID());
        assertEquals(test.getPersonID(), event1.getPersonID());
        assertEquals(test.getLatitude(), event1.getLatitude(), 0);
        assertEquals(test.getLongitude(), event1.getLongitude(), 0);
        assertEquals(test.getCountry(), event1.getCountry());
        assertEquals(test.getCity(), event1.getCity());
        assertEquals(test.getEventType(), event1.getEventType());
        assertEquals(test.getYear(), event1.getYear());

        test = EventDAO.SINGLETON.getIDEvent(event2.getEventID(), event2.getDescendantID());
        assertEquals(test.getEventID(), event2.getEventID());
        assertEquals(test.getDescendantID(), event2.getDescendantID());
        assertEquals(test.getPersonID(), event2.getPersonID());
        assertEquals(test.getLatitude(), event2.getLatitude(), 0);
        assertEquals(test.getLongitude(), event2.getLongitude(), 0);
        assertEquals(test.getCountry(), event2.getCountry());
        assertEquals(test.getCity(), event2.getCity());
        assertEquals(test.getEventType(), event2.getEventType());
        assertEquals(test.getYear(), event2.getYear());

        assertEquals(EventDAO.SINGLETON.getIDEvent(event1.getEventID(), "0"), null);
        assertEquals(EventDAO.SINGLETON.getIDEvent("2", event1.getDescendantID()), null);
        assertEquals(EventDAO.SINGLETON.getIDEvent("2", "15"), null);
    }

    @Test
    public void getAllTest(){
        Set<Event> allEvents = EventDAO.SINGLETON.getAllEvents(event1.getDescendantID());
        Iterator<Event> it = allEvents.iterator();

        Event test = it.next();
        if (test.getEventID().equals(event1.getEventID())) {
            assertEquals(test.getEventID(), event1.getEventID());
            assertEquals(test.getDescendantID(), event1.getDescendantID());
            assertEquals(test.getPersonID(), event1.getPersonID());
            assertEquals(test.getLatitude(), event1.getLatitude(), 0);
            assertEquals(test.getLongitude(), event1.getLongitude(), 0);
            assertEquals(test.getCountry(), event1.getCountry());
            assertEquals(test.getCity(), event1.getCity());
            assertEquals(test.getEventType(), event1.getEventType());
            assertEquals(test.getYear(), event1.getYear());
        }
        else {
            assertEquals(test.getEventID(), event2.getEventID());
            assertEquals(test.getDescendantID(), event2.getDescendantID());
            assertEquals(test.getPersonID(), event2.getPersonID());
            assertEquals(test.getLatitude(), event2.getLatitude(), 0);
            assertEquals(test.getLongitude(), event2.getLongitude(), 0);
            assertEquals(test.getCountry(), event2.getCountry());
            assertEquals(test.getCity(), event2.getCity());
            assertEquals(test.getEventType(), event2.getEventType());
            assertEquals(test.getYear(), event2.getYear());
        }

        test = it.next();
        if (test.getEventID().equals(event1.getEventID())) {
            assertEquals(test.getEventID(), event1.getEventID());
            assertEquals(test.getDescendantID(), event1.getDescendantID());
            assertEquals(test.getPersonID(), event1.getPersonID());
            assertEquals(test.getLatitude(), event1.getLatitude(), 0);
            assertEquals(test.getLongitude(), event1.getLongitude(), 0);
            assertEquals(test.getCountry(), event1.getCountry());
            assertEquals(test.getCity(), event1.getCity());
            assertEquals(test.getEventType(), event1.getEventType());
            assertEquals(test.getYear(), event1.getYear());
        }
        else {
            assertEquals(test.getEventID(), event2.getEventID());
            assertEquals(test.getDescendantID(), event2.getDescendantID());
            assertEquals(test.getPersonID(), event2.getPersonID());
            assertEquals(test.getLatitude(), event2.getLatitude(), 0);
            assertEquals(test.getLongitude(), event2.getLongitude(), 0);
            assertEquals(test.getCountry(), event2.getCountry());
            assertEquals(test.getCity(), event2.getCity());
            assertEquals(test.getEventType(), event2.getEventType());
            assertEquals(test.getYear(), event2.getYear());
        }


        assertEquals(EventDAO.SINGLETON.getAllEvents("3"), null);
    }

    @Test
    public void deleteUserTest(){
        ResultSet rs;
        EventDAO.SINGLETON.addEvent(event3);

        assertTrue(EventDAO.SINGLETON.deleteUserInfo(event1.getDescendantID()));

        try{
            Statement statement = EventDAO.SINGLETON.getConnection().createStatement();

            rs = statement.executeQuery("SELECT * FROM Events");

            rs.next();

            String eventid = rs.getString("eventid");
            String descendant = rs.getString("descendant");
            String personid = rs.getString("personid");
            double latitude = rs.getDouble("latitude");
            double longitude = rs.getDouble("longitude");
            String country = rs.getString("country");
            String city = rs.getString("city");
            String type = rs.getString("type");
            int year = rs.getInt("year");

            assertEquals(eventid, event3.getEventID());
            assertEquals(descendant, event3.getDescendantID());
            assertEquals(personid, event3.getPersonID());
            assertEquals(latitude, event3.getLatitude(), 0);
            assertEquals(longitude, event3.getLongitude(), 0);
            assertEquals(country, event3.getCountry());
            assertEquals(city, event3.getCity());
            assertEquals(type, event3.getEventType());
            assertEquals(year, event3.getYear());

            rs.next();
            eventid = rs.getString("eventid");

            assertTrue(false);
        }catch(SQLException e){
            assertTrue(true);
        }

    }

    @Test
    public void clearTest(){
        ResultSet rs;

        assertTrue(EventDAO.SINGLETON.clearEventTable());

        try{
            Statement statement = EventDAO.SINGLETON.getConnection().createStatement();

            rs = statement.executeQuery("SELECT * FROM Events");

            rs.next();
            rs.getString("eventid");

            assertTrue(false);
        }catch(SQLException e){
            assertTrue(true);
        }
    }

    @AfterClass
    public static void end(){
        try {
            connection.close();
            EventDAO.SINGLETON.getConnection().close();
        }catch(SQLException e){
            System.err.print("Couldn't close connection");
        }
    }
}
