package com.cuberpunk.iconpack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.lang.Exception;
import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import java.lang.Exception;
import java.util.Timer;
import java.util.TimerTask;

public class ChargingActivity extends Activity {
    AnimationDrawable ChargeAnimation;
    Timer timer;
    ActvityFinish finishactivity;
    @Override
    public void onCreate(Bundle bundle) {
        try{
            super.onCreate(bundle);
            Log.i("cyberpunk","activity started");
            setContentView(R.layout.animation); 
            ImageView imageView = (ImageView) findViewById(R.id.chargingImage);
            imageView.setBackgroundResource(R.drawable.sb_charge);
            ChargeAnimation = (AnimationDrawable) imageView.getBackground();
            ChargeAnimation.setOneShot(true);
            int duration = 0;
        for(int i = 0; i < ChargeAnimation.getNumberOfFrames(); i++){
         duration += ChargeAnimation.getDuration(i);
        }
        
        timer = new Timer();
        finishactivity = new ActvityFinish();
        timer.schedule(finishactivity, duration);
        ChargeAnimation.start();
        } catch (Exception e) {
            Log.e("cyberpunk",e.getMessage());
        } 
    }
class ActvityFinish extends TimerTask {

    @Override
    public void run() {
     
     timer.cancel();
     runOnUiThread(new Runnable(){
      @Override
      public void run() {
       finish();
      }});
    }  
   }
  }