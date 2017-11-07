package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Bitmap;

import java.lang.invoke.WrongMethodTypeException;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class DrawableEntity extends Entity {

    protected int mResourceId;

    protected DrawableEntity(int resId){
        this(0, 0, resId);
    }

    protected DrawableEntity(int pX, int pY, int resId){
        super(pX, pY);
        mResourceId = resId;
    }

    protected DrawableEntity(){
        super();
    }
}
