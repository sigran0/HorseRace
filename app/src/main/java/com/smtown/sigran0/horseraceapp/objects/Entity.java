package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Rect;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public abstract class Entity {

    protected int mPositionX;
    protected int mPositionY;
    protected Rect mRect;

    protected int getX(){
        return mPositionX;
    }

    protected int getY() {
        return mPositionY;
    }

    protected void setX(int x){
        mPositionX = x;
    }

    protected void setY(int y){
        mPositionY = y;
    }

    protected Rect getRect() {
        return mRect;
    }

    protected Entity(){

    }

    protected Entity(int initialX, int initialY){
        mPositionX = initialX;
        mPositionY = initialY;
    }
}
