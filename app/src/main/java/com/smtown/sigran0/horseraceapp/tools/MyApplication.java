package com.smtown.sigran0.horseraceapp.tools;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class MyApplication extends android.app.Application {

    private static Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();

        mContext = this;
        MyTool.getInstance().setApplicationContext(this);
    }
}
