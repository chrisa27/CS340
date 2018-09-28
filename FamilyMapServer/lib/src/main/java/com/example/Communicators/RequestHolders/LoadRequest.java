package com.example.Communicators.RequestHolders;

import com.example.ServerModel.Event;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;

import java.util.Iterator;
import java.util.Set;

public class LoadRequest {
    private UserAccount[] users;
    private Person[] persons;
    private Event[] events;

    public LoadRequest(Set<UserAccount> users, Set<Person> people, Set<Event> events){
        if (users != null) {
            this.users = new UserAccount[users.size()];
        }
        else {
            this.users = null;
        }
        if (people != null) {
            persons = new Person[people.size()];
        }
        else{
            persons = null;
        }
        if (events != null) {
            this.events = new Event[events.size()];
        }
        else{
            this.events = null;
        }

        if (this.users != null && persons != null && this.events != null) {
            Iterator<UserAccount> it1 = users.iterator();
            Iterator<Person> it2 = people.iterator();
            Iterator<Event> it3 = events.iterator();
            int counter = 0;

            while (it1.hasNext()) {
                this.users[counter] = it1.next();
                counter++;
            }
            counter = 0;

            while (it2.hasNext()) {
                persons[counter] = it2.next();
                counter++;
            }
            counter = 0;

            while (it3.hasNext()) {
                this.events[counter] = it3.next();
                counter++;
            }
        }
    }

    public UserAccount[] getUsers() {
        return users;
    }

    public void setUsers(UserAccount[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
