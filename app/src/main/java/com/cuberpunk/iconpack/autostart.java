package com.cuberpunk.iconpack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.lang.Exception;


public class autostart extends BroadcastReceiver 
{
    public void onReceive(Context context, Intent arg1) 
    {
        try{
        Intent intent = new Intent(context,BackgroundService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
        Log.i("cyberpunk", "Autostart started");
    } catch (Exception e) {
        Log.e("cyberpunk",e.getMessage());
    }
    }
}