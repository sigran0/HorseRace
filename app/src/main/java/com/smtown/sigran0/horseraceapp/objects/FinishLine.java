package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;

/**
 * Created by jungsungwoo on 11/28/17.
 */

public class FinishLine extends BaseObject {

    private Paint mPaint;
    private Paint mTextPaint;
    private int mIndex;

    public FinishLine(RectF rectF, PointF pointF, int color, int index){
        super(rectF, pointF);

        mPaint = new Paint();
        mPaint.setColor(color);
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mRect.width() / 2);

        mIndex = index;
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
        canvas.drawText(String.format("%d", mIndex), mRect.centerX(), mRect.centerY(), mTextPaint);
    }

    @Override
    public void update(){

    }
}
