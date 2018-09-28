package com.example.video.familymapclient.Activities.SettingsActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Model.UserInfo;
import com.example.video.familymapclient.R;

public class SettingsActivity extends AppCompatActivity {
    public static final String LIFE_SWITCH = "lifeSwitch";
    public static final String TREE_SWITCH = "treeSwitch";
    public static final String SPOUSE_SWITCH = "spouseSwitch";
    public static final String LIFE_COLOR = "lifeColor";
    public static final String TREE_COLOR = "treeColor";
    public static final String SPOUSE_COLOR = "spouseColor";
    public static final String MAP = "mapType";
    public static final String USER_INFO = "userInfo";
    public static final String SYNC = "sync";

    public enum LINE_COLORS {
        RED(0),
        BLUE(1),
        YELLOW(2),
        GREEN(3);

        private int color;
        LINE_COLORS(int col){
            color = col;
        }

        public int getColor(){
            return color;
        }
    }

    public enum MAP_TYPE {
        NORMAL(0),
        HYBRID(1),
        SATELLITE(2),
        TERRAIN(3);

        private int type;
        MAP_TYPE(int map){
            type = map;
        }

        public int getType(){
            return type;
        }
    }

    private SettingsController mController;
    private SettingsModel mModel;

    private Switch mLifeSwitch;
    private Switch mTreeSwitch;
    private Switch mSpouseSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner lifeSpinner;
        Spinner treeSpinner;
        Spinner spouseSpinner;
        Spinner mapSpinner;
        LinearLayout syncButton;
        LinearLayout logoutButton;

        mModel = new SettingsModel(getIntent());
        mController = new SettingsController(mModel);

        ArrayAdapter<CharSequence> colorAdapter;
        colorAdapter = ArrayAdapter.createFromResource(this, R.array.settings_colors, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> mapAdapter;
        mapAdapter = ArrayAdapter.createFromResource(this, R.array.settings_map_types, android.R.layout.simple_spinner_item);
        mapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mLifeSwitch = (Switch)this.findViewById(R.id.settings_life_switch);
        mLifeSwitch.setChecked(mModel.isLifeOn());
        mLifeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLifeSwitch.isChecked()){
                    mModel.setLifeOn(true);
                }
                else{
                    mModel.setLifeOn(false);
                }
            }
        });

        mTreeSwitch = (Switch)this.findViewById(R.id.settings_tree_switch);
        mTreeSwitch.setChecked(mModel.isTreeOn());
        mTreeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTreeSwitch.isChecked()){
                    mModel.setTreeOn(true);
                }
                else{
                    mModel.setTreeOn(false);
                }
            }
        });

        mSpouseSwitch = (Switch)this.findViewById(R.id.settings_spouse_switch);
        mSpouseSwitch.setChecked(mModel.isSpouseOn());
        mSpouseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpouseSwitch.isChecked()){
                    mModel.setSpouseOn(true);
                }
                else{
                    mModel.setSpouseOn(false);
                }
            }
        });

        lifeSpinner = (Spinner)this.findViewById(R.id.settings_life_spinner);
        lifeSpinner.setAdapter(colorAdapter);
        lifeSpinner.setSelection(mModel.getLifeColor());
        lifeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModel.setLifeColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        treeSpinner = (Spinner)this.findViewById(R.id.settings_tree_spinner);
        treeSpinner.setAdapter(colorAdapter);
        treeSpinner.setSelection(mModel.getTreeColor());
        treeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModel.setTreeColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spouseSpinner = (Spinner)this.findViewById(R.id.settings_spouse_spinner);
        spouseSpinner.setAdapter(colorAdapter);
        spouseSpinner.setSelection(mModel.getSpouseColor());
        spouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModel.setSpouseColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mapSpinner = (Spinner)this.findViewById(R.id.settings_map_spinner);
        mapSpinner.setAdapter(mapAdapter);
        mapSpinner.setSelection(mModel.getMapType());
        mapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModel.setMapType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        syncButton = (LinearLayout)this.findViewById(R.id.settings_sync_button);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.reSync();
                Intent reSync = new Intent();
                reSync.putExtra(USER_INFO, mModel.getUserInfo());
                reSync.putExtra(SYNC, true);
                setResult(RESULT_OK, reSync);
                finish();
            }
        });

        logoutButton = (LinearLayout)this.findViewById(R.id.settings_logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra(LIFE_SWITCH, mModel.isLifeOn());
        intent.putExtra(TREE_SWITCH, mModel.isTreeOn());
        intent.putExtra(SPOUSE_SWITCH, mModel.isSpouseOn());
        intent.putExtra(LIFE_COLOR, mModel.getLifeColor());
        intent.putExtra(TREE_COLOR, mModel.getTreeColor());
        intent.putExtra(SPOUSE_COLOR, mModel.getSpouseColor());
        intent.putExtra(MAP, mModel.getMapType());
        intent.putExtra(USER_INFO, mModel.getUserInfo());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra(LIFE_SWITCH, mModel.isLifeOn());
                intent.putExtra(TREE_SWITCH, mModel.isTreeOn());
                intent.putExtra(SPOUSE_SWITCH, mModel.isSpouseOn());
                intent.putExtra(LIFE_COLOR, mModel.getLifeColor());
                intent.putExtra(TREE_COLOR, mModel.getTreeColor());
                intent.putExtra(SPOUSE_COLOR, mModel.getSpouseColor());
                intent.putExtra(MAP, mModel.getMapType());
                intent.putExtra(USER_INFO, mModel.getUserInfo());
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean wasLifeSet(Intent result){
        return result.getBooleanExtra(LIFE_SWITCH, false);
    }

    public static boolean wasTreeSet(Intent result){
        return result.getBooleanExtra(TREE_SWITCH, false);
    }

    public static boolean wasSpouseSet(Intent result){
        return result.getBooleanExtra(SPOUSE_SWITCH, false);
    }

    public static int getLifeColor(Intent result){
        return result.getIntExtra(LIFE_COLOR, -1);
    }

    public static int getTreeColor(Intent result){
        return result.getIntExtra(TREE_COLOR, -1);
    }

    public static int getSpouseColor(Intent result){
        return result.getIntExtra(SPOUSE_COLOR, -1);
    }

    public static int getMapType(Intent result){
        return result.getIntExtra(MAP, -1);
    }

    public static UserInfo getUserInfo(Intent result){
        return (UserInfo)result.getSerializableExtra(USER_INFO);
    }

    public static boolean isSync(Intent result){
        return result.getBooleanExtra(SYNC, false);
    }
}
