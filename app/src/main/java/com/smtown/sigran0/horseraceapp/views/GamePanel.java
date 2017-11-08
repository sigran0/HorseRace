package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smtown.sigran0.horseraceapp.managers.SceneManager;
import com.smtown.sigran0.horseraceapp.threads.MainThread;
import com.smtown.sigran0.horseraceapp.tools.Constants;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private SceneManager manager;

    public GamePanel(Context context) {
        this(context, null);
    }

    public GamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        Constants.CURRENT_CONTEXT = context;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        manager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        boolean retry = true;
        while(true){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //return super.onTouchEvent(event);
        manager.recieveTouch(event);

        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        manager.draw(canvas);
    }

    public void update(){
        manager.update();
    }
}
