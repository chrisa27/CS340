package com.example.video.familymapclient.Tasks;

import android.os.AsyncTask;

import com.example.video.familymapclient.Communication.ProxyServer;
import com.example.video.familymapclient.Communication.ResultHolders.EventResponse;
import com.example.video.familymapclient.Communication.ResultHolders.PersonResponse;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.EventOrderAdapter;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.Model.UserInfo;

import java.util.Set;
import java.util.TreeSet;

public class UserInfoTask extends AsyncTask<String, Void, UserInfo>{
    @Override
    protected UserInfo doInBackground(String... tokens){
        PersonResponse personResponse;
        EventResponse eventResponse;
        UserInfo allInfo = new UserInfo();
        String userToken = tokens[0];

        personResponse = ProxyServer.SINGLETON.person(null, userToken);
        eventResponse = ProxyServer.SINGLETON.event(null, userToken);

        if (eventResponse.isError() || personResponse.isError()){
            return allInfo;
        }
        else {
            Event[] events = eventResponse.getAllEvents();
            Person[] people = personResponse.getAllPeople();
            Set<Event> addedEvents;

            for(Person person : people){
                addedEvents = new TreeSet<>(new EventOrderAdapter());
                for (Event event : events){
                    if (person.getPersonID().equals(event.getPersonID())){
                        addedEvents.add(event);
                    }
                }
                person.setLifeEvents(addedEvents);
            }

            allInfo.setToken(userToken);
            allInfo.setAllPeople(people);
            allInfo.setAllEvents(events);
        }

        return allInfo;
    }
}
