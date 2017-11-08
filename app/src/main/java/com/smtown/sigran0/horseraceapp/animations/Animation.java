package com.smtown.sigran0.horseraceapp.animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying = false;
    public boolean isPlaying() {
        return isPlaying;
    }

    public void play(){
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop(){
        isPlaying = false;
    }

    private float frameTime;

    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime){
        this.frames = frames;
        frameIndex = 0;

        frameTime = animTime / frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect dest){

        if(!isPlaying)
            return;

        scaleRect(dest);

        canvas.drawBitmap(frames[frameIndex], null, dest, new Paint());
    }

    private void scaleRect(Rect rect){
        float whRatio = (float) frames[frameIndex].getWidth() / frames[frameIndex].getHeight();

        if(rect.width() > rect.height()){
            rect.left = rect.right - (int) (rect.height() * whRatio);
        } else
            rect.top = rect.bottom - (int) (rect.width() * whRatio);
    }

    public void update(){

        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}