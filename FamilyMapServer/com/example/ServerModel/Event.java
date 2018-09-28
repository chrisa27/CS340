package com.example.ServerModel;

import com.example.Database.DataAccess.EventDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Event represents all of the events associated with specific people in the Family Map program.
 */
public class Event {

    /** A unique ID to represent this specific event */
    private String eventID;

    /** The person ID of the logged in user */
    private String descendantID;

    /** The person ID of the person this event belongs to */
    private String personID;

    /** The lattitude of where the event happened */
    private double latitude;

    /** The longitude of where the event happened */
    private double longitude;

    /** The country where the event happened */
    private String country;

    /** The city where the event happened */
    private String city;

    /** The type of event. Could be a baptism, birth, death, etc. */
    private String eventType;

    /** The year that the event happened. */
    private int year;

    /**
     * Creates and initializes all of the fields of a new Event object
     * @param descendantID personid of the user the person belongs to
     * @param personID id of the person this event belongs to
     * @param eventType what the event was
     * @param year year the event occurred
     */
    public Event(String descendantID, String personID, Location location, String eventType, int year){
        eventID = generateEventid();
        this.descendantID = descendantID;
        this.personID = personID;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        country = location.getCountry();
        city = location.getCity();
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Generates a new eventid that isn't already in use.
     * @return Returns an eventid
     */
    private String generateEventid(){
        String eventId;

        do{
            eventId = UUID.randomUUID().toString();
        }while(EventDAO.SINGLETON.getEvent(eventId) != null);

        return eventId;
    }



    @Override
    public int hashCode(){
        return eventID.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o instanceof  Event){
            Event obj = (Event) o;
            if (obj.getEventID().equals(this.eventID)){
                return true;
            }
        }
        return false;
    }

    public String getEventID() {
        return eventID;
    }

    public String getDescendantID() {
        return descendantID;
    }

    public String getPersonID() {
        return personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public void setEventID(String id){
        this.eventID = id;
    }

    public void setDescendantID(String descendantID) {
        this.descendantID = descendantID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
