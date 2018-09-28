package com.example.video.familymapclient.Activities.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.video.familymapclient.Activities.Main.LoginFragment.LoginFragment;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapsFragment;
import com.example.video.familymapclient.R;

public class MainActivity extends ActionBarActivity {
    public static final String PEOPLE = "people";
    public static final String EVENT = "event";
    public static final String PERSON_KEY = "mapPerson";
    public static final String ALL_EVENTS = "allEvents";
    public static final String CHEVRON_ICON = "chevronIcon";
    public static final String ANDROID_ICON = "androidIcon";
    public static final String MALE_ICON = "maleIcon";
    public static final String FEMALE_ICON = "femaleIcon";
    public static final String SEARCH_ICON = "searchIcon";
    public static final String FILTER_ICON = "filterIcon";
    public static final String SETTINGS_ICON = "settingsIcon";
    public static final String ALL_PEOPLE = "allPeople";
    public static final String ALL_CHILDREN = "allChildren";

    private static FragmentManager fm;
    private static Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void createMap(Bundle bundle){
        fragment = new MapsFragment();
        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
