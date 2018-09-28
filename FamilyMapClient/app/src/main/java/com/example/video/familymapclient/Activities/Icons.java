package com.example.video.familymapclient.Activities;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.video.familymapclient.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class Icons{
    private Drawable androidIcon;
    private Drawable maleIcon;
    private Drawable femaleIcon;
    private Drawable searchIcon;
    private Drawable filterIcon;
    private Drawable settingsIcon;
    private Drawable cheveronIcon;
    private Drawable markerIcon;

    public Icons(Context context) {
        if (context != null) {
            androidIcon = new IconDrawable(context, Iconify.IconValue.fa_android).colorRes(R.color.androidGreen).sizeDp(40);
            maleIcon = new IconDrawable(context, Iconify.IconValue.fa_male).colorRes(R.color.maleIcon).sizeDp(40);
            femaleIcon = new IconDrawable(context, Iconify.IconValue.fa_female).colorRes(R.color.femaleIcon).sizeDp(40);
            searchIcon = new IconDrawable(context, Iconify.IconValue.fa_search).colorRes(android.R.color.white).sizeDp(35);
            filterIcon = new IconDrawable(context, Iconify.IconValue.fa_filter).colorRes(android.R.color.white).sizeDp(35);
            settingsIcon = new IconDrawable(context, Iconify.IconValue.fa_gear).colorRes(android.R.color.white).sizeDp(35);
            cheveronIcon = new IconDrawable(context, Iconify.IconValue.fa_angle_double_up).colorRes(android.R.color.white).sizeDp(35);
            markerIcon = new IconDrawable(context, Iconify.IconValue.fa_map_marker).colorRes(android.R.color.background_dark).sizeDp(40);
        }
    }

    public Drawable getAndroidIcon() {
        return androidIcon;
    }

    public Drawable getMaleIcon() {
        return maleIcon;
    }

    public Drawable getFemaleIcon() {
        return femaleIcon;
    }

    public Drawable getSearchIcon() {
        return searchIcon;
    }

    public Drawable getFilterIcon() {
        return filterIcon;
    }

    public Drawable getSettingsIcon() {
        return settingsIcon;
    }

    public Drawable getCheveronIcon() {
        return cheveronIcon;
    }

    public Drawable getMarkerIcon() {
        return markerIcon;
    }
}
