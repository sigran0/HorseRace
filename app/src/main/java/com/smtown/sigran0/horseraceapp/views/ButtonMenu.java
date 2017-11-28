package com.smtown.sigran0.horseraceapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smtown.sigran0.horseraceapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jungsungwoo on 11/27/17.
 */

public class ButtonMenu extends LinearLayout {

    private Context mContext;

    @OnClick({R.id.view_button_menu_bt_item1, R.id.view_button_menu_bt_item2, R.id.view_button_menu_bt_item3})
    void OnClickButtons(){
        Toast.makeText(mContext, "Hello World!", Toast.LENGTH_SHORT).show();
    }

    public ButtonMenu(Context context){

        this(context, null);
    }

    public ButtonMenu(Context context, AttributeSet attrs){
        super(context, attrs);

        mContext = context;
        initializeLayout();
    }

    private void initializeLayout(){
        LayoutInflater.from(mContext).inflate(R.layout.view_button_menu, this);
        ButterKnife.bind(this);
    }
}
