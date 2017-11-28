package com.smtown.sigran0.horseraceapp.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by jungsungwoo on 11/27/17.
 */

public interface GameScene {

    public void update();
    public void draw(Canvas canvas);
    public void onTouchEvent(MotionEvent event);
    public void destroy();
}
