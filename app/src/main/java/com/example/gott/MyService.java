package com.example.gott;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import javax.net.ssl.SNIHostName;

public class MyService extends Service {

    private static final String TAG = "MyService";

    private IBinder mBinder = new MyBinder();
    private Handler mHandler;
    private int mProgress, mMaxValue;
    private boolean isPaused;

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mProgress = 0;
        mMaxValue = 5000;
        isPaused = true;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    // methods for counter..
     public void startLongTask(){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mProgress >= mMaxValue || isPaused){

                    Log.d(TAG, "run: Removing the call backs and stopping the counter.");
                    mHandler.removeCallbacks(this);

                    pauseLongTask();
                }else{
                    Log.d(TAG, "run: mProgress" + mProgress);
                    mProgress += 100;
                    mHandler.postDelayed(this, 100);
                }
            }
        };
        mHandler.postDelayed(runnable, 100);
     }

    private void pauseLongTask() {
        isPaused = true;
    }

    private void restartLongTask(){
        isPaused = false;
        startLongTask();
    }

    // METHODS TO RETRIEVE VALUES FROM THE SERVICE
    public boolean getIsPaused(){
        return isPaused;
    }

    public int getProgres(){
        return mProgress;
    }

    public int getMaxValue(){
        return mMaxValue;
    }

    public void resetTask(){
        mProgress = 0;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }
}
