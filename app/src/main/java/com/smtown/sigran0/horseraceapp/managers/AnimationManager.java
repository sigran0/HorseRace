package com.smtown.sigran0.horseraceapp.managers;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.smtown.sigran0.horseraceapp.animations.Animation;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class AnimationManager {

    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations){
        this.animations = animations;
    }

    public void playAnimation(int index){

        for(int c = 0; c < animations.length; c++){

            if(c == index) {
                if(animations[index].isPlaying())
                    animations[c].play();
            } else
                animations[c].stop();
        }

        animationIndex = index;
    }

    public void draw(Canvas canvas, Rect rect){
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, rect);
    }

    public void update() {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }
}
