package com.example.Communicators.ResultHolders;

import com.example.ServerModel.Person;

import java.util.Iterator;
import java.util.Set;

public class PersonResponse {
    private boolean error;
    private Person[] allPeople;
    private String errorMessage;

    public PersonResponse(){
        error = false;
        errorMessage = null;
        allPeople = null;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Person[] getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(Person[] allPeople) {
        this.allPeople = allPeople;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addPeople(Set<Person> people){
        Iterator<Person> it = people.iterator();
        allPeople = new Person[people.size()];
        int counter = 0;

        while(it.hasNext()){
            allPeople[counter] = it.next();
            counter++;
        }
    }
}
