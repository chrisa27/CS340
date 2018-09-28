package com.example.video.familymapclient;

import com.example.video.familymapclient.Activities.Main.MapFragment.MapController;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapModel;
import com.example.video.familymapclient.Activities.Person.ExListModel;
import com.example.video.familymapclient.Activities.Person.PersonController;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Location;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.Model.UserInfo;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by video on 4/18/2017.
 */

public class FindFamilyTest {
    private static UserInfo mUserInfo;
    private static MapController mMapController;
    private static MapModel sMapModel;
    private static PersonController mPersonController;
    private static final Location byu = new Location(40.2518, -111.6493, "Provo", "USA");
    private static List<String> headers;

    @BeforeClass
    public static void initializeUserInfo() {
        //children are set in the initialization of the map controller.
        //family relations are assessed in the person controller.
        mUserInfo = new UserInfo();
        Person[] allPeople;
        allPeople = new Person[7];
        allPeople[0] = new Person("user", "user", "Chris", "Arnold", "m", "dad", "mom", null);
        allPeople[1] = new Person("mom", "user", "Courtney", "Arnold", "f", "mGrandpa", "mGrandma", "dad");
        allPeople[2] = new Person("dad", "user", "Darren", "Arnold", "m", "dGrandpa", "dGrandma", "mom");
        allPeople[3] = new Person("mGrandma", "user", "Betty", "Johnson", "f", null, null, "mGrandpa");
        allPeople[4] = new Person("mGrandpa", "user", "Rick", "Johnson", "m", null, null, "mGrandma");
        allPeople[5] = new Person("dGrandpa", "user", "Earl", "Arnold", "m", null, null, "dGrandma");
        allPeople[6] = new Person("dGrandma", "user", "Carol", "Arnold", "f", null, null, "dGrandpa");
        mUserInfo.setAllPeople(allPeople);

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
        mUserInfo.setAllEvents(allEvents);

        sMapModel = new MapModel(mUserInfo);

        mMapController = new MapController(null, sMapModel);
        mPersonController = new PersonController(null);

        headers = new ArrayList<>();
        headers.add("Events");
        headers.add("Family");
    }

    @Test
    public void regularFamilyTest(){
        //Test a male in the middle of the generations.
        Map<String, List<ExListModel>> adapterInfo;
        adapterInfo = mPersonController.initializeListChildren(headers, sMapModel.getAllPeople()[2], sMapModel.getAllPeople(),
                sMapModel.getChildren(), sMapModel.getFilteredEvents());
        List<ExListModel> familyTies = adapterInfo.get(headers.get(1));

        assertEquals(sMapModel.getAllPeople()[1], familyTies.get(0).getPerson());
        assertEquals(sMapModel.getAllPeople()[0], familyTies.get(1).getPerson());
        assertEquals(sMapModel.getAllPeople()[5], familyTies.get(2).getPerson());
        assertEquals(sMapModel.getAllPeople()[6], familyTies.get(3).getPerson());

        //Test a female in the middle of the generations.
        adapterInfo = mPersonController.initializeListChildren(headers, sMapModel.getAllPeople()[1], sMapModel.getAllPeople(),
                sMapModel.getChildren(), sMapModel.getFilteredEvents());
        familyTies = adapterInfo.get(headers.get(1));

        assertEquals(sMapModel.getAllPeople()[2], familyTies.get(0).getPerson());
        assertEquals(sMapModel.getAllPeople()[0], familyTies.get(1).getPerson());
        assertEquals(sMapModel.getAllPeople()[3], familyTies.get(2).getPerson());
        assertEquals(sMapModel.getAllPeople()[4], familyTies.get(3).getPerson());
    }

    @Test
    public void boundaryTest(){
        //Test the root user.
        Map<String, List<ExListModel>> adapterInfo;
        adapterInfo = mPersonController.initializeListChildren(headers, sMapModel.getAllPeople()[0], sMapModel.getAllPeople(),
                sMapModel.getChildren(), sMapModel.getFilteredEvents());
        List<ExListModel> familyTies = adapterInfo.get(headers.get(1));

        assertEquals(sMapModel.getAllPeople()[1], familyTies.get(0).getPerson());
        assertEquals(sMapModel.getAllPeople()[2], familyTies.get(1).getPerson());

        //Test a person from the last generation found.
        adapterInfo = mPersonController.initializeListChildren(headers, sMapModel.getAllPeople()[6], sMapModel.getAllPeople(),
                sMapModel.getChildren(), sMapModel.getFilteredEvents());
        familyTies = adapterInfo.get(headers.get(1));

        assertEquals(sMapModel.getAllPeople()[5], familyTies.get(0).getPerson());
        assertEquals(sMapModel.getAllPeople()[2], familyTies.get(1).getPerson());
    }

    @Test
    public void peopleInDataNotConnected(){
        //add a person in the array that is not connected to the family and test the normal family member.
        Person[] newPeople = new Person[sMapModel.getAllPeople().length + 1];
        for (int i = 0; i < sMapModel.getAllPeople().length; i++){
            newPeople[i] = sMapModel.getAllPeople()[i];
        }
        newPeople[sMapModel.getAllPeople().length] = new Person("RandomID", "nottheSameUser", "John",
                "Smith", "m", null, null, null);

        mUserInfo.setAllPeople(newPeople);
        sMapModel = new MapModel(mUserInfo);
        mMapController = new MapController(null, sMapModel);

        Map<String, List<ExListModel>> adapterInfo;
        adapterInfo = mPersonController.initializeListChildren(headers, sMapModel.getAllPeople()[2], sMapModel.getAllPeople(),
                sMapModel.getChildren(), sMapModel.getFilteredEvents());
        List<ExListModel> familyTies = adapterInfo.get(headers.get(1));

        assertEquals(sMapModel.getAllPeople()[1], familyTies.get(0).getPerson());
        assertEquals(sMapModel.getAllPeople()[0], familyTies.get(1).getPerson());
        assertEquals(sMapModel.getAllPeople()[5], familyTies.get(2).getPerson());
        assertEquals(sMapModel.getAllPeople()[6], familyTies.get(3).getPerson());

        //test the unconnected person
        adapterInfo = mPersonController.initializeListChildren(headers, sMapModel.getAllPeople()[7], sMapModel.getAllPeople(),
                sMapModel.getChildren(), sMapModel.getFilteredEvents());
        familyTies = adapterInfo.get(headers.get(1));

        assertEquals(0, familyTies.size());
    }
}
