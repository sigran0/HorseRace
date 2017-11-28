package com.smtown.sigran0.horseraceapp.scences;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;

import com.smtown.sigran0.horseraceapp.interfaces.GameScene;
import com.smtown.sigran0.horseraceapp.objects.FinishLine;
import com.smtown.sigran0.horseraceapp.objects.Horse;
import com.smtown.sigran0.horseraceapp.objects.Lane;
import com.smtown.sigran0.horseraceapp.tools.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jungsungwoo on 11/27/17.
 */

public class World implements GameScene {
    
    private static final String TAG = "fucking";

    private static final int MAX_HOSRES = 6;
    private static final int MAX_LANE_HEIGHT = 128;

    private Size mPanelSize;
    private Context mContext;

    private int mDebugTargetIndex = -1;

    private List<Lane> mLaneList = new ArrayList<>();
    private List<Integer> mArrivedList = new ArrayList<>();

    private boolean mIsGameOver = false;

    public World(Context context, Size panelSize){
        mPanelSize = panelSize;
        mContext = context;

        initializeWorld();
    }

    private void initializeWorld(){

        //  Initialize related sizes
        int laneWidth = mPanelSize.getWidth();
        int laneHeight = mPanelSize.getHeight() / MAX_HOSRES;

        if (laneHeight > MAX_LANE_HEIGHT) {
            laneHeight = MAX_LANE_HEIGHT;
        }

        int horseWidth = laneHeight;
        int horseHeight = laneHeight;

        int laneHeightSum = laneHeight * MAX_HOSRES;
        int laneMargin = 0;

        if (mPanelSize.getHeight() > laneHeightSum)
            laneMargin = (mPanelSize.getHeight() - laneHeightSum) / (MAX_HOSRES + 1);

        Lane.OnArrivedEvent arrivedEvent = new Lane.OnArrivedEvent() {
            @Override
            public void onArrived(int laneNumber) {
                Log.d(TAG, "Lane " + laneNumber + " is arrived!");
                mArrivedList.add(laneNumber);
            }
        };

        Constants.LANE_WIDTH = laneWidth;
        Constants.LANE_HEIGHT = laneHeight;

        //  Initialize Horses and lanes
        for (int c = 0; c < MAX_HOSRES; c++) {
            float laneStartY = laneHeight * c + laneMargin * (c + 1);

            Log.d(TAG, String.format("Lane Size : (%d, %d)", horseWidth, horseHeight));

            //  Create Horse
            RectF horseRect = new RectF(0, 0, horseWidth, horseHeight);
            PointF horsePoint = new PointF(0, laneStartY);

            Horse horse = new Horse(horseRect, horsePoint, Color.BLACK, c);

            //  Create Finish Line
            RectF finishRect = new RectF(laneWidth - horseWidth, laneStartY, laneWidth, laneStartY + laneHeight);
            PointF finishPoint = new PointF(laneWidth - horseWidth, laneStartY);

            FinishLine finishLine = new FinishLine(finishRect, finishPoint, Color.BLUE);

            //  Create Lane
            RectF laneRect = new RectF(0, laneStartY, laneWidth, laneStartY + laneHeight);
            PointF lanePoint = new PointF(0, laneStartY);

            Lane lane = new Lane(laneRect, lanePoint, c);

            lane.setLaneFinishLine(finishLine);
            lane.setLaneHorse(horse);
            lane.setArraivedEvent(arrivedEvent);

            mLaneList.add(lane);
        }
    }

    private int getLastHorse(){
        float min = 999999999;
        int minIndex = -1;

        for(int c = 0; c < mLaneList.size(); c++){

            float currentX = mLaneList.get(c).getCurrentHorseX();

            if(min > currentX){
                min = currentX;
                minIndex = c;
            }
        }

        return minIndex;
    }

    private boolean isAllHorseArrived() {

        for(Lane lane : mLaneList){
            if(!lane.getArrived())
                return false;
        }

        return true;
    }

    @Override
    public void update(){

        boolean isEnd = isAllHorseArrived();

        if(!isEnd) {
            int c = 0;
            int lastHorse = getLastHorse();
            for (Lane lane : mLaneList) {
                lane.update();
                if(mDebugTargetIndex == c)
                    lane.debugLog();
                c++;
            }

        } else if(!mIsGameOver){
            mIsGameOver = true;
            Log.d(TAG, String.format("last lane is %d", mArrivedList.get(mArrivedList.size() - 1)));
        }
    }

    @Override
    public void draw(Canvas canvas){

        for(Lane lane : mLaneList)
            lane.draw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event){

        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {

                int c = 0;
                for(Lane lane : mLaneList){

                    if(lane.isCollisionWithPoint(x, y)){
                        mDebugTargetIndex = c;
                    }

                    c++;
                }
            }
        }
    }


    @Override
    public void destroy(){

        for(Lane lane : mLaneList)
            lane.destroy();
    }

    public interface GameEndEvent{
        public void onGameEnd(List<Integer> list);
    }
}
