package com.smtown.sigran0.horseraceapp.threads;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.smtown.sigran0.horseraceapp.views.GamePanel;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class MainThread extends Thread {

    private static final String TAG = "MainThread";

    public static final int MAX_FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMills = 1000/ MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while(running){

            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMills = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMills;
            try{
                if(waitTime > 0)
                    this.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount += 1;

            if(frameCount == MAX_FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;

                Log.d(TAG, "Average FPS: " + averageFPS);
            }
        }
    }
}
