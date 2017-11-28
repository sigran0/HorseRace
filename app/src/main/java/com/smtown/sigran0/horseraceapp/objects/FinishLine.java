package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by jungsungwoo on 11/28/17.
 */

public class FinishLine extends BaseObject {

    private Paint mPaint;

    public FinishLine(RectF rectF, PointF pointF, int color){
        super(rectF, pointF);

        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    protected void initialize(){

    }

    @Override
    public void onCreate(){

    }

    @Override
    public void destroy(){

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public void update(){

    }
}
