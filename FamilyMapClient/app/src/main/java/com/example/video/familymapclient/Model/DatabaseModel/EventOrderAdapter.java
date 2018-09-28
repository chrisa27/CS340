package com.example.video.familymapclient.Model.DatabaseModel;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by video on 4/4/2017.
 */

public class EventOrderAdapter implements Comparator<Event>, Serializable {
    @Override
    public int compare(Event e1, Event e2){
        if (e1.getEventType().equals("Birth")){
            return -1;
        }
        else if (e2.getEventType().equals("Birth")){
            return 1;
        }
        else if(e1.getEventType().equals("Death")){
            return 1;
        }
        else if(e2.getEventType().equals("Death")){
            return -1;
        }
        else if (e1.getYear() < e2.getYear()){
            return -1;
        }
        else if(e1.getYear() > e2.getYear()){
            return 1;
        }
        else{
            return -1;
        }
    }
}
