package com.example.video.familymapclient.Activities.MapActivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapsFragment;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.R;
import com.google.android.gms.maps.GoogleMap;

import java.util.Map;

public class MapActivity extends ActionBarActivity {
    private MapActivityController mController;
    GoogleMap map;
    public static final String MAP_ACT = "mapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        Bundle bundle = new Bundle();
        bundle.putSerializable(MAP_ACT, intent.getSerializableExtra(MAP_ACT));
        bundle.putSerializable(MapsFragment.MAP_INFO, MapsFragment.getMapModel(intent));
        bundle.putSerializable(MapsFragment.USER_INFO, MapsFragment.getUserInfo(intent));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new MapsFragment();
        fragment.setArguments(bundle);

        fm.beginTransaction().add(R.id.map_container, fragment).commit();
    }
}
