package com.smtown.sigran0.horseraceapp.tools;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by jungsungwoo on 11/21/17.
 */

public class MyTools {

    private static Context mContext;
    private static MyTools instance;

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

    private void getScreenSize() throws NullPointerException{
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }
}
