package com.example.video.familymapclient.Model.DatabaseModel;

import java.io.Serializable;

/**
 * Event represents all of the events associated with specific people in the Family Map program.
 */
public class Event implements Serializable {

    /** A unique ID to represent this specific event */
    private String eventID;

    /** The person ID of the logged in user */
    private String descendant;

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
        eventID = null;
        this.descendant = descendantID;
        this.personID = personID;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        country = location.getCountry();
        city = location.getCity();
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getDescendantID() {
        return descendant;
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

    public void setEventType(String type){
        eventType = type;
    }

    public void setEventID(String id){
        this.eventID = id;
    }

    public void setDescendantID(String descendantID) {
        this.descendant = descendantID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
