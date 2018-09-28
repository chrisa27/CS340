package com.example;

import java.util.Comparator;

/**
 * Created by video on 2/3/2017.
 */

public class PartitionCompare implements Comparator<Partition> {
    @Override
    public int compare(Partition p1, Partition p2){
        if(p1.getPositions().equals(p2.getPositions())){
            return 0;
        }

        if (p1.getWords().size() > p2.getWords().size()){
            return -1;
        }
        else if (p1.getWords().size() < p2.getWords().size()){
            return 1;
        }
        else{
            if (p1.getPositions().numLetters() > p2.getPositions().numLetters()){
                return 1;
            }
            if (p1.getPositions().numLetters() < p2.getPositions().numLetters()){
                return -1;
            }
            else {
                if(p1.getPositions().isRightmost(p2.getPositions())){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }
    }
}
