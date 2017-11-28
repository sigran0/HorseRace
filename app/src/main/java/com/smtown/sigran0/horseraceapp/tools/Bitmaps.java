package com.smtown.sigran0.horseraceapp.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smtown.sigran0.horseraceapp.R;

/**
 * Created by jungsungwoo on 11/21/17.
 */

public class Bitmaps {
    private static Resources resources = MyTools.getInstance().getContext().getResources();
    public static Bitmap BITMAP_HORSE = BitmapFactory.decodeResource(resources, R.drawable.sprite_horse);
}
