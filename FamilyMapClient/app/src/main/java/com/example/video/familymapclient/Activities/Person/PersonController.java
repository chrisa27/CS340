package com.example.video.familymapclient.Activities.Person;

import android.content.Context;

import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by video on 4/11/2017.
 */

public class PersonController {
    private Context mContext;

    public String getGender(String gender){
        if (gender.equals("m")){
            return "Male";
        }
        else if (gender.equals("f")){
            return "Female";
        }
        else{
            return "Error";
        }
    }

    private String createEventInfo(Event event){
        return event.getEventType() + ": " +
                event.getCity() + ", " + event.getCountry() +
                " (" + event.getYear() + ")";
    }

    public PersonController(Context context){
        mContext = context;
    }

    public List<String> initializeListHeaders(){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("LIFE EVENTS");
        headers.add("FAMILY");
        return headers;
    }

    private List<ExListModel> getFamily(Person mainPerson, Person[] allPeople, Map<String, String> allChildren){
        LinkedList<ExListModel> family = new LinkedList<>();

        for (int i = 0; i < allPeople.length; i++){
            ExListModel model = new ExListModel(mContext);
            model.setPerson(allPeople[i]);

            if (mainPerson.getSpouse() != null && mainPerson.getSpouse().equals(allPeople[i].getPersonID())){
                model.setMainText(allPeople[i].getFirstName() + " " + allPeople[i].getLastName());
                model.setSubText("Spouse");
                model.setGender(allPeople[i].getGender());
                family.addFirst(model);
            }
            else if (mainPerson.getMother() != null && mainPerson.getMother().equals(allPeople[i].getPersonID())){
                model.setMainText(allPeople[i].getFirstName() + " " + allPeople[i].getLastName());
                model.setSubText("Mother");
                model.setGender(allPeople[i].getGender());
                family.add(model);
            }
            else if (mainPerson.getFather() != null && mainPerson.getFather().equals(allPeople[i].getPersonID())){
                model.setMainText(allPeople[i].getFirstName() + " " + allPeople[i].getLastName());
                model.setSubText("Father");
                model.setGender(allPeople[i].getGender());
                family.addLast(model);
            }
            else if (allChildren.get(mainPerson.getPersonID()) != null &&
                    allChildren.get(mainPerson.getPersonID()).equals(allPeople[i].getPersonID())){
                model.setMainText(allPeople[i].getFirstName() + " " + allPeople[i].getLastName());
                model.setSubText("Child");
                model.setGender(allPeople[i].getGender());
                family.addLast(model);
            }
        }

        return family;
    }

    public HashMap<String, List<ExListModel>> initializeListChildren(List<String> headers, Person person,
                                                                     Person[] allPeople, Map<String, String> allChildren,
                                                                     List<Event> filteredEvents){
        HashMap<String, List<ExListModel>> children = new HashMap<>();
        LinkedList<ExListModel> groupChildren = new LinkedList<>();
        Set<Event> events = person.getLifeEvents();
        Event death = null;

        if (events != null) {
            Iterator<Event> it = events.iterator();
            while (it.hasNext()) {
                ExListModel model = new ExListModel(mContext);
                Event currentEvent = it.next();
                for (Event event : filteredEvents) {
                    if (currentEvent.getEventID().equals(event.getEventID())) {
                        model.setEvent(currentEvent);
                        model.setMainText(createEventInfo(currentEvent));
                        model.setSubText(person.getFirstName() + " " + person.getLastName());
                        if (currentEvent.getEventType().equals("birth")) {
                            groupChildren.addFirst(model);
                        } else if (currentEvent.getEventType().equals("death")) {
                            death = currentEvent;
                        } else {
                            groupChildren.add(model);
                        }
                    }
                }
            }

            if (death != null) {
                ExListModel model = new ExListModel(mContext);
                model.setMainText(createEventInfo(death));
                model.setSubText(person.getFirstName() + " " + person.getLastName());
                model.setEvent(death);
                groupChildren.addLast(model);
            }
        }
        children.put(headers.get(0), groupChildren);

        children.put(headers.get(1), getFamily(person, allPeople, allChildren));

        return children;
    }
}
