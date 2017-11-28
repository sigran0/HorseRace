package com.smtown.sigran0.horseraceapp.animation;

import android.graphics.Bitmap;

/**
 * Created by jungsungwoo on 11/21/17.
 */

public class SpriteSheet {

    private Bitmap mBlob;
    private int mBaseWidth;
    private int mBaseHeight;
    private int mSingleWidth;
    private int mSingleHeight;

    public SpriteSheet(Bitmap blob, int baseWidth, int baseHeight, int singleWidth, int singleHeight){
        mBlob = blob;
        mBaseWidth = baseWidth;
        mBaseHeight = baseHeight;
        mSingleWidth = singleWidth;
        mSingleHeight = singleHeight;
    }
}
