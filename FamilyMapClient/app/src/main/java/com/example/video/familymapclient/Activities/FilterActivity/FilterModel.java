package com.example.video.familymapclient.Activities.FilterActivity;

import java.util.LinkedList;
import java.util.List;

public class FilterModel {
    private LinkedList<String> eventTypes;
    private List<FilterViewModel> viewModel;

    FilterModel(){
        eventTypes = new LinkedList<>();
        eventTypes.add("Male");
        eventTypes.add("Female");
        eventTypes.add("Mother's");
        eventTypes.add("Father's");
    }

    void addEventType(String type){
        eventTypes.addFirst(type);
    }

    LinkedList<String> getEventTypes() {
        return eventTypes;
    }

    List<FilterViewModel> getViewModel() {
        return viewModel;
    }

    void setViewModel(List<FilterViewModel> viewModel) {
        this.viewModel = viewModel;
    }
}
