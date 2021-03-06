package com.smtown.sigran0.horseraceapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.smtown.sigran0.horseraceapp.managers.BinderManager;
import com.smtown.sigran0.horseraceapp.tools.Constants;
import com.smtown.sigran0.horseraceapp.views.ButtonMenu;
import com.smtown.sigran0.horseraceapp.views.GamePanel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends Activity {
    
    private static final String TAG = "fucking";

    @BindView(R.id.activity_main_button_menu)
    ButtonMenu mButtonMenu;

    @BindView(R.id.activity_main_game_panel)
    GamePanel mGamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //mGamePanel = new GamePanel(this);

        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        initialize();

        Intent intent = getIntent();
        int size = intent.getIntExtra("size", 1);

        mGamePanel.setSize(size);

        BinderManager.getInstance().bind("finish", new BinderManager.BinderInterface() {
            @Override
            public void update(Object data) {
                finish();
            }
        });

        Log.d(TAG, "onCreate called");
    }

    private void initialize(){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCRREN_HEIGHT = dm.heightPixels;
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
