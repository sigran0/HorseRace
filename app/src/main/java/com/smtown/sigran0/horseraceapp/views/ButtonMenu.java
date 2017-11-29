package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smtown.sigran0.horseraceapp.R;
import com.smtown.sigran0.horseraceapp.managers.BinderManager;
import com.smtown.sigran0.horseraceapp.objects.items.LastIndex;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jungsungwoo on 11/27/17.
 */

public class ButtonMenu extends LinearLayout {

    private Context mContext;

    @BindView(R.id.view_button_menu_tv_score)
    TextView mTvScore;

    BinderManager binderManager = BinderManager.getInstance();

    Integer lastLane;

    @BindViews({R.id.view_button_menu_bt_item1, R.id.view_button_menu_bt_item2, R.id.view_button_menu_bt_item3})
    Button[] mBtItems;

    @OnClick({R.id.view_button_menu_bt_item1, R.id.view_button_menu_bt_item2, R.id.view_button_menu_bt_item3})
    void OnClickButtons(View v){

        switch (v.getId()){
            case R.id.view_button_menu_bt_item1:{
                binderManager.startUpdate("slow", lastLane);
                setButton(true);
                break;
            }

            case R.id.view_button_menu_bt_item2: {
                binderManager.startUpdate("boost", lastLane);
                setButton(true);
                break;
            }

            case R.id.view_button_menu_bt_item3: {
                binderManager.startUpdate("teleport", lastLane);
                setButton(true);
            }
        }
    }

    public ButtonMenu(Context context){

        this(context, null);
    }

    public void setScoreTextView(int score){
        mTvScore.setText(String.format("%d", score));
    }

    public void setButton(boolean itemUsed){

        if(itemUsed){

            for(Button button : mBtItems){
                button.setClickable(false);
                button.setBackgroundColor(Color.DKGRAY);
            }
        } else {
            for(Button button : mBtItems){
                button.setClickable(true);
                button.setBackgroundColor(0xff2c2ccc);
            }
        }
    }

    public ButtonMenu(Context context, AttributeSet attrs){
        super(context, attrs);

        mContext = context;
        initializeLayout();
    }

    private void initializeLayout(){
        LayoutInflater.from(mContext).inflate(R.layout.view_button_menu, this);
        ButterKnife.bind(this);

        binderManager.bind("scoreChanged", new BinderManager.BinderInterface<LastIndex>() {
            @Override
            public void update(LastIndex data) {
                LastIndex item = data;
                lastLane = data.getLaneNumber();
                setScoreTextView(data.getLaneNumber());
                setButton(item.getItemUsed());
            }
        });
    }
}
