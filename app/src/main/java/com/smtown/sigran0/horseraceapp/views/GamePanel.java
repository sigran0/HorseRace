package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smtown.sigran0.horseraceapp.scences.World;
import com.smtown.sigran0.horseraceapp.threads.MainThread;
import com.smtown.sigran0.horseraceapp.tools.MyTools;

/**
 * Created by jungsungwoo on 11/20/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    
    private static final String TAG = "fucking";

    private MainThread mMainThread;
    private boolean isViewAttached = false;
    private MyTools tools;
    private World mWorld;
    private int mHorseSize;

    public GamePanel(Context conetxt){
        this(conetxt, null);
    }

    public GamePanel(final Context context, AttributeSet attrs){

        super(context, attrs);

        tools = MyTools.getInstance();

        getHolder().addCallback(this);

        mMainThread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    private Size measurePanelSize(){
        int screenWidth, screenHeight, panelWidth, panelHeight;

        screenWidth = tools.getScreenWidth();
        screenHeight = tools.getScreenHeight();

        panelWidth = this.getWidth();
        panelHeight = this.getHeight();

        Size panelSize = new Size(panelWidth, panelHeight);

        Log.d(TAG, String.format("ScreenSize : (%d, %d), PanelSize : (%d, %d)", screenWidth, screenHeight, panelWidth, panelHeight));

        return panelSize;
    }

    public void finish(){

    }

    public void setSize(int size){
        mHorseSize = size;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        Log.d(TAG, "surfaceChanged called");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        Log.d(TAG, "surfaceCreated called");

        mMainThread = new MainThread(getHolder(), this);

        mMainThread.setRunning(true);
        mMainThread.start();

        Size panelSize = measurePanelSize();

        mWorld = new World(getContext(), panelSize, mHorseSize);

        setViewAttached(true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        Log.d(TAG, "surfaceDestroyed called");

        boolean retry = true;
        while(retry){
            try {
                mMainThread.setRunning(false);
                mMainThread.join();

                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mWorld.destroy();
        Log.d(TAG, "surfaceDestroyed end");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mWorld.onTouchEvent(event);

        return true;
    }

    @Override
    public void draw(Canvas canvas){
        if(isViewAttached) {
            if(canvas != null) {
                super.draw(canvas);
                canvas.drawColor(Color.WHITE);

                mWorld.draw(canvas);
            }
        }
    }

    public void updateSecond(int second){
        if(isViewAttached) {
            mWorld.updateSecond(second);
        }
    }

    public void update(){
        if(isViewAttached) {
            mWorld.update();
        }
    }

    public void setViewAttached(boolean viewAttached){
        isViewAttached = viewAttached;
    }
}
