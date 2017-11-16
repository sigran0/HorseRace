package com.smtown.sigran0.horseraceapp.managers;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.smtown.sigran0.horseraceapp.animations.Animation;

/**
 * Created by jungsungwoo on 11/8/17.
 */
public class AnimationManager {

    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    public void playAnimation(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if(!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, Rect rect) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, rect);
    }

    public void update() {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }
}