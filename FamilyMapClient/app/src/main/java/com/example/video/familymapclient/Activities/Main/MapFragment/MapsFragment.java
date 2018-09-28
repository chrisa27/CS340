package com.example.video.familymapclient.Activities.Main.MapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.video.familymapclient.Activities.FilterActivity.FilterActivity;
import com.example.video.familymapclient.Activities.FilterActivity.FilterModel;
import com.example.video.familymapclient.Activities.Icons;
import com.example.video.familymapclient.Activities.Main.LoginFragment.LoginFragment;
import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.MapActivity.MapActivity;
import com.example.video.familymapclient.Activities.Person.PersonActivity;
import com.example.video.familymapclient.Activities.SearchActivity.SearchActivity;
import com.example.video.familymapclient.Activities.SettingsActivity.SettingsActivity;
import com.example.video.familymapclient.Activities.FilterActivity.FilterViewModel;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.Model.UserInfo;
import com.example.video.familymapclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    public static final String EVENT_TYPES = "eventTypes";
    public static final String FILTERED_EVENTS = "filteredEvents";
    public static final String ALL_PEOPLE = "allPeople";
    public static final String MAP_INFO = "mapInfo";
    public static final String USER_INFO = "userInfo";

    private MapController mController;
    private MapModel mModel;

    private TextView mPersonName;
    private TextView mEventInfo;
    private ImageView mGenderIcon;
    private RelativeLayout mMapButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        if (getArguments().getSerializable(MAP_INFO) != null){
            Bundle bundle = getArguments();

            mModel = new MapModel(LoginFragment.getUserInfo(bundle));
            mModel.initializeActivity((ImportantInfo)bundle.getSerializable(MAP_INFO), (Event)bundle.getSerializable(MapActivity.MAP_ACT));
        }
        else {
            mModel = new MapModel(LoginFragment.getUserInfo(getArguments()));
        }
        mController = new MapController(getActivity(), mModel);

        mPersonName = (TextView) v.findViewById(R.id.map_fragment_name);
        mEventInfo = (TextView) v.findViewById(R.id.map_fragment_event);
        mGenderIcon = (ImageView) v.findViewById(R.id.map_fragment_icon);

        mGenderIcon.setImageDrawable(mController.setIcon(MainActivity.ANDROID_ICON));

        SupportMapFragment supportMapFragment;
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {
                        mController.setMap(googleMap);
                        mController.initializeMap();
                        if(mModel.isCenter()){
                            mController.setPerson();
                            mPersonName.setText(mController.getPersonDetails());
                            mEventInfo.setText(mController.getEventDetails());
                            mGenderIcon.setImageDrawable(mController.getGenderIcon());
                        }
                        mController.checkInput(getArguments());


                        mController.getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                              boolean rValue = mController.markerClick(marker);
                                mPersonName.setText(mController.getPersonDetails());
                                mEventInfo.setText(mController.getEventDetails());
                                mGenderIcon.setImageDrawable(mController.getGenderIcon());
                                return rValue;
                            }
                        });
                    }
                }
            });
        }

        mMapButton = (RelativeLayout) v.findViewById(R.id.map_fragment_button);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mController.getCurrentPerson() != null && mController.getCurrentEvent() != null) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra(MainActivity.PERSON_KEY, mController.getCurrentPerson());
                    intent.putExtra(MainActivity.ALL_PEOPLE, mController.getAllPeople());
                    intent.putExtra(MainActivity.ALL_EVENTS, mController.getAllEvents());
                    intent.putExtra(MainActivity.ALL_CHILDREN, mController.getChildren());
                    intent.putExtra(FILTERED_EVENTS, (ArrayList)mModel.getFilteredEvents());
                    intent.putExtra(MAP_INFO, mController.getMapInfo());
                    intent.putExtra(USER_INFO, mModel.getCurrentUser());
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        if (getArguments().getSerializable(MAP_INFO) != null) {
            inflater.inflate(R.menu.top_menu, menu);
            menu.findItem(R.id.menu_cheveron).setIcon(new Icons(getContext()).getCheveronIcon());
        }
        else {
            inflater.inflate(R.menu.map_fragment_menu, menu);
            menu.findItem(R.id.map_menu_search).setIcon(mController.setIcon(MainActivity.SEARCH_ICON));
            menu.findItem(R.id.map_menu_filter).setIcon(mController.setIcon(MainActivity.FILTER_ICON));
            menu.findItem(R.id.map_menu_settings).setIcon(mController.setIcon(MainActivity.SETTINGS_ICON));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.map_menu_search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                searchIntent.putExtra(FILTERED_EVENTS, (ArrayList<Event>)mModel.getFilteredEvents());
                searchIntent.putExtra(ALL_PEOPLE, mModel.getAllPeople());
                searchIntent.putExtra(MainActivity.ALL_CHILDREN, mController.getChildren());
                searchIntent.putExtra(MAP_INFO, mController.getMapInfo());
                searchIntent.putExtra(USER_INFO, mModel.getCurrentUser());
                searchIntent.putExtra(FILTERED_EVENTS, (ArrayList)mModel.getFilteredEvents());
                startActivity(searchIntent);
                return true;

            case R.id.map_menu_filter:
                Intent filterIntent = new Intent(getActivity(), FilterActivity.class);
                filterIntent.putExtra(EVENT_TYPES, mController.getAllEvents());
                filterIntent.putExtra(FilterActivity.SWITCHES, mController.getViewModel());
                startActivityForResult(filterIntent, MapController.REQUEST_CODE_FILTER);
                return true;

            case R.id.map_menu_settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                settingsIntent.putExtra(SettingsActivity.LIFE_SWITCH, mModel.isLifeView());
                settingsIntent.putExtra(SettingsActivity.TREE_SWITCH, mModel.isTreeView());
                settingsIntent.putExtra(SettingsActivity.SPOUSE_SWITCH, mModel.isSpouseView());
                settingsIntent.putExtra(SettingsActivity.LIFE_COLOR, mModel.getLifeColor());
                settingsIntent.putExtra(SettingsActivity.TREE_COLOR, mModel.getTreeColor());
                settingsIntent.putExtra(SettingsActivity.SPOUSE_COLOR, mModel.getSpouseColor());
                settingsIntent.putExtra(SettingsActivity.MAP, mModel.getMapType());
                settingsIntent.putExtra(SettingsActivity.USER_INFO, mModel.getCurrentUser());
                startActivityForResult(settingsIntent, MapController.REQUEST_CODE_SETTINGS);
                return true;
            case R.id.menu_cheveron:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch(requestCode){
            case MapController.REQUEST_CODE_FILTER:
                mModel.setFilterViews(FilterActivity.getSwitches(intent));
                mController.filter();
                mController.initializeMap();
                mController.updateLines();
                break;
            case MapController.REQUEST_CODE_SETTINGS:
                if (SettingsActivity.isSync(intent)){
                    mModel.setCurrentUser(SettingsActivity.getUserInfo(intent));
                    mController.filter();
                    mController.initializeMap();
                    mController.updateLines();
                    mPersonName.setText(R.string.map_fragment_name);
                    mEventInfo.setText(R.string.map_fragment_event);
                    mGenderIcon.setImageDrawable(mController.setIcon(MainActivity.ANDROID_ICON));
                }
                else {
                    mModel.setLifeView(SettingsActivity.wasLifeSet(intent));
                    mModel.setTreeView(SettingsActivity.wasTreeSet(intent));
                    mModel.setSpouseView(SettingsActivity.wasSpouseSet(intent));
                    mModel.setLifeColor(SettingsActivity.getLifeColor(intent));
                    mModel.setTreeColor(SettingsActivity.getTreeColor(intent));
                    mModel.setSpouseColor(SettingsActivity.getSpouseColor(intent));
                    mController.setMapType(SettingsActivity.getMapType(intent));

                    mController.filter();
                    mController.initializeMap();
                    mController.updateLines();
                }
                break;
        }
    }

    public static Event[] getEventTypes(Intent intent){
        return (Event[])intent.getSerializableExtra(EVENT_TYPES);
    }

    public static List<Event> getFilteredEvents(Intent intent){
        return (List<Event>)intent.getSerializableExtra(FILTERED_EVENTS);
    }

    public static Person[] getAllPeople(Intent intent){
        return (Person[])intent.getSerializableExtra(ALL_PEOPLE);
    }

    public static ImportantInfo getMapModel(Intent intent){
        return (ImportantInfo) intent.getSerializableExtra(MAP_INFO);
    }

    public static UserInfo getUserInfo(Intent intent){
        return (UserInfo) intent.getSerializableExtra(USER_INFO);
    }
}