package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.smtown.sigran0.horseraceapp.R;
import com.smtown.sigran0.horseraceapp.animations.Animation;
import com.smtown.sigran0.horseraceapp.interfaces.GameObject;
import com.smtown.sigran0.horseraceapp.managers.AnimationManager;
import com.smtown.sigran0.horseraceapp.tools.Constants;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class RectPlayer implements GameObject {

    private Rect rect;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animationManager;

    public RectPlayer(Rect rect, int color){

        this.rect = rect;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();

        Bitmap idleBitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alien_blue);
        Bitmap walk1Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alien_blue_walk_1);
        Bitmap walk2Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alien_blue_walk_2);

        idle = new Animation(new Bitmap[]{idleBitmap}, 2.f);
        walkRight = new Animation(new Bitmap[]{walk1Bitmap, walk2Bitmap}, 0.5f);

        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        walk1Bitmap = Bitmap.createBitmap(walk1Bitmap, 0, 0, walk1Bitmap.getWidth(), walk1Bitmap.getHeight(), matrix, false);
        walk2Bitmap = Bitmap.createBitmap(walk2Bitmap, 0, 0, walk2Bitmap.getWidth(), walk2Bitmap.getHeight(), matrix, false);
        walkLeft = new Animation(new Bitmap[]{walk1Bitmap, walk2Bitmap}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }

    public Rect getRect(){
        return rect;
    }

    @Override
    public void draw(Canvas canvas){

//        Paint paint = new Paint();
//        paint.setColor(color);
//        canvas.drawRect(rect, paint);
        animationManager.draw(canvas, rect);
    }

    @Override
    public void update(){
        animationManager.update();
    }

    public void update(Point point){

        float oldLeft = rect.left;

        //  l, t, r, b
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);

        int state = 0;

        if(rect.left - oldLeft > 5){
            state = 1;
        } else if(rect.left - oldLeft < -5) {
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();
    }
}
