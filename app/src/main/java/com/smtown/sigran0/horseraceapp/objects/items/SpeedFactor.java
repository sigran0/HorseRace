package com.smtown.sigran0.horseraceapp.objects.items;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.smtown.sigran0.horseraceapp.objects.Horse;
import com.smtown.sigran0.horseraceapp.tools.Constants;

/**
 * Created by jungsungwoo on 11/28/17.
 */

public class SpeedFactor extends Item {

    private float mForceSize;

    public SpeedFactor(RectF rectF, PointF pointF){
        super(rectF, pointF);

        mForceSize = tools.getRangeFloat(0, Constants.HORSE_ADD_FORCE);
        int minusFactor = tools.getRangeInt(1, 10);

        if(minusFactor > 8)
            mForceSize *= -1;

        if(mForceSize < 0)
            mPaint.setColor(Color.RED);
        else
            mPaint.setColor(Color.YELLOW);
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

    @Override
    public void onTouch(Horse player){
        player.addForce(mForceSize);
    }
}
