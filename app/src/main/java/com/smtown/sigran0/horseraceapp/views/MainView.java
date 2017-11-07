package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smtown.sigran0.horseraceapp.MainActivity;
import com.smtown.sigran0.horseraceapp.objects.Horse;
import com.smtown.sigran0.horseraceapp.threads.MainThread;
import com.smtown.sigran0.horseraceapp.tools.ScreenConfig;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MainView";

    private MainActivity m_main_activity;
    private MainThread m_main_thread;
    private Handler m_handler;
    private Context m_context;
    private boolean m_draw_cls = false;
    private ScreenConfig m_screen_config;

    private Horse m_horse1;
    private Horse m_horse2;
    private Horse m_horse3;
    private Horse m_horse4;
    private Horse m_horse5;

    public MainView(Context context){
        this(context, null);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
        m_main_thread = new MainThread(getHolder(), this);
        setFocusable(true);
        m_context = context;
    }

    public void init(int width, int height, MainActivity mainActivity) {

        this.m_main_activity = mainActivity;
        m_screen_config = new ScreenConfig(width, height);
        m_screen_config.setSize(width, height);
        m_draw_cls = true;

        m_horse1 = new Horse(0, 0);
        m_horse2 = new Horse(0, 200);
        m_horse3 = new Horse(0, 400);
        m_horse4 = new Horse(0, 600);
        m_horse5 = new Horse(0, 800);
    }

    @Override
    public void onDraw(Canvas canvas) {

        if(m_draw_cls == false)
            return;

        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        m_horse1.setX(m_horse1.getX() + 10);
        m_horse1.getSprite().setX(m_horse1.getX() + 10);

        if(m_horse1.getX() >= canvas.getWidth()) {
            m_horse1.setX(0);
            m_horse1.getSprite().setX(0);
        }

        m_horse1.setNextImage();
        m_horse1.getSprite().onDraw(canvas);

        m_horse2.setX(m_horse2.getX() + 15);
        m_horse2.getSprite().setX(m_horse2.getX() + 15);

        if(m_horse2.getX() >= canvas.getWidth()) {
            m_horse2.setX(0);
            m_horse2.getSprite().setX(0);
        }

        m_horse2.setNextImage();
        m_horse2.getSprite().onDraw(canvas);

        m_horse3.setX(m_horse3.getX() + 20);
        m_horse3.getSprite().setX(m_horse3.getX() + 20);

        if(m_horse3.getX() >= canvas.getWidth()) {
            m_horse3.setX(0);
            m_horse3.getSprite().setX(0);
        }

        m_horse3.setNextImage();
        m_horse3.getSprite().onDraw(canvas);

        m_horse4.setX(m_horse4.getX() + 25);
        m_horse4.getSprite().setX(m_horse4.getX() + 25);

        if(m_horse4.getX() >= canvas.getWidth()) {
            m_horse4.setX(0);
            m_horse4.getSprite().setX(0);
        }

        m_horse4.setNextImage();
        m_horse4.getSprite().onDraw(canvas);

        m_horse5.setX(m_horse5.getX() + 30);
        m_horse5.getSprite().setX(m_horse5.getX() + 30);

        if(m_horse5.getX() >= canvas.getWidth()) {
            m_horse5.setX(0);
            m_horse5.getSprite().setX(0);
        }

        m_horse5.setNextImage();
        m_horse5.getSprite().onDraw(canvas);
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
                m_main_thread = new MainThread(getHolder(), this);
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
