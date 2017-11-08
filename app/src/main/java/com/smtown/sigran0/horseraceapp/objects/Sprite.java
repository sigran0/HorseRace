package com.smtown.sigran0.horseraceapp.objects;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.smtown.sigran0.horseraceapp.tools.MyApplication;
import com.smtown.sigran0.horseraceapp.tools.MyTool;
import com.smtown.sigran0.horseraceapp.views.MainView;

import java.util.ArrayList;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class Sprite {

    private Bitmap mBlob;
    //private MainView mMainView;

    private int mSingleImageWidth;
    private int mSingleImageHeight;

    private int mFullImageWidth;
    private int mFullImageHeight;

    private int mCurrentImageIndex;

    private ArrayList<ImageIndex> mSpriteImageIndex = new ArrayList<>();

    private Rect mRectSrc;
    private Rect mRectDst;

    private int mX;
    private int mY;

//    public Sprite(MainView mainView, Bitmap blob, int singleImageWidth, int singleImageHeight){
//
//        //mMainView = mainView;
//        mBlob = blob;
//
//        mSingleImageWidth = singleImageWidth;
//        mSingleImageHeight = singleImageHeight;
//        initialize();
//    }
//
//    public Sprite(MainView mainView, int resId, int singleImageWidth, int singleImageHeight){
//
//        Bitmap bitmap = BitmapFactory.decodeResource(mainView.getResources(), resId);
//
//        mBlob = bitmap.copy(bitmap.getConfig(), true);
//
//        mSingleImageWidth = singleImageWidth;
//        mSingleImageHeight = singleImageHeight;
//        initialize();
//    }

    public Sprite(Bitmap blob, int singleImageWidth, int singleImageHeight){

        //mMainView = mainView;
        mBlob = blob;

        mSingleImageWidth = singleImageWidth;
        mSingleImageHeight = singleImageHeight;
        initialize();
    }

    public Sprite(int resId, int singleImageWidth, int singleImageHeight){

        Bitmap bitmap = BitmapFactory.decodeResource(MyTool.getInstance().getApplicationContext().getResources(), resId);

        mBlob = bitmap.copy(bitmap.getConfig(), true);

        mSingleImageWidth = singleImageWidth;
        mSingleImageHeight = singleImageHeight;
        initialize();
    }

    private void initialize(){

        int width = mBlob.getWidth();
        int height = mBlob.getHeight();
        float dpi = MyTool.getInstance().getDpi();

        mCurrentImageIndex = 0;
        mFullImageWidth = width;
        mFullImageHeight = height;

//        if(width % mSingleImageWidth != 0 || height % mSingleImageHeight != 0)
//            throw new IllegalArgumentException("Width and height should be 0 that remainder divided by width and height of single image");

        //Log.d(TAG, "initialize: bitmap width: " + mBlob.getWidth() + ", height : " + mBlob.getHeight());

        for(int y = 0; y < height; y += mSingleImageHeight * dpi){

            for(int x = 0; x < width; x += mSingleImageWidth * dpi){

                //Log.d(TAG, String.format("(%d, %d) width: %d, height: %d", x, y, mSingleImageWidth, mSingleImageHeight));

                ImageIndex imageIndex = new ImageIndex(x, y, mSingleImageWidth * dpi, mSingleImageHeight * dpi);
                mSpriteImageIndex.add(imageIndex);
            }
        }

        mRectSrc = new Rect(300, 0, 600, 300);
        mRectDst = new Rect(0, 0, 300, 300);
    }

    protected int getImageIndex(){
        return mCurrentImageIndex;
    }

    protected void setImageIndex(int index){

        if(index >= mSpriteImageIndex.size())
            throw new IndexOutOfBoundsException("Sprite:setImageIndex => ImageIndex index must less than array size");

        mCurrentImageIndex = index;
    }

    public void setX(int x) {
        mX = x;
    }

    public void setY(int y){
        mY = y;
    }

    public void onDraw(Canvas canvas){

//        ImageIndex targetIndex = mSpriteImageIndex.get(mCurrentImageIndex);
//        mRectSrc.set(targetIndex.getX(), targetIndex.getY(), targetIndex.getWidth(), targetIndex.getHeight());
//        mRectDst.set(0, 0, 100, 100);
//        mRectDst.set(targetIndex.getX(), targetIndex.getY(), targetIndex.getWidth(), targetIndex.getHeight());
        float dpi = MyTool.getInstance().getDpi();

        ImageIndex targetIndex = mSpriteImageIndex.get(mCurrentImageIndex);
        mRectDst.set(mX, mY, (int) (mX + mSingleImageWidth * dpi), (int) (mY + mSingleImageHeight * dpi));
        mRectSrc.set(targetIndex.getLeft(), targetIndex.getTop(), targetIndex.getRight(), targetIndex.getBottom());

        canvas.drawBitmap(mBlob, mRectSrc, mRectDst, null);
    }

    private class ImageIndex {

        private float width;
        private float height;
        private float x;
        private float y;
        private float dpi;

        ImageIndex(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            dpi = MyTool.getInstance().getDpi();
        }

        float getWidth(){
            return width;
        }

        float getHeight() {
            return height;
        }

        float getX(){
            return x;
        }

        float getY(){
            return y;
        }

        int getLeft() {
            return (int) x;
        }

        int getTop(){
            return (int) y;
        }

        int getRight(){
            return (int) (x + width);
        }

        int getBottom(){
            return (int) (y + height);
        }
    }
}
