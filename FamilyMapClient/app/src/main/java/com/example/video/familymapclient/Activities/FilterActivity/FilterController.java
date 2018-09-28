package com.example.video.familymapclient.Activities.FilterActivity;

import com.example.video.familymapclient.Model.DatabaseModel.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class FilterController {
    private FilterModel mModel;

    FilterController(Event[] allEvents){
        Set<String> eventTypes = new HashSet<>();
        mModel = new FilterModel();

        for(Event event : allEvents){
            String type = event.getEventType().toLowerCase();
            type = type.substring(0,1).toUpperCase() + type.substring(1);
            eventTypes.add(type);
        }

        for (String eventType : eventTypes){
            mModel.addEventType(eventType);
        }
    }

    List<FilterViewModel> getEventViews(){
        List<FilterViewModel> items = new ArrayList<>();
        List<String> eventTypes = mModel.getEventTypes();

        for (String type : eventTypes){
            FilterViewModel model = new FilterViewModel();
            model.setEventType(type);
            items.add(model);
        }
        mModel.setViewModel(items);

        return items;
    }

    List<FilterViewModel> persist(FilterViewModel[] filterViewModels){
        List<FilterViewModel> models = new ArrayList<>();
        models.addAll(Arrays.asList(filterViewModels));
        mModel.setViewModel(models);
        return models;
    }

    FilterViewModel[] getViewModel(){
        Iterator<FilterViewModel> it = mModel.getViewModel().iterator();
        FilterViewModel[] models = new FilterViewModel[mModel.getViewModel().size()];
        int counter = 0;
        while (it.hasNext()){
            models[counter] = it.next();
            counter++;
        }
        return models;
    }
}
