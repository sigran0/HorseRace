package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Random;

/**
 * Created by jungsungwoo on 11/28/17.
 */

public class Lane extends BaseObject{
    
    private static final String TAG = "fucking";

    private Horse mHorse;
    private FinishLine mFinishLine;
    private boolean mIsArrived;
    private Paint mPaint;
    private OnArrivedEvent mArraivedEvent;
    private int mLaneNumber;

    public Lane(@NonNull RectF rect, PointF point, int laneNumber){
        super(rect, point);

        setBasePosition(BasePosition.LEFT_TOP);
        mRect = rect;

        int color = new Random().nextInt();
        //int color = Color.BLACK;
        mPaint = new Paint();
        mPaint.setColor(color);

        mLaneNumber = laneNumber;
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
    }

    @Override
    public void update(){

        if(mHorse.isCollisionWithObject(mFinishLine)) {
            mHorse.setFinish();
            mArraivedEvent.onArrived(mLaneNumber);
        } else {
            mHorse.update();
        }
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
