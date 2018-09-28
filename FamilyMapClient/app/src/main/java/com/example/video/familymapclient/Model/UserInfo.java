package com.example.video.familymapclient.Model;

import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;

import java.io.Serializable;

public class UserInfo implements Serializable{
    private String token;
    private Person[] allPeople;
    private Event[] allEvents;

    public Person getPerson(int index){
        return allPeople[index];
    }

    public Person findUser(){
        for (Person person : allPeople){
            if (person.getPersonID().equals(person.getDescendant())){
                return person;
            }
        }
        return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Event getEvent(int index){
        return allEvents[index];
    }

    public Person[] getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(Person[] allPeople) {
        this.allPeople = allPeople;
    }

    public Event[] getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(Event[] allEvents) {
        this.allEvents = allEvents;
    }
}
