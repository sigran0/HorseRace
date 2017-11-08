package com.smtown.sigran0.horseraceapp.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.smtown.sigran0.horseraceapp.tools.Constants;

import java.util.ArrayList;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class ObstacleManager {

    //  higher index = lower on screens = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {

        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    private void populateObstacles(){

        int currY = -5 * Constants.SCREEN_HEIGHT / 2;

        while(currY < 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){

        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGHT / 10000.0f;

        for(Obstacle obstacle : obstacles){
            obstacle.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size() - 1).getRect().top > Constants.SCREEN_HEIGHT){
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRect().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
        }
    }

    public void draw(Canvas canvas){

        for(Obstacle obstacle : obstacles)
            obstacle.draw(canvas);
    }
}