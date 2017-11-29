package com.smtown.sigran0.horseraceapp.scences;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.MotionEvent;

import com.smtown.sigran0.horseraceapp.interfaces.GameScene;
import com.smtown.sigran0.horseraceapp.managers.BinderManager;
import com.smtown.sigran0.horseraceapp.managers.DialogManager;
import com.smtown.sigran0.horseraceapp.objects.FinishLine;
import com.smtown.sigran0.horseraceapp.objects.Horse;
import com.smtown.sigran0.horseraceapp.objects.Lane;
import com.smtown.sigran0.horseraceapp.objects.items.LastIndex;
import com.smtown.sigran0.horseraceapp.tools.Constants;
import com.smtown.sigran0.horseraceapp.tools.MyTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jungsungwoo on 11/27/17.
 */

public class World implements GameScene {
    
    private static final String TAG = "fucking";
    private static final int MAX_LANE_HEIGHT = 128;

    private Size mPanelSize;
    private Context mContext;

    private int mDebugTargetIndex = -1;

    private List<Lane> mLaneList = new ArrayList<>();
    private List<Integer> mArrivedList = new ArrayList<>();
    private SparseArray<Integer> mNotArrivedList = new SparseArray<>();
    private SparseArray<Integer> mArrivedTempList = new SparseArray<>();

    private  int mHorseSize;
    private boolean mIsGameOver = false;
    private boolean mIsStart = false;
    private int mLastIndex = -1;
    private boolean mIsLastChanged = false;
    private MyTools tools = MyTools.getInstance();
    private int mCurrentRanking = 1;

    private BinderManager binderManager = BinderManager.getInstance();

    public World(Context context, Size panelSize, int horseSize){
        mHorseSize = horseSize;
        mPanelSize = panelSize;
        mContext = context;

        mIsStart = true;
        initializeWorld();
    }

