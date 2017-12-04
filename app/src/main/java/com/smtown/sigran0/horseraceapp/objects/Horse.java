package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.smtown.sigran0.horseraceapp.managers.BinderManager;
import com.smtown.sigran0.horseraceapp.tools.Constants;

/**
 * Created by jungsungwoo on 11/20/17.
 */

public class Horse extends BaseObject {

    enum State {
        Booster,    //  1초 속도 2배
        Slow,      //  1초 속도 0.5
        Normal
    }

    private static final float MAX_SPEED = 20.0f;
    private static final float MIN_SPEED = 2.0f;
    private static final int SPEED_SETTER = 512;    //  512 is best
    
    private static final String TAG = "fucking";

    private int mColor;
    private Paint mPaint;
    private Paint mTextPaint;

    private float mBaseSpeed = 0;
    private float mCurrentSpeed = 0;
    private float mCurrentAcceleration;
    private float mCurrentForce;
    private float mHorseWeight;
    private float mHorseMaxForce;

    private int mHorseNumber;
    private boolean mIsLastHorse;

    private boolean mItemUsed;
    private State mState = State.Normal;
    private int mStateCounter = 0;
    private int mSecond = 0;

    public Horse(RectF rect, PointF point, int color, int number){
        super(rect, point);

        mRect = rect;
        mColor = color;
        mPaint = new Paint();
        mPaint.setColor(mColor);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mRect.width() / 2);

        mHorseNumber = number;
    }

    private void calculateSpeed(){
        mBaseSpeed += mCurrentForce / (mHorseWeight * SPEED_SETTER);

        if(Math.abs(mCurrentForce) - mBaseSpeed * 10 < 0)
            mCurrentForce = 0;
        else if(mCurrentForce > 0){
            mCurrentForce -= mBaseSpeed * 10;
        } else {
            mCurrentForce += mBaseSpeed * 10;
        }

        if(mBaseSpeed < MIN_SPEED) {
            mBaseSpeed = MIN_SPEED;
        }

        if(!mIsLastHorse)
            mCurrentSpeed = mBaseSpeed;
        else {
            mCurrentSpeed = mBaseSpeed * 1.0f;
        }

        if(mState == State.Booster) {
            mCurrentSpeed = mBaseSpeed * 2.0f;
        }

        if(mState == State.Slow) {
            mCurrentSpeed = 0.0f;
        }
    }

    public void setLastHorse(boolean b){
        mIsLastHorse = b;
    }

    public void addForce(float force){

        mCurrentForce += force;

        if(mCurrentForce > mHorseMaxForce)
            mCurrentForce = mHorseMaxForce;
    }

    public void debugLog(){
        //Log.d(TAG, String.format("[%d] Position : (%.1f, %.1f), speed : %.1f, force : %.1f", mHorseNumber, mPosition.x, mPosition.y, mBaseSpeed, mCurrentForce));
    }

    public void setFinish(){
        mCurrentForce = -99999;
        Log.d(TAG, String.format("[%d] Finished Position : (%.1f, %.1f), speed : %.1f, Rect : (%.1f, %.1f, %.1f, %.1f)", mHorseNumber, mPosition.x, mPosition.y, mBaseSpeed, mRect.left, mRect.top, mRect.right, mRect.bottom));
        update();
    }

    public void setSlow(){
        mStateCounter += 2;
        mState = State.Slow;
    }

    public void setItemUsed(boolean b){
        mItemUsed = b;
    }

    public boolean getItemUsed(){
        return mItemUsed;
    }

    @Override
    protected void initialize(){
        mHorseWeight = tools.getRangeFloat(Constants.HORSE_MIN_WEIGHT, Constants.HORSE_MAX_WEIGHT);
        //mCurrentForce = Constants.HORSE_START_FORCE;
        mHorseMaxForce = mHorseWeight * 10;
        mCurrentForce = tools.getRangeFloat(Constants.HORSE_MIN_WEIGHT * 10, mHorseMaxForce);

        //Log.d(TAG, String.format("Weight : " + mHorseWeight));

        binderManager.bind("boost", new BinderManager.BinderInterface<Integer>() {
            @Override
            public void update(Integer data) {

                if(data == mHorseNumber){
                    mItemUsed = true;
                    mStateCounter = 3;
                    mState = State.Booster;
                }
            }
        });
    }

    public void updateSecond(int second){

        mSecond = second;

        if(mState != State.Normal) {

            if(mStateCounter > 0){
                mStateCounter -= 1;

                if(mStateCounter <= 0){
                    mState = State.Normal;
                }
            }
        }
    }

    @Override
    public void update(){
//        mRect.set(mPosition.x - mRect.width() / 2, mPosition.y - mRect.height() / 2,
//                mPosition.x + mRect.width() / 2, mPosition.y + mRect.height() / 2);
        calculateSpeed();
        setPosition(mPosition.x + mCurrentSpeed, mPosition.y);
        mRect.set(mPosition.x, mPosition.y, mPosition.x + mRect.width(), mPosition.y + mRect.height());

        //Log.d(TAG, String.format("[%d] Speed : %f, Force : %f", mHorseNumber, mBaseSpeed, mCurrentForce));
        //Log.d(TAG, String.format("[%d] Position : (%.1f, %.1f), speed : %.1f, Rect : (%.1f, %.1f, %.1f, %.1f)", mHorseNumber, mPosition.x, mPosition.y, mBaseSpeed, mRect.left, mRect.top, mRect.right, mRect.bottom));
    }

    @Override
    public void draw(Canvas canvas){

        if(canvas != null) {

            if (mState == State.Normal) {
                mPaint.setColor(Color.BLACK);
            } else if (mState == State.Booster) {
                mPaint.setColor(Color.RED);
            } else if (mState == State.Slow) {
                mPaint.setColor(Color.DKGRAY);
            }

            canvas.drawRect(mRect, mPaint);
            canvas.drawText(String.format("%.1f", mCurrentSpeed), mRect.centerX() - mRect.width() / 2, mRect.centerY(), mTextPaint);
        }
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
