package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.smtown.sigran0.horseraceapp.interfaces.GameObject;
import com.smtown.sigran0.horseraceapp.tools.Constants;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class Obstacle implements GameObject{

    private Rect rect;
    private Rect rect2;
    private int color;
    private int startX;
    private int playerGap;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap){
        this.color = color;

        //  l, t, r, b
        rect = new Rect(0, startY, startX, startY + rectHeight);
        rect2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public Rect getRect(){
        return rect;
    }

    public void incrementY(float y){
        rect.top += y;
        rect.bottom += y;

        rect2.top += y;
        rect2.bottom += y;
    }

    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rect, player.getRect()) || Rect.intersects(rect2, player.getRect());
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
        canvas.drawRect(rect2, paint);
    }

    @Override
    public void update(){

    }
}
