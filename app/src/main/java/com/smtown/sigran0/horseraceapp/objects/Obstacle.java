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

public class Obstacle extends BaseObject {

    private static final String TAG = "Obstacle";

    private int mColor;
    private Paint mPaint;

    private static int mObstacleCounter = 0;

    public Obstacle(RectF rect, PointF position, int color){
        super(rect, position);

        mRect = rect;
        mColor = color;
        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    public void onCreate(){
        mRect.set(mPosition.x - mRect.width() / 2, mPosition.y - mRect.height() / 2,
                mPosition.x + mRect.width() / 2, mPosition.y + mRect.height() / 2);

        mObstacleCounter += 1;

        if(mObstacleCounter % 100 == 0)
            Log.d(TAG, "onCreate: Obstacle Counter : " + mObstacleCounter);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public void update(){
        mRect.set(mPosition.x - mRect.width() / 2, mPosition.y - mRect.height() / 2,
                mPosition.x + mRect.width() / 2, mPosition.y + mRect.height() / 2);
    }

    @Override
    public void destroy(){

    }
}
