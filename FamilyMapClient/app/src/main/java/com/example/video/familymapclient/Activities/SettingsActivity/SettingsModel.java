package com.example.video.familymapclient.Activities.SettingsActivity;

import android.content.Intent;

import com.example.video.familymapclient.Model.UserInfo;

class SettingsModel{
    private UserInfo mUserInfo;
    private boolean mLifeOn;
    private boolean mTreeOn;
    private boolean mSpouseOn;
    private int mLifeColor;
    private int mTreeColor;
    private int mSpouseColor;
    private int mMapType;

    SettingsModel(Intent input){
        mLifeOn = input.getBooleanExtra(SettingsActivity.LIFE_SWITCH, false);
        mTreeOn = input.getBooleanExtra(SettingsActivity.TREE_SWITCH, false);
        mSpouseOn = input.getBooleanExtra(SettingsActivity.SPOUSE_SWITCH, false);
        mLifeColor = input.getIntExtra(SettingsActivity.LIFE_COLOR, 0);
        mTreeColor = input.getIntExtra(SettingsActivity.TREE_COLOR, 0);
        mSpouseColor = input.getIntExtra(SettingsActivity.SPOUSE_COLOR, 0);
        mMapType = input.getIntExtra(SettingsActivity.MAP, 0);
        mUserInfo = (UserInfo) input.getSerializableExtra(SettingsActivity.USER_INFO);
    }

    UserInfo getUserInfo() {
        return mUserInfo;
    }

    void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    boolean isLifeOn() {
        return mLifeOn;
    }

    void setLifeOn(boolean lifeOn) {
        mLifeOn = lifeOn;
    }

    boolean isTreeOn() {
        return mTreeOn;
    }

    void setTreeOn(boolean treeOn) {
        mTreeOn = treeOn;
    }

    boolean isSpouseOn() {
        return mSpouseOn;
    }

    void setSpouseOn(boolean spouseOn) {
        mSpouseOn = spouseOn;
    }

    int getLifeColor() {
        return mLifeColor;
    }

    void setLifeColor(int lifeColor) {
        mLifeColor = lifeColor;
    }

    int getTreeColor() {
        return mTreeColor;
    }

    void setTreeColor(int treeColor) {
        mTreeColor = treeColor;
    }

    int getSpouseColor() {
        return mSpouseColor;
    }

    void setSpouseColor(int spouseColor) {
        mSpouseColor = spouseColor;
    }

    int getMapType() {
        return mMapType;
    }

    void setMapType(int mapType) {
        mMapType = mapType;
    }
}
