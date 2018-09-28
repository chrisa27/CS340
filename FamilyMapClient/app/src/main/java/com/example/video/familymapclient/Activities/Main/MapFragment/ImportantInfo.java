package com.example.video.familymapclient.Activities.Main.MapFragment;

import com.example.video.familymapclient.Activities.FilterActivity.FilterViewModel;
import com.example.video.familymapclient.Model.DatabaseModel.Event;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by video on 4/16/2017.
 */

public class ImportantInfo implements Serializable {
    private boolean mLifeView;
    private boolean mTreeView;
    private boolean mSpouseView;
    private boolean mCenter;
    private int mLifeColor;
    private int mTreeColor;
    private int mSpouseColor;
    private int mMapType;
    private ArrayList<Event> filteredEvents;

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

    public boolean isCenter() {
        return mCenter;
    }

    public void setCenter(boolean center) {
        mCenter = center;
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

    public int getMapType() {
        return mMapType;
    }

    public void setMapType(int mapType) {
        mMapType = mapType;
    }

    public ArrayList<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(ArrayList<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }
}
