package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Bitmap;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class AnimationEntity extends Entity {

    protected boolean mPauseAnimation;
    protected int mImageIndex = 0;
    protected Bitmap mBlob;
    protected Sprite mSprite;

    protected AnimationEntity(int resId){
        this(0, 0, resId);
    }

    protected AnimationEntity(int pX, int pY, int resId){
        this(pX, pY, resId, 100, 100);
    }

    protected AnimationEntity(int pX, int pY, int resId, int singleWidth, int singleHeight){
        super(pX, pY);

        mSprite = new Sprite(resId, singleWidth, singleHeight);
        mSprite.setX(pX);
        mSprite.setY(pY);
    }

    protected AnimationEntity(int pX, int pY, Bitmap bitmap, int singleWidth, int singleHeight){
        super(pX, pY);

        mSprite = new Sprite(bitmap, singleWidth, singleHeight);
        mSprite.setX(pX);
        mSprite.setY(pY);
    }

    public Sprite getSprite(){
        return mSprite;
    }
}
