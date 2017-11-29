package com.smtown.sigran0.horseraceapp.managers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by jungsungwoo on 11/15/17.
 */

public class DialogManager {

    private Context context;

    public DialogManager(Context context){
        this.context = context;
    }

    private AlertDialog.Builder getBuilder(){

        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }

        return builder;
    }

    private void showDialogMessageWithCancel(final String title, final String message, final String ok, final String no, final OnClickEventWithCancel lisnter){
        getBuilder()
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lisnter.onClickYes();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lisnter.onClickNo();
                    }
                }).show();
    }

    private void showDialogMessageWithCancel(final String message, final String ok, final String no, final OnClickEventWithCancel lisnter){

        getBuilder()
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lisnter.onClickYes();
                    }
                })
                .setNegativeButton(no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lisnter.onClickNo();
                    }
                }).show();
    }

    private void showDialogMessage(final String title, final String message, final String ok){
        showDialogMessage(title, message, ok,null);
    }

    private void showDialogMessage(final String title, final String message, final String ok, final OnClickEvent listener){

        Handler mainHandler = new Handler(Looper.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                getBuilder()
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(listener != null)
                                    listener.onClick();
                            }
                        })
                        .show();
            }
        };
        mainHandler.post(myRunnable);
    }

    public void showResult(String result, final OnClickEvent listener){
        showDialogMessage("Ranking", result, "Ok", listener);
    }

    public interface OnClickEventWithCancel {

        public void onClickYes();
        public void onClickNo();
    }

    public interface OnClickEvent{
        public void onClick();
    }
}
