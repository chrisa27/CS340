package com.example.video.familymapclient.Activities.MapActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.video.familymapclient.Activities.Icons;
import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.Person.PersonActivity;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by video on 4/12/2017.
 */

public class MapActivityController {
    private MapActivityModel mModel;
    private Icons icons;

    public Person getPerson(){
        Person[] allPeople = mModel.getAllPeople();

        for (int i = 0; i < allPeople.length; i++){
            if (allPeople[i].getPersonID().equals(mModel.getChosenEvent().getPersonID())){
                return allPeople[i];
            }
        }
        return null;
    }

    public String getEventInfo(){
        Event currentEvent = mModel.getChosenEvent();
        return currentEvent.getEventType() + ": " +
                currentEvent.getCity() + ", " + currentEvent.getCountry() +
                " (" + currentEvent.getYear() + ")";
    }

    public Drawable setIcon(String icon) {
        switch(icon){
            case MainActivity.MALE_ICON:
                return icons.getMaleIcon();
            case MainActivity.FEMALE_ICON:
                return icons.getFemaleIcon();
            case MainActivity.ANDROID_ICON:
                return icons.getAndroidIcon();
            case MainActivity.CHEVRON_ICON:
                return icons.getCheveronIcon();
            default:
                return icons.getAndroidIcon();
        }
    }

    public Drawable setGenderIcon(){
        switch (getPerson().getGender()) {
            case "m":
                return setIcon(MainActivity.MALE_ICON);
            case "f":
                return setIcon(MainActivity.FEMALE_ICON);
            default:
                return setIcon(MainActivity.ANDROID_ICON);
        }
    }

    public MapActivityController(Bundle bundle, Context context){
        mModel = new MapActivityModel();
        mModel.setChosenEvent((Event)bundle.getSerializable(PersonActivity.CURR_EVENT));
        mModel.setAllEvents((Event[])bundle.getSerializable(MainActivity.ALL_EVENTS));
        mModel.setAllPeople((Person[])bundle.getSerializable(MainActivity.ALL_PEOPLE));
        icons = new Icons(context);
    }

    public void initializeMap(){

    }

    public void setMap(GoogleMap map){
        mModel.setMap(map);
    }
}
