package com.example.video.familymapclient.Activities.Person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.video.familymapclient.Activities.Icons;
import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapsFragment;
import com.example.video.familymapclient.Activities.MapActivity.MapActivity;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonActivity extends AppCompatActivity {
    public static final String CURR_EVENT = "currentEvent";

    private Person mPerson;
    private ExpandableListView mExList;
    private ExListAdapter mExListAdapter;
    private PersonController mController;

    private TextView firstName;
    private TextView lastName;
    private TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mController = new PersonController(this);

        final Intent intent = getIntent();
        mPerson = (Person)intent.getSerializableExtra(MainActivity.PERSON_KEY);

        firstName = (TextView)findViewById(R.id.person_first);
        firstName.setText(mPerson.getFirstName());

        lastName = (TextView)findViewById(R.id.person_last);
        lastName.setText(mPerson.getLastName());

        gender = (TextView)findViewById(R.id.person_gender);
        gender.setText(mController.getGender(mPerson.getGender()));

        mExList = (ExpandableListView)findViewById(R.id.person_exlist);

        List<String> headers = mController.initializeListHeaders();

        mExListAdapter = new ExListAdapter(this, headers,
                mController.initializeListChildren(headers, mPerson, (Person[])intent.getSerializableExtra(MainActivity.ALL_PEOPLE),
                        (HashMap<String, String>)intent.getSerializableExtra(MainActivity.ALL_CHILDREN),
                        MapsFragment.getFilteredEvents(intent)));
        mExList.setAdapter(mExListAdapter);
        mExList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    Intent eventIntent = new Intent(PersonActivity.this, MapActivity.class);
                    eventIntent.putExtra(MainActivity.ALL_EVENTS, (Event[])intent.getSerializableExtra(MainActivity.ALL_EVENTS));
                    eventIntent.putExtra(CURR_EVENT, ((ExListModel)mExListAdapter.getChild(groupPosition, childPosition)).getEvent());
                    eventIntent.putExtra(MainActivity.ALL_PEOPLE, (Person[])intent.getSerializableExtra(MainActivity.ALL_PEOPLE));
                    eventIntent.putExtra(MapsFragment.MAP_INFO, MapsFragment.getMapModel(intent));
                    eventIntent.putExtra(MapsFragment.USER_INFO, MapsFragment.getUserInfo(intent));
                    eventIntent.putExtra(MapActivity.MAP_ACT, ((ExListModel) mExListAdapter.getChild(groupPosition, childPosition)).getEvent());
                    startActivity(eventIntent);
                    return true;
                }
                else if (groupPosition == 1){
                    Intent personIntent = new Intent(PersonActivity.this, PersonActivity.class);
                    personIntent.putExtra(MainActivity.PERSON_KEY,
                            ((ExListModel)mExListAdapter.getChild(groupPosition, childPosition)).getPerson());
                    personIntent.putExtra(MainActivity.ALL_PEOPLE, intent.getSerializableExtra(MainActivity.ALL_PEOPLE));
                    personIntent.putExtra(MainActivity.ALL_EVENTS, intent.getSerializableExtra(MainActivity.ALL_EVENTS));
                    personIntent.putExtra(MainActivity.ALL_CHILDREN, intent.getSerializableExtra(MainActivity.ALL_CHILDREN));
                    personIntent.putExtra(MapsFragment.MAP_INFO, MapsFragment.getMapModel(intent));
                    personIntent.putExtra(MapsFragment.USER_INFO, MapsFragment.getUserInfo(intent));
                    personIntent.putExtra(MapsFragment.FILTERED_EVENTS, (ArrayList)MapsFragment.getFilteredEvents(intent));
                    startActivity(personIntent);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);
        menu.findItem(R.id.menu_cheveron).setIcon(new Icons(PersonActivity.this).getCheveronIcon());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_cheveron:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
