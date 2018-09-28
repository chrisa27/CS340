package com.example.video.familymapclient.Activities.SettingsActivity;

import com.example.video.familymapclient.Tasks.UserInfoTask;

class SettingsController {
    private SettingsModel mModel;

    SettingsController(SettingsModel model){
        mModel = model;
    }

    void reSync(){
        try {
            mModel.setUserInfo(new UserInfoTask().execute(mModel.getUserInfo().getToken()).get());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
