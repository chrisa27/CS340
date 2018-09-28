package com.example.video.familymapclient.Activities.Main.MapFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.video.familymapclient.Activities.Icons;
import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.MapActivity.MapActivity;
import com.example.video.familymapclient.Activities.SettingsActivity.SettingsActivity;
import com.example.video.familymapclient.Activities.FilterActivity.FilterViewModel;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.Model.UserInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MapController {
    public static final int REQUEST_CODE_FILTER = 0;
    public static final int REQUEST_CODE_SETTINGS = 1;

    private static final String LIFE = "life";
    private static final String TREE = "tree";
    private static final String SPOUSE = "spouse";

    private static final int NORMAL_MAP = 0;
    private static final int HYBRID_MAP = 1;
    private static final int SATELLITE_MAP = 2;
    private static final int TERRAIN_MAP = 3;

    private MapModel mModel;
    private Icons icons;

    public MapController(Context context, MapModel model){
        mModel = model;
        icons = new Icons(context);
        setChildren();
    }

    public void checkInput(Bundle bundle){
        Event initialEvent = (Event)bundle.getSerializable(MapActivity.MAP_ACT);
        if (initialEvent != null){
            mModel.setCenter(true);
            for (Marker marker : mModel.getAllMarkers()){
                if (((Event)marker.getTag()).getEventID().equals(initialEvent.getEventID())){
                    markerClick(marker);
                    LatLng position = new LatLng(initialEvent.getLatitude(), initialEvent.getLongitude());
                    mModel.getMap().animateCamera(CameraUpdateFactory.newLatLng(position));
                }
            }
        }
    }

    private void setChildren(){
        Person[] allPeople = mModel.getAllPeople();
        for (Person person : allPeople){
            mModel.getChildren().put(person.getMother(), person.getPersonID());
            mModel.getChildren().put(person.getFather(), person.getPersonID());
        }
    }

    public Drawable setIcon(String icon){
        switch(icon){
            case MainActivity.MALE_ICON:
                mModel.setGenderIcon(icons.getMaleIcon());
                break;
            case MainActivity.FEMALE_ICON:
                mModel.setGenderIcon(icons.getFemaleIcon());
                break;
            case MainActivity.ANDROID_ICON:
                mModel.setGenderIcon(icons.getAndroidIcon());
                break;
            case MainActivity.SEARCH_ICON:
                mModel.setGenderIcon(icons.getSearchIcon());
                break;
            case MainActivity.FILTER_ICON:
                mModel.setGenderIcon(icons.getFilterIcon());
                break;
            case MainActivity.SETTINGS_ICON:
                mModel.setGenderIcon(icons.getSettingsIcon());
                break;
            default:
                return mModel.getGenderIcon();
        }

        return mModel.getGenderIcon();
    }

    public void initializeMap(){
        List<Event> allEvents = mModel.getFilteredEvents();
        Set<Marker> allMarkers = mModel.getAllMarkers();

        for (Event event : allEvents){
            event.setEventType(event.getEventType().toLowerCase());
            mModel.getEventTypes().add(event.getEventType().toLowerCase());
        }

        for (Marker marker : allMarkers){
            marker.remove();
        }
        allMarkers.clear();

        for (Event event : allEvents) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(event.getLatitude(), event.getLongitude()));

            Set<String> eventTypes = mModel.getEventTypes();
            List<String> eventList = new ArrayList<>();
            eventList.addAll(eventTypes);
            if (event.getEventType().equals(eventList.get(0))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            else if (event.getEventType().equals(eventList.get(1))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            else if (event.getEventType().equals(eventList.get(2))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
            else if (event.getEventType().equals(eventList.get(3))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            }
            else if (event.getEventType().equals(eventList.get(4))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            }
            else if (event.getEventType().equals(eventList.get(5))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
            else if (event.getEventType().equals(eventList.get(6))){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }

            mModel.addMarker(marker, event);
        }

        if (mModel.isCenter()){
            Event event = mModel.getCurrentEvent();
            for (Marker marker : mModel.getAllMarkers()){
                if (((Event)marker.getTag()).getEventID().equals(event.getEventID())){
                    markerClick(marker);
                }
            }
        }
    }

    private Person getEventPerson(String personID){
        Person eventPerson = null;
        Person[] allPeople = mModel.getAllPeople();

        for (int i = 0; i < allPeople.length; i++){
            if (allPeople[i].getPersonID().equals(personID)){
                eventPerson = allPeople[i];
            }
        }

        return eventPerson;
    }

    private int setColor(int color){
        if (color == SettingsActivity.LINE_COLORS.RED.getColor()){
            return Color.RED;
        }
        else if (color == SettingsActivity.LINE_COLORS.BLUE.getColor()){
            return Color.BLUE;
        }
        else if (color == SettingsActivity.LINE_COLORS.GREEN.getColor()){
            return Color.GREEN;
        }
        else if (color == SettingsActivity.LINE_COLORS.YELLOW.getColor()){
            return Color.YELLOW;
        }
        else{
            return -1;
        }
    }

    private void setLifeLine(Person person){
        PolylineOptions familyLine = new PolylineOptions();
        Set<Event> personEvents = person.getLifeEvents();
        Iterator<Event> it = personEvents.iterator();
        Event currentEvent;
        Iterator<Marker> markers;

        while (it.hasNext()){
            currentEvent = it.next();
            markers = mModel.getAllMarkers().iterator();
            Marker currentMarker;
            while (markers.hasNext()){
                currentMarker = markers.next();
                if (currentEvent.getEventID().equals(((Event)currentMarker.getTag()).getEventID())){
                    familyLine.add(currentMarker.getPosition()).width(20).color(setColor(mModel.getLifeColor()));
                }
            }
        }

        mModel.addLine(familyLine, LIFE);
    }

    private void setTreeLine(Person person, Event start, int lineWidth){
        Person dad = null;
        Person mom = null;
        Person[] allPeople = mModel.getAllPeople();

        if (lineWidth < 3){
            lineWidth = 3;
        }


        for (int i = 0; i < allPeople.length; i++){
            if (person.getFather() != null) {
                if (person.getFather().equals(allPeople[i].getPersonID())) {
                    dad = allPeople[i];
                }
            }
            if (person.getMother() != null) {
                if (person.getMother().equals(allPeople[i].getPersonID())) {
                    mom = allPeople[i];
                }
            }
        }

        Set<Event> parentEvents;
        Iterator<Event> eventIterator;

        if (dad != null){
            if (dad.getLifeEvents().size() != 0) {
                outerloop:
                for (Event event : dad.getLifeEvents()) {
                    for (Marker marker : mModel.getAllMarkers()) {
                        if (((Event) marker.getTag()).getEventID().equals(event.getEventID())) {
                            PolylineOptions dadLine = new PolylineOptions();
                            dadLine.add(new LatLng(start.getLatitude(), start.getLongitude()));
                            dadLine.add(new LatLng(event.getLatitude(), event.getLongitude()));
                            dadLine.width(lineWidth).color(setColor(mModel.getTreeColor()));

                            mModel.addLine(dadLine, TREE);
                            setTreeLine(dad, event, lineWidth - 5);
                            break outerloop;
                        }
                    }
                }
            }
        }

        if (mom != null){
            if (mom.getLifeEvents().size() != 0) {
                outerloop:
                for (Event event : mom.getLifeEvents()) {
                    for (Marker marker : mModel.getAllMarkers()) {
                        if (((Event) marker.getTag()).getEventID().equals(event.getEventID())) {
                            PolylineOptions momLine = new PolylineOptions();
                            momLine.add(new LatLng(start.getLatitude(), start.getLongitude()));
                            momLine.add(new LatLng(event.getLatitude(), event.getLongitude()));
                            momLine.width(lineWidth).color(setColor(mModel.getTreeColor()));

                            mModel.addLine(momLine, TREE);
                            setTreeLine(mom, event, lineWidth - 5);
                            break outerloop;
                        }
                    }
                }
            }
        }
    }

    private void setSpouseLine(Person person, Event start) {
        Person spouse = null;
        Person[] allPeople = mModel.getAllPeople();

        for (int i = 0; i < allPeople.length; i++) {
            if (person.getSpouse() != null) {
                if (person.getSpouse().equals(allPeople[i].getPersonID())) {
                    spouse = allPeople[i];
                }
            }
        }

        if (spouse != null) {
            Set<Event> spouseEvents = spouse.getLifeEvents();
            Iterator<Event> it = spouseEvents.iterator();

            if (it.hasNext()) {
                Event spouseBirth = it.next();
                PolylineOptions spouseLine = new PolylineOptions();
                spouseLine.add(new LatLng(start.getLatitude(), start.getLongitude()));
                spouseLine.add(new LatLng(spouseBirth.getLatitude(), spouseBirth.getLongitude()));
                spouseLine.width(20).color(setColor(mModel.getSpouseColor()));

                mModel.addLine(spouseLine, SPOUSE);
            }
        }
    }

    public boolean markerClick(Marker marker){
        Iterator<Marker> it = mModel.getAllMarkers().iterator();
        Iterator<Polyline> iterator = mModel.getAllLines().iterator();
        mModel.setCurrentMarker(marker);

        while (iterator.hasNext()){
            iterator.next().remove();
        }
        mModel.getAllLines().clear();

        if (mModel.isCenter()) {
            mModel.getMap().animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        }

        while (it.hasNext()) {
            Marker markerCheck = it.next();
            if (mModel.getCurrentEvent() != null) {
                if (marker.getTag() == null) {
                    if (mModel.getCurrentEvent().getEventID().equals(((Event) markerCheck.getTag()).getEventID())) {
                        if (mModel.isLifeView()) {
                            setLifeLine(mModel.getCurrentPerson());
                        }
                        if (mModel.isTreeView()) {
                            setTreeLine(mModel.getCurrentPerson(), mModel.getCurrentEvent(), 20);
                        }
                        if (mModel.isSpouseView()) {
                            setSpouseLine(mModel.getCurrentPerson(), mModel.getCurrentEvent());
                        }

                        return true;
                    }
                }
                else {
                    if (((Event)marker.getTag()).getEventID().equals(((Event)markerCheck.getTag()).getEventID())) {
                        mModel.setCurrentEvent((Event) markerCheck.getTag());
                        mModel.setCurrentPerson(getEventPerson(mModel.getCurrentEvent().getPersonID()));

                        if (mModel.getCurrentPerson().getGender().equals("m")) {
                            setIcon(MainActivity.MALE_ICON);
                        } else {
                            setIcon(MainActivity.FEMALE_ICON);
                        }

                        if (mModel.isLifeView()) {
                            setLifeLine(mModel.getCurrentPerson());
                        }
                        if (mModel.isTreeView()) {
                            setTreeLine(mModel.getCurrentPerson(), mModel.getCurrentEvent(), 20);
                        }
                        if (mModel.isSpouseView()) {
                            setSpouseLine(mModel.getCurrentPerson(), mModel.getCurrentEvent());
                        }

                        return true;
                    }
                }
            }
            else{
                mModel.setCurrentEvent((Event)marker.getTag());
                mModel.setCurrentPerson(getEventPerson(mModel.getCurrentEvent().getPersonID()));

                if (mModel.getCurrentPerson().getGender().equals("m")) {
                    setIcon(MainActivity.MALE_ICON);
                } else {
                    setIcon(MainActivity.FEMALE_ICON);
                }
                return true;
            }
        }
        return false;
    }

    public void setPerson(){
        mModel.setCurrentPerson(findPerson(mModel.getCurrentEvent().getPersonID()));
        if (mModel.getCurrentPerson().getGender().equals("m")) {
            mModel.setGenderIcon(setIcon(MainActivity.MALE_ICON));
        }
        else{
            mModel.setGenderIcon(setIcon(MainActivity.FEMALE_ICON));
        }
    }

    public void updateLines(){
        if (mModel.getCurrentMarker() != null) {
            markerClick(mModel.getCurrentMarker());
        }
    }

    private void filterEvent(String eventType, List<Event> filteredEvents){
        Iterator<Event> it = filteredEvents.iterator();
        while (it.hasNext()){
            Event event = it.next();
            if(event.getEventType().toLowerCase().equals(eventType)){
                filteredEvents.remove(event);
                it = filteredEvents.iterator();
            }
        }
    }

    private Person findPerson(String id){
        Person[] allPeople = mModel.getAllPeople();
        if (id == null){
            for (Person person : allPeople){
                if (person.getPersonID().equals(person.getDescendant())){
                    return person;
                }
            }
        }
        else {
            for (Person person : allPeople){
                if (person.getPersonID().equals(id)){
                    return person;
                }
            }
        }
        return null;
    }

    private void filterGenerations(Person ancestor, List<Event> filteredEvents){
        Iterator<Event> it = filteredEvents.iterator();
        while (it.hasNext()){
            Event event = it.next();
            if (event.getPersonID().equals(ancestor.getPersonID())) {
                filteredEvents.remove(event);
                it = filteredEvents.iterator();
            }
        }

        if (ancestor.getFather() != null){
            filterGenerations(findPerson(ancestor.getFather()), filteredEvents);
        }
        if (ancestor.getMother() != null){
            filterGenerations(findPerson(ancestor.getMother()), filteredEvents);
        }
    }

    private void filterSide(String side, List<Event> filteredEvents){
        Person user = findPerson(null);
        if (user != null) {
            if (side.equals("dad")) {
                filterGenerations(findPerson(user.getFather()), filteredEvents);
            } else if (side.equals("mom")) {
                filterGenerations(findPerson(user.getMother()), filteredEvents);
            }
        }
    }

    private void filterGender(String gender, List<Event> filteredEvents) {
        Iterator<Event> it = filteredEvents.iterator();
        while(it.hasNext()) {
            Event event = it.next();
            Person person = findPerson(event.getPersonID());
            if (person != null) {
                if (person.getGender().equals(gender)) {
                    filteredEvents.remove(event);
                    it = filteredEvents.iterator();
                }
            }
        }
    }

    public void filter(){
        FilterViewModel[] filters = mModel.getFilterViews();
        List<Event> filteredEvents = new ArrayList<>();
        filteredEvents.addAll(Arrays.asList(mModel.getAllEvents()));

        if (filters != null) {
            for (int i = 0; i < filters.length - 4; i++) {
                if (!filters[i].isSelected()) {
                    filterEvent(filters[i].getEventType().toLowerCase(), filteredEvents);
                }
            }

            if (!filters[filters.length - 1].isSelected()){
                filterSide("dad", filteredEvents);
            }
            if (!filters[filters.length - 2].isSelected()){
                filterSide("mom", filteredEvents);
            }
            if (!filters[filters.length - 3].isSelected()){
                filterGender("f", filteredEvents);
            }
            if (!filters[filters.length - 4].isSelected()){
                filterGender("m", filteredEvents);
            }
        }
        mModel.setFilteredEvents((ArrayList<Event>)filteredEvents);
    }

    public void setMapType(int type){
        if (type == SettingsActivity.MAP_TYPE.NORMAL.getType()){
            mModel.setMapType(NORMAL_MAP);
            mModel.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else if (type == SettingsActivity.MAP_TYPE.SATELLITE.getType()){
            mModel.setMapType(SATELLITE_MAP);
            mModel.getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if (type == SettingsActivity.MAP_TYPE.HYBRID.getType()){
            mModel.setMapType(HYBRID_MAP);
            mModel.getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else if (type == SettingsActivity.MAP_TYPE.TERRAIN.getType()){
            mModel.setMapType(TERRAIN_MAP);
            mModel.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        else {
            mModel.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public GoogleMap getMap(){
        return mModel.getMap();
    }

    public String getPersonDetails(){
        return mModel.getPersonDetails();
    }

    public String getEventDetails(){
        return mModel.getEventDetails();
    }

    public Drawable getGenderIcon(){
        return mModel.getGenderIcon();
    }

    public void setMap(GoogleMap map){
        mModel.setMap(map);
    }

    public Person getCurrentPerson(){
        return mModel.getCurrentPerson();
    }

    public Event getCurrentEvent(){
        return mModel.getCurrentEvent();
    }

    public UserInfo getUserInfo(){
        return mModel.getCurrentUser();
    }

    public MapModel getModel(){
        return mModel;
    }

    public Event[] getAllEvents(){
        return mModel.getAllEvents();
    }

    public Person[] getAllPeople(){
        return mModel.getAllPeople();
    }

    public void setFilterModel(FilterViewModel[] viewModel){
        mModel.setFilterViews(viewModel);
    }

    public FilterViewModel[] getViewModel(){
        return mModel.getFilterViews();
    }

    public HashMap<String, String> getChildren(){
        return mModel.getChildren();
    }

    public ImportantInfo getMapInfo(){
        ImportantInfo mapInfo = new ImportantInfo();
        mapInfo.setFilteredEvents((ArrayList)mModel.getFilteredEvents());
        mapInfo.setLifeView(mModel.isLifeView());
        mapInfo.setLifeColor(mModel.getLifeColor());
        mapInfo.setTreeView(mModel.isTreeView());
        mapInfo.setTreeColor(mModel.getTreeColor());
        mapInfo.setSpouseView(mModel.isSpouseView());
        mapInfo.setSpouseColor(mModel.getSpouseColor());
        mapInfo.setMapType(mModel.getMapType());

        return mapInfo;
    }
}
