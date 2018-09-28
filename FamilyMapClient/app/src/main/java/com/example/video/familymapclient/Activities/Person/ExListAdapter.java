package com.example.video.familymapclient.Activities.Person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.video.familymapclient.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by video on 4/11/2017.
 */

public class ExListAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<String> headers;
    HashMap<String, List<ExListModel>> childData;


    public ExListAdapter(Context context, List<String> headers, HashMap<String, List<ExListModel>> childData) {
        mContext = context;
        this.headers = headers;
        this.childData = childData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return childData.get(getGroup(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ExListModel model = (ExListModel)getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater childInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childInflater.inflate(R.layout.exlist_child, null);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.child_icon);
        TextView mainText = (TextView)convertView.findViewById(R.id.child_main);
        TextView subText = (TextView)convertView.findViewById(R.id.child_sub);

        icon.setImageDrawable(model.getIcon());
        mainText.setText(model.getMainText());
        subText.setText(model.getSubText());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater groupInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = groupInflater.inflate(R.layout.exlist_group, null);
        }
        TextView headerText = (TextView)convertView.findViewById(R.id.group_title);
        headerText.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
