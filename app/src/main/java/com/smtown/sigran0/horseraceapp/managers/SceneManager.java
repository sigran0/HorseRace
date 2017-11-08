package com.smtown.sigran0.horseraceapp.managers;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.smtown.sigran0.horseraceapp.interfaces.Scene;
import com.smtown.sigran0.horseraceapp.scenes.GameplayScene;

import java.util.ArrayList;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class SceneManager {

    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
