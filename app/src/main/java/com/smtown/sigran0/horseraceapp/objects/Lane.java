package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.smtown.sigran0.horseraceapp.objects.items.Item;
import com.smtown.sigran0.horseraceapp.objects.items.SpeedFactor;
import com.smtown.sigran0.horseraceapp.tools.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jungsungwoo on 11/28/17.
 */

public class Lane extends BaseObject {
    
    private static final String TAG = "fucking";

    private Horse mHorse;
    private FinishLine mFinishLine;
    private boolean mIsArrived;
    private Paint mPaint;
    private OnArrivedEvent mArraivedEvent;
    private int mLaneNumber;

    private SparseArray<Item> mItemList = new SparseArray<>();

    public Lane(@NonNull RectF rect, PointF point, int laneNumber){
        super(rect, point);

        setBasePosition(BasePosition.LEFT_TOP);
        mRect = rect;

        int color = new Random().nextInt();
        //int color = Color.BLACK;
        mPaint = new Paint();
        mPaint.setColor(color);

        mLaneNumber = laneNumber;

        initializeLane();
    }

    protected void initializeLane(){
        int itemSize = tools.getRangeInt(Constants.LANE_ITEM_MIN, Constants.LANE_ITEM_MAX);

        float laneY = mRect.top;
        float laneStartX = mRect.left;
        float laneEndX = mRect.right;

        for(int c = 0; c < itemSize; c++){

            float randomPositionX = tools.getRangeFloat(laneStartX, laneEndX - Constants.LANE_HEIGHT * 2);  //  horse 크기와 결승점 크기 빼줌
            RectF rect = new RectF(randomPositionX, laneY, randomPositionX + Constants.LANE_HEIGHT, laneY + Constants.LANE_HEIGHT);
            PointF point = new PointF(randomPositionX, laneY);

            Log.d(TAG, String.format("RandomPositionX : %.2f, Rect : (%.2f %.2f %.2f %.2f), Point : (%.2f %.2f)", randomPositionX, rect.left, rect.top, rect.right, rect.bottom, point.x, point.y));

            SpeedFactor speedFactor = new SpeedFactor(rect, point);

            mItemList.append(c, speedFactor);
        }
    }

    @Override
    protected void initialize(){

    }

    @Override
    public void onCreate(){

        if(mHorse == null)
            throw new NullPointerException("Horse object is null.");

        if(mFinishLine == null)
            throw new NullPointerException("Finish line object is null.");
    }

    @Override
    public void destroy(){

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(mRect, mPaint);

        mFinishLine.draw(canvas);
        mHorse.draw(canvas);

        for(int c = 0; c < mItemList.size(); c++){
            int key = mItemList.keyAt(c);
            Item item = mItemList.get(key);

            item.draw(canvas);
        }
    }

    @Override
    public void update(){

        if(mHorse.isCollisionWithObject(mFinishLine) && !mIsArrived) {
            mHorse.setFinish();
            mArraivedEvent.onArrived(mLaneNumber);
            mIsArrived = true;
        } else if(!mIsArrived){
            mHorse.update();

            for(int c = 0; c < mItemList.size(); c++){
                int key = mItemList.keyAt(c);
                Item item = mItemList.get(key);

                if(item.isCollisionWithObject(mHorse)){
                    item.onTouch(mHorse);
                    mItemList.remove(key);
                }
            }
        }
    }

    public void debugLog(){
        mHorse.debugLog();
    }

    public float getCurrentHorseX(){
        return mHorse.mPosition.x;
    }

    public boolean getArrived(){
        return mIsArrived;
    }

    public void setArraivedEvent(OnArrivedEvent event){
        mArraivedEvent = event;
    }

    public void setLaneHorse(Horse horse){
        mHorse = horse;
    }

    public void setLaneFinishLine(FinishLine finishLine){
        mFinishLine = finishLine;
    }

    public interface OnArrivedEvent{
        public void onArrived(int laneNumber);
    }
}
