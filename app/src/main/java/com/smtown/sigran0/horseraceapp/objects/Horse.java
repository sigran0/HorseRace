package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smtown.sigran0.horseraceapp.R;
import com.smtown.sigran0.horseraceapp.tools.MyTool;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class Horse extends AnimationEntity {

    //private static Bitmap mHorseBitmap = BitmapFactory.decodeResource(MyTool.getInstance().getApplicationContext().getResources(), R.drawable.sprite_horse);

    ArrayList<Integer> mHorseAnimationIndex = new ArrayList<>();

    public Horse(){
        this(0, 0);
    }

    public Horse(int pX, int pY){
        super(pX, pY, R.drawable.sprite_horse);
        initialize();
    }

    private void initialize(){

        for(int c = 0; c < 24; c++){
            mHorseAnimationIndex.add(c);
        }
    }

    public void setNextImage(){

        if(mSprite.getImageIndex() + 1 >= mHorseAnimationIndex.size())
            mSprite.setImageIndex(mHorseAnimationIndex.get(0));
        else
            mSprite.setImageIndex(mSprite.getImageIndex() + 1);
    }
}
