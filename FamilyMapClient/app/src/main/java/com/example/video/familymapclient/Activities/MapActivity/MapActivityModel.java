package com.example.video.familymapclient.Activities.MapActivity;

import android.provider.Contacts;

import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by video on 4/12/2017.
 */

public class MapActivityModel {
    private Event chosenEvent;
    private Event[] allEvents;
    private Person[] allPeople;
    GoogleMap mMap;

    public GoogleMap getMap() {
        return mMap;
    }

    public void setMap(GoogleMap map) {
        mMap = map;
    }

    public Event getChosenEvent() {
        return chosenEvent;
    }

    public void setChosenEvent(Event chosenEvent) {
        this.chosenEvent = chosenEvent;
    }

    public Event[] getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(Event[] allEvents) {
        this.allEvents = allEvents;
    }

    public Person[] getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(Person[] allPeople) {
        this.allPeople = allPeople;
    }
}
