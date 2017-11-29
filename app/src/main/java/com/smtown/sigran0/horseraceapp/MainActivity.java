package com.smtown.sigran0.horseraceapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    @BindView(R.id.start_button)
    Button button;

    @OnClick(R.id.start_button)
    void OnClickStart(){
        int size = numberPicker.getValue();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("size", size);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        numberPicker.setMaxValue(128);
        numberPicker.setMinValue(1);
    }
}
