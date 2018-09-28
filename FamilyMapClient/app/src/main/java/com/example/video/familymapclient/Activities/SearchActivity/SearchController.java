package com.example.video.familymapclient.Activities.SearchActivity;

import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by video on 4/15/2017.
 */

public class SearchController {
    List<Person> filteredPeople;
    List<Event> filteredEvents;

    public SearchController(Person[] allPeople, List<Event> filteredEvents){
        this.filteredPeople = createFilteredPeople(allPeople, filteredEvents);
        this.filteredEvents = filteredEvents;
    }

    private List<Person> createFilteredPeople(Person[] allPeople, List<Event> filteredEvents){
        List<Person> filteredPeople = new ArrayList<>();
        Set<Person> noDuplicates = new HashSet<>();
        for(Event event : filteredEvents){
            noDuplicates.add(findPerson(event.getPersonID(), Arrays.asList(allPeople)));
        }
        filteredPeople.addAll(noDuplicates);
        return filteredPeople;
    }

    private boolean checkString(String toCheck, String query){
        if (toCheck.length() >= query.length()){
            for (int i = 0; i <= toCheck.length() - query.length(); i++){
                if (toCheck.substring(i, i + query.length()).toLowerCase().equals(query.toLowerCase())){
                    return true;
                }
            }
        }
        return false;
    }

    private Person findPerson(String id, List<Person> filteredPeople){
        for (Person person : filteredPeople){
            if (id.equals(person.getPersonID())){
                return person;
            }
        }
        return null;
    }

    private boolean searchPerson(Person person, String query){
        if (checkString(person.getFirstName(), query) || checkString(person.getLastName(), query)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean searchEvent(Event event, String query){
        if (checkString(event.getCity(), query) || checkString(event.getCountry(), query)
                || checkString(event.getEventType(), query) || checkString(Integer.toString(event.getYear()), query)){
            return true;
        }
        return false;
    }

    public void updateSearch(String query, SearchAdapter adapter){
        List<SearchRecycleModel> items = new ArrayList<>();
        SearchRecycleModel model;
        for (Person person : filteredPeople){
            if (searchPerson(person, query)){
                model = new SearchRecycleModel();
                model.setGivenEvent(null);
                model.setGivenPerson(person);
                items.add(model);
            }
        }

        for (Event event : filteredEvents){
            if (searchEvent(event, query) ||
                    searchPerson(findPerson(event.getPersonID(), filteredPeople), query)){
                model = new SearchRecycleModel();
                model.setGivenPerson(null);
                model.setGivenEvent(event);
                items.add(model);
            }
        }

        adapter.setItems(items);
    }
}
