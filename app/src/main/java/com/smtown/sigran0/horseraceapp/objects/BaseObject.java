package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by jungsungwoo on 11/20/17.
 */

public abstract class BaseObject {

    protected enum BasePosition {
        LEFT_TOP,
        CENTER_CENTER
    }

    protected RectF mRect;
    protected PointF mPosition;
    protected BasePosition mBasePosition = BasePosition.CENTER_CENTER;

    public abstract void onCreate();
    public abstract void destroy();
    public abstract void draw(Canvas canvas);
    public abstract void update();

    BaseObject(RectF rect, PointF position){
        mRect = rect;
        mPosition = position;
    }

    protected void setBasePosition(BasePosition basePosition){
        mBasePosition = basePosition;
    }

    public boolean isCollisionWithPoint(int x, int y){

        if((x > mPosition.x - mRect.width() / 2 && x < mPosition.x + mRect.width() / 2) &&
                (y > mPosition.y - mRect.height() / 2 && y < mPosition.y + mRect.height() / 2))
            return true;

        return false;
    }

    public boolean isCollisionWithObject(BaseObject object){
        return mRect.intersect(object.mRect);
    }

    public void setPosition(float x, float y){
        mPosition = new PointF(x, y);
    }
}
