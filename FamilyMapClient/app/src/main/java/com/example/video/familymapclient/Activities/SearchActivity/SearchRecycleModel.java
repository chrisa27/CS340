package com.example.video.familymapclient.Activities.SearchActivity;

import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;

/**
 * Created by video on 4/15/2017.
 */

public class SearchRecycleModel {
    Person givenPerson;
    Event givenEvent;

    public Person getGivenPerson() {
        return givenPerson;
    }

    public void setGivenPerson(Person givenPerson) {
        this.givenPerson = givenPerson;
    }

    public Event getGivenEvent() {
        return givenEvent;
    }

    public void setGivenEvent(Event givenEvent) {
        this.givenEvent = givenEvent;
    }
}
