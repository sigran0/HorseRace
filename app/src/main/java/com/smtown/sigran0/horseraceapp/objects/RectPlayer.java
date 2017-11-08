package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.smtown.sigran0.horseraceapp.interfaces.GameObject;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class RectPlayer implements GameObject {

    private Rect rect;
    private int color;

    public RectPlayer(Rect rect, int color){

        this.rect = rect;
        this.color = color;
    }

    public Rect getRect(){
        return rect;
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

    public void update(Point point){

        //  l, t, r, b
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
    }
}
