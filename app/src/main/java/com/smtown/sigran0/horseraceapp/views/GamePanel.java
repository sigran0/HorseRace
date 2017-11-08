package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smtown.sigran0.horseraceapp.objects.ObstacleManager;
import com.smtown.sigran0.horseraceapp.objects.RectPlayer;
import com.smtown.sigran0.horseraceapp.threads.MainThread;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private ObstacleManager obstacleManager;
    private RectPlayer player;
    private Point playerPoint;

    public GamePanel(Context context) {
        this(context, null);
    }

    public GamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.RED);
        playerPoint = new Point(150, 150);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);

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

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);
        obstacleManager.draw(canvas);
        player.draw(canvas);

    }

    public void update(){
        obstacleManager.update();
        player.update(playerPoint);
    }
}