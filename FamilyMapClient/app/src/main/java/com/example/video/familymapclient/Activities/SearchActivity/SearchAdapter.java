package com.example.video.familymapclient.Activities.SearchActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.video.familymapclient.Activities.Icons;
import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Activities.Main.MapFragment.MapsFragment;
import com.example.video.familymapclient.Activities.MapActivity.MapActivity;
import com.example.video.familymapclient.Activities.Person.PersonActivity;
import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.Person;
import com.example.video.familymapclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by video on 4/15/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<SearchRecycleModel> items;
    Icons icons;
    static Person[] allPeople;
    static Context mContext;
    static Map<String, String> allChildren;
    private static Intent initialIntent;

    public SearchAdapter(Context context, Person[] allPeople, Map<String,
            String> allChildren, Intent initialIntent){
        icons = new Icons(context);
        items = new ArrayList<>();
        this.allPeople = allPeople;
        mContext = context;
        this.allChildren = allChildren;
        this.initialIntent = initialIntent;
    }

    private Person findPerson(String id){
        for (int i = 0; i < allPeople.length; i++){
            if (id.equals(allPeople[i].getPersonID())){
                return allPeople[i];
            }
        }
        return null;
    }

    public String getEventDetails(Event event){
        return event.getEventType() + ": " +
                event.getCity() + ", " + event.getCountry() +
                " (" + event.getYear() + ")";
    }

    public String getPersonDetails(Person person){
        return person.getFirstName() + " " + person.getLastName();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exlist_child, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        SearchRecycleModel item = items.get(position);
        holder.person = null;
        holder.event = null;
        if (item.getGivenPerson()!= null){
            Person person = item.getGivenPerson();
            holder.subText.setText("");
            if (person.getGender().equals("m")){
                holder.icon.setImageDrawable(icons.getMaleIcon());
            }
            else{
                holder.icon.setImageDrawable(icons.getFemaleIcon());
            }
            holder.mainText.setText(getPersonDetails(person));
            holder.setPerson(item.getGivenPerson());
        }
        else if (item.getGivenEvent() != null){
            Event event = item.getGivenEvent();
            Person person = findPerson(event.getPersonID());
            holder.icon.setImageDrawable(icons.getMarkerIcon());
            holder.mainText.setText(getEventDetails(event));
            holder.subText.setText(getPersonDetails(person));
            holder.setEvent(item.getGivenEvent());
        }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public void setItems(List<SearchRecycleModel> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Person person;
        Event event;
        public ImageView icon;
        public TextView mainText;
        public TextView subText;

        public ViewHolder(View itemView){
            super(itemView);
            icon = (ImageView)itemView.findViewById(R.id.child_icon);
            mainText = (TextView)itemView.findViewById(R.id.child_main);
            subText = (TextView)itemView.findViewById(R.id.child_sub);
            itemView.setOnClickListener(this);
            person = null;
            event = null;
        }

        public void setPerson(Person person){
            this.person = person;
        }

        public void setEvent (Event event){
            this.event = event;
        }

        @Override
        public void onClick(View v){
            Intent intent;
            if(person != null){
                intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra(MainActivity.PERSON_KEY, person);
                intent.putExtra(MainActivity.ALL_CHILDREN, (HashMap<String, String>)allChildren);
                intent.putExtra(MainActivity.ALL_PEOPLE, allPeople);
                intent.putExtra(MapsFragment.MAP_INFO, MapsFragment.getMapModel(initialIntent));
                intent.putExtra(MapsFragment.USER_INFO, MapsFragment.getUserInfo(initialIntent));
                intent.putExtra(MapsFragment.FILTERED_EVENTS, (ArrayList)MapsFragment.getFilteredEvents(initialIntent));
                mContext.startActivity(intent);
            }
            else{
                intent = new Intent(mContext, MapActivity.class);
                intent.putExtra(MapActivity.MAP_ACT, event);
                intent.putExtra(MapsFragment.MAP_INFO, MapsFragment.getMapModel(initialIntent));
                intent.putExtra(MapsFragment.USER_INFO, MapsFragment.getUserInfo(initialIntent));
                mContext.startActivity(intent);
            }
        }
    }
}
