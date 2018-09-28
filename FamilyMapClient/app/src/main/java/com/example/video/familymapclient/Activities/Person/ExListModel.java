package com.example.video.familymapclient.Activities.Person;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

import com.example.video.familymapclient.Activities.Icons;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;

/**
 * Created by video on 4/11/2017.
 */

public class ExListModel {
    private Icons icons;
    private String mainText;
    private String subText;
    private String gender;
    private Person mPerson;
    private Event mEvent;

    public ExListModel(Context context){
        icons = new Icons(context);
        gender = null;
    }

    public Drawable getIcon() {
        if (gender != null) {
            switch (gender) {
                case "m":
                    return icons.getMaleIcon();
                case "f":
                    return icons.getFemaleIcon();
                default:
                    return icons.getMarkerIcon();
            }
        }
        return icons.getMarkerIcon();
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Person getPerson() {
        return mPerson;
    }

    public void setPerson(Person person) {
        mPerson = person;
    }

    public Event getEvent() {
        return mEvent;
    }

    public void setEvent(Event event) {
        mEvent = event;
    }
}