package com.example.sagoo.servicesample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by sagoo on 2/25/2018.
 */

public class MyService extends Service {

    public int randomNumbers;
    private boolean isServiceRunning;
    private IBinder mBinder = new MyServiceBinder();

    class MyServiceBinder extends Binder{
        public MyService getMyservice(){
            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        stopRandomNumberGenerator();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isServiceRunning){
            isServiceRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startRandomNumberGenerator();
                }
            }).start();
        }
        return START_STICKY;
    }

    private void startRandomNumberGenerator() {
        while(isServiceRunning){
            try {
                Thread.sleep(1000);
                randomNumbers = new Random().nextInt(100)+1;
                Log.d("values", String.valueOf(randomNumbers));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void stopRandomNumberGenerator() {
        isServiceRunning = false;
    }

    public int getRendomNumber(){
        return randomNumbers;
    }
}
