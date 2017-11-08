package com.smtown.sigran0.horseraceapp.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.smtown.sigran0.horseraceapp.interfaces.Scene;
import com.smtown.sigran0.horseraceapp.managers.ObstacleManager;
import com.smtown.sigran0.horseraceapp.managers.SceneManager;
import com.smtown.sigran0.horseraceapp.objects.RectPlayer;
import com.smtown.sigran0.horseraceapp.tools.Constants;

/**
 * Created by jungsungwoo on 11/8/17.
 */

public class GameplayScene implements Scene {

    private Rect r = new Rect();

    private ObstacleManager obstacleManager;
    private RectPlayer player;
    private Point playerPoint;

    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private long gameOverTime;

    public GameplayScene(){
        SceneManager.ACTIVE_SCENE = 0;

        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.RED);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;
        gameOver = false;
    }

    @Override
    public void recieveTouch(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRect().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                if (!gameOver && movingPlayer)
                    playerPoint.set((int) event.getX(), (int) event.getY());
                break;
            }
            case MotionEvent.ACTION_UP: {
                movingPlayer = false;
                break;
            }

        }
    }

    @Override
    public void terminate(){

    }

    @Override
    public void draw(Canvas canvas){

        canvas.drawColor(Color.WHITE);

        obstacleManager.draw(canvas);
        player.draw(canvas);

        if(gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);

            drawCenterText(canvas, paint, "Game Over");
        }
    }

    @Override
    public void update(){

        if(!gameOver) {
            obstacleManager.update();
            player.update(playerPoint);
            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
