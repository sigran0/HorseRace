package com.smtown.sigran0.horseraceapp.threads;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.smtown.sigran0.horseraceapp.views.MainView;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class MainThread extends Thread {

    private static final String TAG = "MainThread";

    private SurfaceHolder surfaceHolder;
    private MainView mainView;
    private boolean running = false;

    public MainThread(SurfaceHolder pHolder, MainView pView) {
        surfaceHolder = pHolder;
        mainView = pView;
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        Log.d(TAG, "run called / running : " + running);

        try {
            Canvas canvas;
            while(running) {
                canvas = null;

                try {
                    canvas = surfaceHolder.lockCanvas(null);

                    synchronized (surfaceHolder) {
                        try {
                            mainView.onDraw(canvas);
                            Thread.sleep(3);
                        } catch (Exception e){
                            Log.e(TAG, "run: ", e);
                        }
                    }
                } finally {
                    if(canvas != null){
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
        }
    }
}
