package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.smtown.sigran0.horseraceapp.interfaces.GameObject;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class Obstable implements GameObject{

    private Rect rect;
    private int color;

    public Obstable(Rect rect, int color){
        this.rect = rect;
        this.color = color;
    }

    public boolean playerCollide(RectPlayer player){
        if(rect.contains(player.getRect().left, player.getRect().top)
                ||  rect.contains(player.getRect().right, player.getRect().top)
                ||  rect.contains(player.getRect().left, player.getRect().bottom)
                ||  rect.contains(player.getRect().right, player.getRect().bottom))
            return true;
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(){

    }
}
