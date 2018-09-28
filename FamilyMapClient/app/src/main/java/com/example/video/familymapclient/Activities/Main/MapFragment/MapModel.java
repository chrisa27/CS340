package com.example.video.familymapclient.Activities.Main.MapFragment;

import android.graphics.drawable.Drawable;

import com.example.video.familymapclient.Activities.FilterActivity.FilterViewModel;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.Model.UserInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by video on 4/5/2017.
 */

public class MapModel implements Serializable{
    private GoogleMap mMap;
    private HashSet<Marker> mAllMarkers;
    private HashSet<Polyline> mAllLines;
    private HashMap<String, String> mChildren;

    private Marker mCurrentMarker;
    private UserInfo mCurrentUser;
    private ArrayList<Event> mFilteredEvents;
    private Person mCurrentPerson;
    private Event mCurrentEvent;
    private Drawable genderIcon;

    private boolean mLifeView;
    private boolean mTreeView;
    private boolean mSpouseView;
    private boolean mCenter;
    private int mLifeColor;
    private int mTreeColor;
    private int mSpouseColor;
    private int mMapType;
    private FilterViewModel[] filterViews;
    private Set<String> eventTypes;

    public MapModel(UserInfo info){
        mChildren = new HashMap<>();
        mAllMarkers = new HashSet<>();
        mAllLines = new HashSet<>();
        mCurrentUser = info;
        mLifeView = false;
        mTreeView = false;
        mSpouseView = false;
        mLifeColor = 0;
        mTreeColor = 0;
        mSpouseColor = 0;
        mMapType = 0;
        mFilteredEvents = new ArrayList<>();
        mCenter = false;
        eventTypes = new HashSet<>();

        for (int i = 0; i < info.getAllEvents().length; i++){
            mFilteredEvents.add(info.getAllEvents()[i]);
        }
    }

    public void addMarker(MarkerOptions marker, Object tag){
        Marker point;
        point = mMap.addMarker(marker);
        point.setTag(tag);
        mAllMarkers.add(point);
    }

    public void addLine(PolylineOptions polyline, Object tag){
        Polyline line;
        line = mMap.addPolyline(polyline);
        line.setTag(tag);
        mAllLines.add(line);
    }

    public String getEventDetails(){
        return mCurrentEvent.getEventType() + ": " +
                mCurrentEvent.getCity() + ", " + mCurrentEvent.getCountry() +
                " (" + mCurrentEvent.getYear() + ")";
    }

    void initializeActivity(ImportantInfo mapInfo, Event currentEvent){
        mLifeView = mapInfo.isLifeView();
        mLifeColor = mapInfo.getLifeColor();
        mTreeView = mapInfo.isTreeView();
        mTreeColor = mapInfo.getTreeColor();
        mSpouseView = mapInfo.isSpouseView();
        mSpouseColor = mapInfo.getSpouseColor();
        mMapType = mapInfo.getMapType();
        mFilteredEvents = mapInfo.getFilteredEvents();
        mCenter = true;
        mCurrentEvent = currentEvent;
    }

    public String getPersonDetails(){
        return mCurrentPerson.getFirstName() + " " + mCurrentPerson.getLastName();
    }

    public Event[] getAllEvents(){
        return mCurrentUser.getAllEvents();
    }

    public Person[] getAllPeople(){
        return mCurrentUser.getAllPeople();
    }

    public Person getCurrentPerson() {
        return mCurrentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        mCurrentPerson = currentPerson;
    }

    public Event getCurrentEvent() {
        return mCurrentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        mCurrentEvent = currentEvent;
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public void setMap(GoogleMap map) {
        mMap = map;
    }

    public Set<Marker> getAllMarkers() {
        return mAllMarkers;
    }

    public Set<Polyline> getAllLines() {
        return mAllLines;
    }

    public boolean isLifeView() {
        return mLifeView;
    }

    public void setLifeView(boolean lifeView) {
        mLifeView = lifeView;
    }

    public boolean isTreeView() {
        return mTreeView;
    }

    public void setTreeView(boolean treeView) {
        mTreeView = treeView;
    }

    public boolean isSpouseView() {
        return mSpouseView;
    }

    public void setSpouseView(boolean spouseView) {
        mSpouseView = spouseView;
    }

    public Drawable getGenderIcon() {
        return genderIcon;
    }

    public void setGenderIcon(Drawable genderIcon) {
        this.genderIcon = genderIcon;
    }

    public int getLifeColor() {
        return mLifeColor;
    }

    public void setLifeColor(int lifeColor) {
        mLifeColor = lifeColor;
    }

    public int getTreeColor() {
        return mTreeColor;
    }

    public void setTreeColor(int treeColor) {
        mTreeColor = treeColor;
    }

    public int getSpouseColor() {
        return mSpouseColor;
    }

    public void setSpouseColor(int spouseColor) {
        mSpouseColor = spouseColor;
    }

    int getMapType() {
        return mMapType;
    }

    void setMapType(int mapType) {
        mMapType = mapType;
    }

    public UserInfo getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(UserInfo currentUser) {
        mCurrentUser = currentUser;
    }

    public Marker getCurrentMarker() {
        return mCurrentMarker;
    }

    public void setCurrentMarker(Marker currentMarker) {
        mCurrentMarker = currentMarker;
    }

    public FilterViewModel[] getFilterViews() {
        return filterViews;
    }

    public void setFilterViews(FilterViewModel[] filterViews) {
        this.filterViews = filterViews;
    }

    public HashMap<String, String> getChildren(){
        return mChildren;
    }

    public List<Event> getFilteredEvents() {
        return mFilteredEvents;
    }

    public void setFilteredEvents(ArrayList<Event> filteredEvents) {
        mFilteredEvents = filteredEvents;
    }

    public boolean isCenter() {
        return mCenter;
    }

    public void setCenter(boolean center) {
        mCenter = center;
    }

    public Set<String> getEventTypes(){
        return eventTypes;
    }
}
