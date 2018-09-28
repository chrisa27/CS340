package com.example.video.familymapclient.Activities.FilterActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.video.familymapclient.R;

import java.util.List;

class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>{
    private List<FilterViewModel> items;

    FilterAdapter(List<FilterViewModel> items){
        this.items = items;
    }

    private String convertMainText(String eventType){
        switch (eventType){
            case "Mother's:":
                return eventType + " Side";
            case "Father's":
                return eventType + " Side";
            default:
                return eventType + " Events";
        }
    }

    private String convertSubText(String eventType) {
        switch (eventType) {
            case "Male":
                return "Filter events based on gender";
            case "Female":
                return "Filter events based on gender";
            case "Mother's:":
                return "Filter by " + eventType + " side of family";
            case "Father's":
                return "Filter by " + eventType + " side of family";
            default:
                return "Filter by " + eventType + " events";
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_filter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String item = items.get(position).getEventType();
        holder.filterText.setText(convertMainText(item));
        holder.filterSubText.setText(convertSubText(item));
        if (!items.get(position).isSelected()){
            holder.enabler.setChecked(false);
        }
        else{
            holder.enabler.setChecked(true);
        }
        holder.enabler.setTag(items.get(position));

        holder.enabler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterViewModel model = (FilterViewModel)v.getTag();
                model.setSelected(((Switch) v).isChecked());
            }
        });

        holder.itemView.setTag(item);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView filterText;
        TextView filterSubText;
        Switch enabler;

        ViewHolder(View itemView){
            super(itemView);
            filterText = (TextView)itemView.findViewById(R.id.filter_maintext);
            filterSubText = (TextView)itemView.findViewById(R.id.filter_subtext);
            enabler = (Switch)itemView.findViewById((R.id.filter_switch));
        }
    }
}
