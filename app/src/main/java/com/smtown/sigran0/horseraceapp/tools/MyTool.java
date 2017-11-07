package com.smtown.sigran0.horseraceapp.tools;

import android.content.Context;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class MyTool {

    private static MyTool instance;

    private MyTool(){

    }

    private Context mContext;

    public static MyTool getInstance() {

        if(instance == null)
            instance = new MyTool();

        return instance;
    }

    public void setApplicationContext(Context context){
        mContext = context;
    }

    public Context getApplicationContext(){
        return mContext;
    }

    public float getDpi(){
        float dpi = mContext.getResources().getDisplayMetrics().density;

        return dpi;
    }
}
