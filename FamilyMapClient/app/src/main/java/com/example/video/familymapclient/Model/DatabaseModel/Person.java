package com.example.video.familymapclient.Model.DatabaseModel;

import java.io.Serializable;
import java.util.Set;

/**
 * Represents all of the ancestors and family members stored in the family map program
 */
public class Person implements Serializable{
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

    public Person(String personID, String descendant, String firstName, String lastName,
                  String gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public void setLifeEvents(Set<Event> lifeEvents) {
        this.lifeEvents = lifeEvents;
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendant() {
        return descendant;
    }

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

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public Set<Event> getLifeEvents() {
        return lifeEvents;
    }
}
