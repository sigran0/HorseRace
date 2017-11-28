package com.smtown.sigran0.horseraceapp.threads;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.smtown.sigran0.horseraceapp.views.GamePanel;

/**
 * Created by jungsungwoo on 11/20/17.
 */

public class MainThread extends Thread{
    
    private static final String TAG = "good";

    public static final int MAX_FPS = 30;

    private double averageFPS;
    private SurfaceHolder mSurfaceHolder;
    private GamePanel mGamePanel;
    private boolean mOnRunning;
    public static Canvas mCanvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        mSurfaceHolder = surfaceHolder;
        mGamePanel = gamePanel;
    }

    public void setRunning(boolean running){
        mOnRunning = running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMills = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        long beforeTime = 0;

        while(mOnRunning){
            startTime = System.nanoTime();
            mCanvas = null;

            try {
                mCanvas = this.mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    this.mGamePanel.update();
                    this.mGamePanel.draw(mCanvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(mCanvas != null){
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMills = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMills;

            try {
                if(waitTime > 0)
                    this.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAX_FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //Log.d(TAG, "run: " + averageFPS + ", before Time : " + ((System.nanoTime() - beforeTime) / 1000000) / 1000.0f);

                beforeTime = System.nanoTime();
            }
        }
    }
}
