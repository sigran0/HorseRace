package com.smtown.sigran0.horseraceapp.tools;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jungsungwoo on 11/21/17.
 */

public class MyTools {

    private static Context mContext;
    private static MyTools instance;
    private static Random random = new Random();

    private int mScreenWidth = -1;
    private int mScreenHeight = -1;

    private MyTools(Context context){
        mContext = context;
    }

    public static MyTools getInstance(){
        if(instance == null)
            throw new NullPointerException("You must call initialize before use getInstance()");

        return instance;
    }

    public static void initialize(Context context){
        instance = new MyTools(context);
    }

    public Context getContext(){
        return mContext;
    }

    public int getScreenWidth() {

        if(mScreenWidth < 0 || mScreenHeight < 0)
            getScreenSize();
        return mScreenWidth;
    }

    public int getScreenHeight(){
        if(mScreenWidth < 0 || mScreenHeight < 0)
            getScreenSize();
        return mScreenHeight;
    }

    public float getRangeFloat(float min, float max){

        return (Math.abs(random.nextFloat()) * (max - min)) + min;
    }

    public int getRangeInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void getScreenSize() throws NullPointerException{
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }
}
