package com.smtown.sigran0.horseraceapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.smtown.sigran0.horseraceapp.tools.BitmapProvider;
import com.smtown.sigran0.horseraceapp.tools.Constants;
import com.smtown.sigran0.horseraceapp.tools.MyTool;
import com.smtown.sigran0.horseraceapp.views.GamePanel;
import com.smtown.sigran0.horseraceapp.views.MainView;

public class MainActivity extends Activity {

    MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(new GamePanel(this));

        //mainView = (MainView) findViewById(R.id.a_main_main_view);

        Log.e("Start", "Start...");
        //mainView.init(screenWidth, screenHeight, this);
    }
}
