package com.example.video.familymapclient.Communication.ResultHolders;

import com.example.video.familymapclient.Model.DatabaseModel.Event;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by video on 3/15/2017.
 */

public class ServerPerson {
    /**
     * A unique ID for this instance of a Person
     */
    private String personID;

    /**
     * The person ID associated with the user
     */
    private String descendant;

    /**
     * First name of the person
     */
    private String firstName;

    /**
     * Last name of the person
     */
    private String lastName;

    /**
     * Gender of the person
     */
    private String gender;

    /**
     * pointer to another person object representing the person's father (can be null)
     */
    private String father;

    /**
     * pointer to another person object representing the person's mother (can be null)
     */
    private String mother;

    /**
     * pointer to another person object representing the person's spouse (can be null)
     */
    private String spouse;

    /**
     * A list of all the events associated with this person
     */
    private Set<Event> lifeEvents;

    /**
     * Creates a new instance of person and initializes the fields for the object used for the user.
     *
     * @param firstName person's first name
     * @param lastName  person's last name
     * @param gender    person's gender
     */
    public ServerPerson(String firstName, String lastName, String gender) {
        personID = null;
        descendant = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        father = null;
        mother = null;
        spouse = null;
        lifeEvents = null;
    }

    public ServerPerson(String descendentID, String firstName, String lastName, String gender) {
        personID = null;
        this.descendant = descendentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        father = null;
        mother = null;
        spouse = null;
        lifeEvents = new HashSet<>();
    }

    public void addEvent(Event event){
        lifeEvents.add(event);
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendentID() {
        return descendant;
    }

    public void setDescendentID(String descendentID) {this.descendant = descendentID; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public Set<Event> getLifeEvents() {
        return lifeEvents;
    }

    public void setLifeEvents(Set<Event> lifeEvents) {
        this.lifeEvents = lifeEvents;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
