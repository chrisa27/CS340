package com.example.video.familymapclient.Activities.FilterActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.video.familymapclient.Activities.Main.MapFragment.MapsFragment;
import com.example.video.familymapclient.R;

public class FilterActivity extends AppCompatActivity {
    public static final String SWITCHES = "switches";

    private FilterController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView)findViewById(R.id.filter_recycle);

        mController = new FilterController(MapsFragment.getEventTypes(getIntent()));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (getSwitches(getIntent()) == null) {
            mAdapter = new FilterAdapter(mController.getEventViews());
        }
        else{
            mAdapter = new FilterAdapter(mController.persist(getSwitches(getIntent())));
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra(SWITCHES, mController.getViewModel());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra(SWITCHES, mController.getViewModel());
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static FilterViewModel[] getSwitches(Intent intent){
        return (FilterViewModel[]) intent.getSerializableExtra(SWITCHES);
    }
}
