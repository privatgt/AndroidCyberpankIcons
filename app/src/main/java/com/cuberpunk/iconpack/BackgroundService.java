package com.cuberpunk.iconpack;


import android.content.IntentFilter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.cuberpunk.iconpack.onBatteryCharge;
import android.app.Notification;
import java.lang.Exception;


public class BackgroundService extends Service {
    private onBatteryCharge batterychange = null;
    public BackgroundService() {
        
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            Log.i("cyberpunk","service is ok");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
            batterychange = new onBatteryCharge();
            registerReceiver(batterychange, intentFilter);
        } catch (Exception e) {
            Log.e("cyberpunk",e.getMessage());
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            unregisterReceiver(this.batterychange);
    }
            
}