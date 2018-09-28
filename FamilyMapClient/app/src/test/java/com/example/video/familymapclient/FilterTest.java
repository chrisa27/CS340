package com.example.video.familymapclient;

import com.example.video.familymapclient.Activities.FilterActivity.FilterViewModel;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapController;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapModel;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Location;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.Model.UserInfo;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by video on 4/18/2017.
 */

public class FilterTest {
    private static UserInfo testInformation;
    private static MapModel mMapModel;
    private static MapController mController;
    private static FilterViewModel[] sFilterViewModels;

    /*create 3 people
    each have birth death and marriage can be in the same place
     */

    @BeforeClass
    public static void initializeModels(){
        testInformation = new UserInfo();
        Person[] allPeople = new Person[3];
        allPeople[0] = new Person("user", "user", "Chris", "Arnold", "m", "dad", "mom", null);
        allPeople[1] = new Person("mom", "user", "Courtney", "Arnold", "f", null, null, "dad");
        allPeople[2] = new Person("dad", "user", "Darren", "Arnold", "m", null, null, "mom");
        testInformation.setAllPeople(allPeople);

        Location byu = new Location(40.2518, -111.6493, "Provo", "USA");
        Event[] allEvents = new Event[8];
        allEvents[0] = new Event("user", "user", byu, "Birth", 1765);
        allEvents[0].setEventID("0");
        allEvents[1] = new Event("user", "user", byu, "Death", 1830);
        allEvents[1].setEventID("1");
        allEvents[2] = new Event("user", "mom", byu, "Birth", 1749);
        allEvents[2].setEventID("2");
        allEvents[3] = new Event("user", "mom", byu, "Marriage", 1760);
        allEvents[3].setEventID("3");
        allEvents[4] = new Event("user", "mom", byu, "Death", 1800);
        allEvents[4].setEventID("4");
        allEvents[5] = new Event("user", "dad", byu, "Birth", 1750);
        allEvents[5].setEventID("5");
        allEvents[6] = new Event("user", "dad", byu, "Marriage", 1760);
        allEvents[6].setEventID("6");
        allEvents[7] = new Event("user", "dad", byu, "Death", 1802);
        allEvents[7].setEventID("7");
        testInformation.setAllEvents(allEvents);

        mMapModel = new MapModel(testInformation);
        mController = new MapController(null, mMapModel);
        sFilterViewModels = new FilterViewModel[7];
        for (int i = 0 ; i < sFilterViewModels.length; i++){
            sFilterViewModels[i] = new FilterViewModel();
        }
        sFilterViewModels[0].setEventType("Birth");
        sFilterViewModels[1].setEventType("Marriage");
        sFilterViewModels[2].setEventType("Death");
        sFilterViewModels[3].setEventType("Male");
        sFilterViewModels[4].setEventType("Female");
        sFilterViewModels[5].setEventType("Mother's");
        sFilterViewModels[6].setEventType("Father's");
    }


    @Test
    public void allFiltersOff(){
        for (FilterViewModel model : sFilterViewModels){
            model.setSelected(true);
        }
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();
        Iterator<Event> filteredEvents = mMapModel.getFilteredEvents().iterator();

        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
    }

    @Test
    public void allFiltersOn(){
        for (FilterViewModel model : sFilterViewModels){
            model.setSelected(false);
        }
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();

        List<Event> filteredEvents = mMapModel.getFilteredEvents();
        assertTrue(filteredEvents.size() == 0);
    }

    @Test
    public void genderFilter(){
        //tests male
        for(FilterViewModel model : sFilterViewModels){
            model.setSelected(true);
        }
        sFilterViewModels[3].setSelected(false);
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();

        Iterator<Event> filteredEvents = mMapModel.getFilteredEvents().iterator();

        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());

        sFilterViewModels[3].setSelected(true);
        sFilterViewModels[4].setSelected(false);
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();

        filteredEvents = mMapModel.getFilteredEvents().iterator();
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
    }

    @Test
    public void sideFilter(){
        for(FilterViewModel model : sFilterViewModels){
            model.setSelected(true);
        }
        sFilterViewModels[5].setSelected(false);
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();
        Iterator<Event> filteredEvents = mMapModel.getFilteredEvents().iterator();

        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());

        sFilterViewModels[5].setSelected(true);
        sFilterViewModels[6].setSelected(false);
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();

        filteredEvents = mMapModel.getFilteredEvents().iterator();
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
    }

    @Test
    public void eventFilter(){
        //removes the death events
        for(FilterViewModel model : sFilterViewModels){
            model.setSelected(true);
        }
        sFilterViewModels[2].setSelected(false);
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();
        Iterator<Event> filteredEvents = mMapModel.getFilteredEvents().iterator();

        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());

        //test with an eventtype that doesn't exist
        sFilterViewModels[2].setEventType("Christening");
        mMapModel.setFilterViews(sFilterViewModels);
        mController.filter();

        filteredEvents = mMapModel.getFilteredEvents().iterator();
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
        assertEquals("Birth", filteredEvents.next().getEventType());
        assertEquals("Marriage", filteredEvents.next().getEventType());
        assertEquals("Death", filteredEvents.next().getEventType());
    }
}
