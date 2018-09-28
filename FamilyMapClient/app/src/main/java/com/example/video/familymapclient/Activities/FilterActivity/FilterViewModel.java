package com.example.video.familymapclient.Activities.FilterActivity;

import java.io.Serializable;

public class FilterViewModel implements Serializable{
    private String eventType;
    private boolean isSelected;

    public FilterViewModel(){
        isSelected = true;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
