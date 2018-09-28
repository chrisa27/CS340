package com.example.Database.DataAccess;

import com.example.ServerModel.Event;
import com.example.ServerModel.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static com.example.Server.connection;


/**
 * EventDAO is used to access and manipulate data in the Event table in the database.
 */
public class EventDAO {
    /**
     * Singleton instance that allows the entire program to access it.
     */
    public static final EventDAO SINGLETON = new EventDAO();

    /**
     * Only allows the EventDAO class to construct itself.
     */
    private EventDAO(){};

    /**
     * Adds a new Event entry to the database.
     * @param event an object containing all of the parameters for an Event entry.
     */
    public void addEvent(Event event){
        String eventid = event.getEventID();
        String descendentid = event.getDescendantID();
        String personid = event.getPersonID();
        double latitude = event.getLatitude();
        double longitude = event.getLongitude();
        String country = event.getCountry();
        String city = event.getCity();
        String eventType = event.getEventType();
        int year = event.getYear();

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Events values(" +
                    "'" + eventid + "', " +
                    "'" + descendentid + "', " +
                    "'" + personid + "', " +
                    latitude + ", " +
                    longitude + ", " +
                    "'" + country + "', " +
                    "'" + city + "', " +
                    "'" + eventType + "', " +
                    year + ");");
        }catch(SQLException e){
            //System.err.println("Couldn't add the event to the table");
        }
    }

    /**
     * Queries the database for a single event given an eventID.
     * @param eventid the ID of the desired even.
     * @return Returns the desired event.
     */
    public Event getEvent(String eventid){
        Event event;
        String descendentid;
        String personid;
        double latitude;
        double longitude;
        String country;
        String city;
        String type;
        int year;

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events WHERE eventid = '" + eventid + "';");
            rs.next();
            descendentid = rs.getString("descendant");
            personid = rs.getString("personid");
            latitude = rs.getDouble("latitude");
            longitude = rs.getDouble("longitude");
            country = rs.getString("country");
            city = rs.getString("city");
            type = rs.getString("type");
            year = rs.getInt("year");
            Location location = new Location(latitude, longitude, city, country);

            event = new Event(descendentid, personid, location, type, year);
            event.setEventID(eventid);

            return event;
        }catch(SQLException e){
            //System.err.println("The Event doesn't exist");
            return null;
        }
    }

    public Event getIDEvent(String eventid, String descendentid){
        Event event;
        String personid;
        double latitude;
        double longitude;
        String country;
        String city;
        String type;
        int year;

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events WHERE eventid = '" + eventid + "' AND descendant = '" + descendentid + "';");
            rs.next();
            personid = rs.getString("personid");
            latitude = rs.getDouble("latitude");
            longitude = rs.getDouble("longitude");
            country = rs.getString("country");
            city = rs.getString("city");
            type = rs.getString("type");
            year = rs.getInt("year");
            Location location = new Location(latitude, longitude, city, country);

            event = new Event(descendentid, personid, location, type, year);
            event.setEventID(eventid);

            return event;
        }catch(SQLException e){
            //System.err.println("The Event doesn't exist");
            return null;
        }
    }

    /**
     * Accesses all of the events listed in the database.
     * @return The set of all events in the database.
     */
    public Set<Event> getAllEvents(String descendentid){
        HashSet<Event> allEvents = new HashSet<>();
        Event event;
        String eventid;
        String personid;
        double latitude;
        double longitude;
        String country;
        String city;
        String type;
        int year;

        try {
            boolean exists = false;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events WHERE descendant = '" + descendentid + "';");


            while (rs.next()) {
                exists = true;
                eventid = rs.getString("eventid");
                personid = rs.getString("personid");
                latitude = rs.getDouble("latitude");
                longitude = rs.getDouble("longitude");
                country = rs.getString("country");
                city = rs.getString("city");
                type = rs.getString("type");
                year = rs.getInt("year");
                Location location = new Location(latitude, longitude, city, country);

                event = new Event(descendentid, personid, location, type, year);
                event.setEventID(eventid);

                allEvents.add(event);
            }

            if (exists) {
                return allEvents;
            }
            else {
                return null;
            }
        }catch(SQLException e) {
            //System.err.println("Error getting all events");
            return null;
        }
    }

    /**
     * Deletes all of the events that are connected to a given user.
     * @param descendentid the shared id for the user whose data will be deleted
     * @return returns whether the delete was successful or not.
     */
    public boolean deleteUserInfo(String descendentid){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Events WHERE descendant = '" + descendentid + "';");

            return true;
        }catch (SQLException e){
            //System.err.println("Error deleting user Events data");
            return false;
        }
    }

    /**
     * Deletes all of the Event data in the database.
     * @return Returns if the method was successful in deleting all Event data.
     */
    public boolean clearEventTable(){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Events;");
            return true;
        }catch(SQLException e){
            //System.err.println("Couldn't delete data from Events");
            return false;
        }
    }

    public Event getTypeEvent(String personid, String type){
        Event event;
        String eventid;
        String descendentid;
        double latitude;
        double longitude;
        String country;
        String city;
        int year;

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events WHERE personid = '" + personid + "' AND type = '" + type + "';");
            rs.next();
            eventid = rs.getString("eventid");
            descendentid = rs.getString("descendant");
            latitude = rs.getDouble("latitude");
            longitude = rs.getDouble("longitude");
            country = rs.getString("country");
            city = rs.getString("city");
            type = rs.getString("type");
            year = rs.getInt("year");
            Location location = new Location(latitude, longitude, city, country);

            event = new Event(descendentid, personid, location, type, year);
            event.setEventID(eventid);

            return event;
        }catch(SQLException e){
            //System.err.println("The Event doesn't exist");
            return null;
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
