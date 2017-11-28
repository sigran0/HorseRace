package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by jungsungwoo on 11/20/17.
 */

public class Horse extends BaseObject {
    
    private static final String TAG = "Horse";

    private int mColor;
    private Paint mPaint;

    private float mCurrentSpeed;
    private float mCurrentAcceleration;

    private int mHorseNumber;

    public Horse(RectF rect, PointF point, int color, int number){
        super(rect, point);

        mRect = rect;
        mColor = color;
        mPaint = new Paint();
        mPaint.setColor(mColor);

        mHorseNumber = number;

        mCurrentSpeed = 10.0f;
    }

    public void setCurrentSpeed(float speed){
        mCurrentSpeed = speed;
    }

    public void setCurrentAcceleration(float acceleration){
        mCurrentAcceleration = acceleration;
    }

    @Override
    public void update(){
//        mRect.set(mPosition.x - mRect.width() / 2, mPosition.y - mRect.height() / 2,
//                mPosition.x + mRect.width() / 2, mPosition.y + mRect.height() / 2);

        mPosition.x += mCurrentSpeed;
        mRect.set(mPosition.x, mPosition.y, mPosition.x + mRect.width(), mPosition.y + mRect.height());

        Log.d(TAG, String.format("[%d] Position : (%.1f, %.1f), speed : %.1f, Rect : (%.1f, %.1f, %.1f, %.1f)", mHorseNumber, mPosition.x, mPosition.y, mCurrentSpeed, mRect.left, mRect.top, mRect.right, mRect.bottom));
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "onCreate: Hello World!");
    }

    @Override
    public void destroy(){
        Log.d(TAG, "destroy: GoodBye World!");
    }
}
