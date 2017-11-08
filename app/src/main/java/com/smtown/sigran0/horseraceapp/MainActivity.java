package com.smtown.sigran0.horseraceapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.smtown.sigran0.horseraceapp.tools.BitmapProvider;
import com.smtown.sigran0.horseraceapp.tools.MyTool;
import com.smtown.sigran0.horseraceapp.views.GamePanel;
import com.smtown.sigran0.horseraceapp.views.MainView;

public class MainActivity extends Activity {
    
    private static final String TAG = "fucking";

    MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));

        //Log.d("fucking", "onCreate: Horse Bitmap : " + BitmapProvider.HorseBitmap);
        //Log.d(TAG, "onCreate: dpi : " + MyTool.getInstance().getDpi());

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        //mainView = (MainView) findViewById(R.id.a_main_main_view);

        Log.e("Start", "Start...");
        //mainView.init(screenWidth, screenHeight, this);
    }
}
