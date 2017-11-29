package com.smtown.sigran0.horseraceapp.objects.items;

/**
 * Created by jungsungwoo on 11/29/17.
 */

public class LastIndex {

    private int laneNumber;
    private boolean itemUsed;

    public LastIndex(int laneNumber){
        this.laneNumber = laneNumber;
    }

    public void setItemUsed(boolean itemUsed){
        this.itemUsed = itemUsed;
    }

    public int getLaneNumber(){
        return laneNumber;
    }

    public boolean getItemUsed(){
        return itemUsed;
    }
}
