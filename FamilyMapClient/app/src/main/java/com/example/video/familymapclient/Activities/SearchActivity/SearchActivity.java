package com.example.video.familymapclient.Activities.SearchActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapsFragment;
import com.example.video.familymapclient.R;

import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchBar;
    private SearchController mController;
    private RecyclerView searchResults;
    private SearchAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        mController = new SearchController(MapsFragment.getAllPeople(intent),
                MapsFragment.getFilteredEvents(intent));

        searchBar = (SearchView)findViewById(R.id.search);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mController.updateSearch(query, adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mController.updateSearch(newText, adapter);
                return true;
            }
        });

        searchResults = (RecyclerView)findViewById(R.id.search_recycler);

        layoutManager = new LinearLayoutManager(this);
        searchResults.setLayoutManager(layoutManager);

        adapter = new SearchAdapter(this, MapsFragment.getAllPeople(intent),
                (Map<String, String>)intent.getSerializableExtra(MainActivity.ALL_CHILDREN), intent);
        searchResults.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
