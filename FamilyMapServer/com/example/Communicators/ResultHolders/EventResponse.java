package com.example.Communicators.ResultHolders;

import com.example.ServerModel.Event;

import java.util.Iterator;
import java.util.Set;

public class EventResponse {
    private boolean error;
    private Event[] allEvents; //Make this a JSON object
    private String errorMessage;

    public EventResponse(){
        error = false;
        errorMessage = null;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Event[] getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(Event[] allEvents) {
        this.allEvents = allEvents;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addEvents(Set<Event> events){
        Iterator<Event> it = events.iterator();
        allEvents = new Event[events.size()];
        int counter = 0;

        while (it.hasNext()){
            allEvents[counter] = it.next();
            counter++;
        }
    }
}
