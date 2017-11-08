package com.smtown.sigran0.horseraceapp.tools;

import android.content.Context;
import android.util.Log;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class MyTool {

    private static MyTool instance;
    private static final String TAG = "MyTool";

    private MyTool(){

    }

    private Context mContext;
    private long mCurrentTime;
    private boolean mMeasurePerformance = false;

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

    public void startMeasurePerformance(){

        if(mMeasurePerformance)
            throw new IllegalStateException("This method is already called before");

        mMeasurePerformance = true;
        mCurrentTime = System.currentTimeMillis();
    }

    public long getMeasurePerformance(){

        if(!mMeasurePerformance)
            throw new IllegalStateException("You must call startMeasurePerformance method before use this method");

        mMeasurePerformance = false;
        return System.currentTimeMillis() - mCurrentTime;
    }

    public void logMeasurePerformance() {

        double runtime = getMeasurePerformance() / 1000f;

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTraceElements[stackTraceElements.length - 2];

        String methodName = element.getMethodName();
        String className = element.getClassName();
        int lineNumber = element.getLineNumber();

        Log.d(TAG, String.format("logMeasurePerformance: <%s> : [%s : %d] runtime : %f", className, methodName, lineNumber, runtime));
    }
}
