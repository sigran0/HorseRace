package com.smtown.sigran0.horseraceapp.objects.items;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.smtown.sigran0.horseraceapp.objects.BaseObject;
import com.smtown.sigran0.horseraceapp.objects.Horse;


public abstract class Item extends BaseObject {

    protected Paint mPaint;

    /**
     * Created by jungsungwoo on 11/28/17.
     */
    protected Item(RectF rect, PointF point){
        super(rect, point);
        mPaint = new Paint();
    }

    @Override
    protected void initialize(){

    }

    public abstract void onTouch(Horse player);
}
