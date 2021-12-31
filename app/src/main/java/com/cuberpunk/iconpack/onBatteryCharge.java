package com.cuberpunk.iconpack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.lang.Exception;
import android.util.Log;
import android.view.WindowManager;
import android.content.res.Configuration;

public class onBatteryCharge extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Log.i("cyberpunk","charging started");
            //Intent chargingIntent=new Intent(context, ChargingActivity.class);
            //chargingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //chargingIntent.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            //context.startActivity(chargingIntent);
            int orient = context.getResources().getConfiguration().orientation;
            if(orient == Configuration.ORIENTATION_PORTRAIT){
                Intent chargingIntent=new Intent(context, ChargingActivity.class);
                chargingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                chargingIntent.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                context.startActivity(chargingIntent);
            }
                //stopForeground(true);
        } catch (Exception e) {
            Log.e("cyberpunk",e.getMessage());
        } 
    }
}