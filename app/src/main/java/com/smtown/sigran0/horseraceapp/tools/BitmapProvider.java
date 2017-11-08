package com.smtown.sigran0.horseraceapp.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smtown.sigran0.horseraceapp.R;

/**
 * Created by jungsungwoo on 11/7/17.
 */

public class BitmapProvider {

    public static Bitmap HorseBitmap = BitmapFactory.decodeResource(MyTool.getInstance().getApplicationContext().getResources(), R.drawable.sprite_horse);
}
