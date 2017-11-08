package com.smtown.sigran0.horseraceapp.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public interface Scene {

    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
