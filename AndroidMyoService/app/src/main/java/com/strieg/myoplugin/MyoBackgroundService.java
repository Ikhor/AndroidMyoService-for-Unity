package com.strieg.myoplugin;

/**
 * Created by florian strieg on 20.08.2015.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.os.Binder;

import com.thalmic.myo.Hub;

public class MyoBackgroundService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private AndroidDeviceHandler mListener = new AndroidDeviceHandler();

    public class LocalBinder extends Binder {
        MyoBackgroundService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyoBackgroundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
        //return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyoTag", "onCreate was called");

        Hub hub = Hub.getInstance();
        if (!hub.init(this, getPackageName())) {
            Log.e("MyoTag", "Could not initialize the Hub.");
            stopSelf();
            return;
        }

        hub.setLockingPolicy(Hub.LockingPolicy.NONE);
        hub.getInstance().setSendUsageData(false);
        hub.addListener(mListener);
    }

    @Override
    public void onDestroy() {
        Log.e("MyoTag", "OnDestroy was called");
        super.onDestroy();
        Hub.getInstance().removeListener(mListener);
        Hub.getInstance().shutdown();
    }

    public void attachToAdjacent(){
        Hub.getInstance().attachToAdjacentMyo();
        Log.e("MyoTag", "Waiting for Myo");
    }

    public void attachByMacAddress(String mac){
        Hub.getInstance().attachByMacAddress(mac);
    }
    public void vibrate(int length){
        Log.e("MyoTag", "Vibrate called.");
        mListener.vibrate(length);
    }
}

