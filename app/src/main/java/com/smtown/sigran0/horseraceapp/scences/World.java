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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jungsungwoo on 11/27/17.
 */

public class World implements GameScene {
    
    private static final String TAG = "fucking";

    private static final int MAX_HOSRES = 2;
    private static final int MAX_LANE_HEIGHT = 128;

    private Size mPanelSize;
    private Context mContext;

    private List<Lane> mLaneList = new ArrayList<>();
    private List<Horse> mHorseList = new ArrayList<>();

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

            Lane lane = new Lane(laneRect, lanePoint);

            lane.setLaneFinishLine(finishLine);
            lane.setLaneHorse(horse);

            mLaneList.add(lane);
            mHorseList.add(horse);
        }
    }

    @Override
    public void update(){

        for(Lane lane : mLaneList)
            lane.update();
    }

    @Override
    public void draw(Canvas canvas){

        for(Lane lane : mLaneList)
            lane.draw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event){

    }


    @Override
    public void destroy(){

        for(Lane lane : mLaneList)
            lane.destroy();
    }
}
