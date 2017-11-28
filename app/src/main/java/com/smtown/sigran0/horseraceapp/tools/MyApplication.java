package com.smtown.sigran0.horseraceapp.tools;

import android.app.Application;
import android.content.Context;

/**
 * Created by jungsungwoo on 11/21/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        MyTools.initialize(this);
    }
}
