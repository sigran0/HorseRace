package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.smtown.sigran0.horseraceapp.tools.Constants;
import com.smtown.sigran0.horseraceapp.tools.MyTools;

import java.util.Random;

/**
 * Created by jungsungwoo on 11/20/17.
 */

public class Horse extends BaseObject {

    private static final float MAX_SPEED = 20.0f;
    private static final int SPEED_SETTER = 64;    //  512 is best
    
    private static final String TAG = "fucking";

    private int mColor;
    private Paint mPaint;

    private float mCurrentSpeed = 0;
    private float mCurrentAcceleration;
    private float mCurrentForce;
    private float mHorseWeight;
    private float mHorseMaxForce;

    private int mHorseNumber;

    public Horse(RectF rect, PointF point, int color, int number){
        super(rect, point);

        mRect = rect;
        mColor = color;
        mPaint = new Paint();
        mPaint.setColor(mColor);

        mHorseNumber = number;
    }

    private void calculateSpeed(){
        mCurrentSpeed += mCurrentForce / (mHorseWeight * SPEED_SETTER);

        if(Math.abs(mCurrentForce) - mCurrentSpeed * 10 < 0)
            mCurrentForce = 0;
        else if(mCurrentForce > 0){
            mCurrentForce -= mCurrentSpeed * 10;
        } else {
            mCurrentForce += mCurrentSpeed * 10;
        }

        if(mCurrentSpeed < 2) {
            mCurrentSpeed = 2;
        }
    }

    private void initializeHorse(){

    }

    public void setCurrentSpeed(float speed){
        mCurrentSpeed = speed;
    }

    public void addForce(float force){

        mCurrentForce += force;

        if(mCurrentForce > mHorseMaxForce)
            mCurrentForce = mHorseMaxForce;
    }

    public void debugLog(){
        Log.d(TAG, String.format("[%d] Position : (%.1f, %.1f), speed : %.1f, force : %.1f", mHorseNumber, mPosition.x, mPosition.y, mCurrentSpeed, mCurrentForce));
    }

    public void setFinish(){
        mCurrentForce = -99999;
        Log.d(TAG, String.format("[%d] Finished Position : (%.1f, %.1f), speed : %.1f, Rect : (%.1f, %.1f, %.1f, %.1f)", mHorseNumber, mPosition.x, mPosition.y, mCurrentSpeed, mRect.left, mRect.top, mRect.right, mRect.bottom));
        update();
    }

    @Override
    protected void initialize(){
        mHorseWeight = tools.getRangeFloat(Constants.HORSE_MIN_WEIGHT, Constants.HORSE_MAX_WEIGHT);
        //mCurrentForce = Constants.HORSE_START_FORCE;
        mHorseMaxForce = mHorseWeight * 10;
        mCurrentForce = tools.getRangeFloat(Constants.HORSE_MIN_WEIGHT * 10, mHorseMaxForce);

        Log.d(TAG, String.format("Weight : " + mHorseWeight));
    }

    @Override
    public void update(){
//        mRect.set(mPosition.x - mRect.width() / 2, mPosition.y - mRect.height() / 2,
//                mPosition.x + mRect.width() / 2, mPosition.y + mRect.height() / 2);
        calculateSpeed();
        setPosition(mPosition.x + mCurrentSpeed, mPosition.y);
        mRect.set(mPosition.x, mPosition.y, mPosition.x + mRect.width(), mPosition.y + mRect.height());

        //Log.d(TAG, String.format("[%d] Speed : %f, Force : %f", mHorseNumber, mCurrentSpeed, mCurrentForce));
        //Log.d(TAG, String.format("[%d] Position : (%.1f, %.1f), speed : %.1f, Rect : (%.1f, %.1f, %.1f, %.1f)", mHorseNumber, mPosition.x, mPosition.y, mCurrentSpeed, mRect.left, mRect.top, mRect.right, mRect.bottom));
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
