package com.cuberpunk.iconpack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import java.lang.Exception;
import android.util.Log;
 
public class widget extends AppWidgetProvider {
 
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
        int[] appWidgetIds) {
            try{
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        } catch (Exception e) {
        Log.e("cyberpunk",e.getMessage());
    }
    //Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.google.android.calendar");
        //context.startActivity(launchIntent);
        
    }
}