package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smtown.sigran0.horseraceapp.MainActivity;
import com.smtown.sigran0.horseraceapp.objects.Horse;
import com.smtown.sigran0.horseraceapp.threads.MainThreadBefore;
import com.smtown.sigran0.horseraceapp.tools.ScreenConfig;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MainView";
    private static final int HORSE_SIZE = 100;

    private MainActivity m_main_activity;
    private MainThreadBefore m_main_thread;
    private Handler m_handler;
    private Context m_context;
    private boolean m_draw_cls = false;
    private ScreenConfig m_screen_config;

    private Horse[] m_horses = new Horse[HORSE_SIZE];

    public MainView(Context context){
        this(context, null);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
        m_main_thread = new MainThreadBefore(getHolder(), this);
        setFocusable(true);
        m_context = context;
    }

    public void init(int width, int height, MainActivity mainActivity) {

        this.m_main_activity = mainActivity;
        m_screen_config = new ScreenConfig(width, height);
        m_screen_config.setSize(width, height);
        m_draw_cls = true;

        for(int c = 0; c < HORSE_SIZE; c++){
            m_horses[c] = new Horse(0, c * 150);
        }
    }

    public void nextTick(){

        for(int c = 0; c < HORSE_SIZE; c++){
            m_horses[c].setPosition(m_horses[c].getXPosiotion() + (10 + c * 5), m_horses[c].getYPosition());

            if(m_horses[c].getXPosiotion() >= this.getWidth()) {
                m_horses[c].setPosition(0, m_horses[c].getYPosition());
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

        if(m_draw_cls == false)
            return;

        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        for(int c = 0; c < HORSE_SIZE; c++){
            m_horses[c].setNextImage();
            m_horses[c].getSprite().onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_main_thread.setRunning(true);

        try {
            if(m_main_thread.getState() == Thread.State.TERMINATED) {
                m_main_thread = new MainThreadBefore(getHolder(), this);
                m_main_thread.setRunning(true);
                setFocusable(true);
                m_main_thread.start();
            } else {
                m_main_thread.start();
            }

        } catch (Exception e) {
            Log.e(TAG, "surfaceCreated: ", e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        m_main_thread.setRunning(false);
        while (retry){
            try {
                m_main_thread.join();
                retry = false;
            } catch (Exception e) {
                Log.i(TAG, "surfaceDestroyed: Surface Destroy Exception" + e.toString());
            }
        }
    }
}
