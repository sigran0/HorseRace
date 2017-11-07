package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Rect;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class Entity {

    protected int mPositionX;
    protected int mPositionY;
    protected Rect mRect;

    public int getX(){
        return mPositionX;
    }

    public int getY() {
        return mPositionY;
    }

    public void setX(int x){
        mPositionX = x;
    }

    public void setY(int y){
        mPositionY = y;
    }

    public Rect getRect() {
        return mRect;
    }

    protected Entity(){

    }

    protected Entity(int initialX, int initialY){
        mPositionX = initialX;
        mPositionY = initialY;
    }
}