    private void initializeWorld(){

        //  Initialize related sizes
        int laneWidth = mPanelSize.getWidth();
        int laneHeight = mPanelSize.getHeight() / mHorseSize;

        if (laneHeight > MAX_LANE_HEIGHT) {
            laneHeight = MAX_LANE_HEIGHT;
        }

        int horseWidth = laneHeight;
        int horseHeight = laneHeight;

        int laneHeightSum = laneHeight * mHorseSize;
        int laneMargin = 0;

        if (mPanelSize.getHeight() > laneHeightSum)
            laneMargin = (mPanelSize.getHeight() - laneHeightSum) / (mHorseSize + 1);

        Lane.OnArrivedEvent arrivedEvent = new Lane.OnArrivedEvent() {
            @Override
            public void onArrived(int laneNumber) {
                Log.d(TAG, "Lane " + laneNumber + " is arrived!");
                //mArrivedTempList.append(laneNumber, laneNumber);
                mNotArrivedList.remove(laneNumber);
                mArrivedList.add(laneNumber);
            }
        };

        Constants.LANE_WIDTH = laneWidth;
        Constants.LANE_HEIGHT = laneHeight;

        //  Initialize Horses and lanes
        for (int c = 0; c < mHorseSize; c++) {
            float laneStartY = laneHeight * c + laneMargin * (c + 1);

            //Log.d(TAG, String.format("Lane Size : (%d, %d)", horseWidth, horseHeight));

            mNotArrivedList.append(c, c);

            //  Create Horse
            RectF horseRect = new RectF(0, 0, horseWidth, horseHeight);
            PointF horsePoint = new PointF(0, laneStartY);

            Horse horse = new Horse(horseRect, horsePoint, Color.BLACK, c);

            //  Create Finish Line
            RectF finishRect = new RectF(laneWidth - horseWidth, laneStartY, laneWidth, laneStartY + laneHeight);
            PointF finishPoint = new PointF(laneWidth - horseWidth, laneStartY);

            FinishLine finishLine = new FinishLine(finishRect, finishPoint, Color.BLUE, c);

            //  Create Lane
            RectF laneRect = new RectF(0, laneStartY, laneWidth, laneStartY + laneHeight);
            PointF lanePoint = new PointF(0, laneStartY);

            Lane lane = new Lane(laneRect, lanePoint, c);

            lane.setLaneFinishLine(finishLine);
            lane.setLaneHorse(horse);
            lane.setArraivedEvent(arrivedEvent);

            mLaneList.add(lane);
        }

        binderManager.bind("slow", new BinderManager.BinderInterface<Integer>() {
            @Override
            public void update(Integer data) {
                Integer target = getRandomNotArrivedLane(data);
                mLaneList.get(data).setLaneItemUsed(true);
                mLaneList.get(target).setSlow();
            }
        });

        binderManager.bind("teleport", new BinderManager.BinderInterface<Integer>() {
            @Override
            public void update(Integer data) {
                Integer target = getRandomNotArrivedLane(data);
                mLaneList.get(data).setLaneItemUsed(true);
                mLaneList.get(data).switchPosition(mLaneList.get(target));
            }
        });
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

        if(minIndex != mLastIndex){
            mIsLastChanged = true;
            mLastIndex = minIndex;
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

    private Integer getRandomNotArrivedLane(int myIndex){

        if(mNotArrivedList.size() < 0)
            return null;
        int randIndex = -1;
        do {
            Log.d(TAG, String.format("Not Arrived size : %d", mNotArrivedList.size()));
            randIndex = tools.getRangeInt(0, mNotArrivedList.size() - 1);
        } while(randIndex == myIndex);

        return new Integer(randIndex);
    }

    @Override
    public void updateSecond(int second){

        if(mIsStart) {
            for (Lane lane : mLaneList) {
                if (!lane.getArrived())
                    lane.updateSecond(second);
            }
        }
    }

    @Override
    public void update(){

        Log.d(TAG, String.format("Not Arrived : %d", mNotArrivedList.size()));

        if(mIsStart) {

            boolean isEnd = isAllHorseArrived();

            if (!isEnd) {
                int c = 0;
                int lastHorse = getLastHorse();
                if (mIsLastChanged) {
                    mIsLastChanged = false;
                    LastIndex index = new LastIndex(mLastIndex);
                    boolean itemUsed = mLaneList.get(mLastIndex).getLaneItemUsed();
                    index.setItemUsed(itemUsed);
                    binderManager.startUpdate("scoreChanged", index);
                    //Log.d(TAG, String.format("Last horse is changed ! %d", mLastIndex));
                }

                for (Lane lane : mLaneList) {
                    if (lastHorse == c) {
                        lane.setLastLane(true);
                    } else {
                        lane.setLastLane(false);
                    }
                    lane.update();
                    if (mDebugTargetIndex == c)
                        lane.debugLog();
                    c++;
                }

                if (mArrivedTempList.size() > 0) {

                    for (int d = 0; d < mArrivedTempList.size(); d++) {

                        int randIdx = tools.getRangeInt(0, mArrivedTempList.size() - 1);

                        mNotArrivedList.remove(mArrivedTempList.keyAt(randIdx));
                        mArrivedList.add(mArrivedTempList.keyAt(randIdx));
                        mArrivedTempList.remove(randIdx);
                    }
                }

                mArrivedTempList.clear();

            } else if (!mIsGameOver) {
                mIsGameOver = true;
                LastIndex index = new LastIndex(mArrivedList.get(mArrivedList.size() - 1));
                boolean itemUsed = mLaneList.get(mLastIndex).getLaneItemUsed();
                index.setItemUsed(itemUsed);
                binderManager.startUpdate("scoreChanged", index);
                Log.d(TAG, String.format("last lane is %d", mArrivedList.get(mArrivedList.size() - 1)));

                DialogManager dialogManager = new DialogManager(mContext);
                StringBuilder stringBuilder = new StringBuilder();

                for(int c = 0; c < mArrivedList.size(); c++){
                    stringBuilder
                            .append(String.format("[Rank %d] : %d", c + 1, mArrivedList.get(c)))
                            .append("\n");
                }

                dialogManager.showResult(stringBuilder.toString(), new DialogManager.OnClickEvent() {
                    @Override
                    public void onClick() {
                        binderManager.startUpdate("finish", "finish");
                    }
                });
            }
        }
    }

    @Override
    public void draw(Canvas canvas){

        if(mIsStart) {
            for (Lane lane : mLaneList)
                lane.draw(canvas);
        }
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
