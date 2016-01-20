package com.strieg.myoplugin;

/**
 * Created by florian strieg on 20.08.2015.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class MyoStarter {
    MyoBackgroundService mService;
    boolean mBound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyoBackgroundService.LocalBinder binder = (MyoBackgroundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            Log.e("MyoTag", "Bound.");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.e("MyoTag", "Debound.");
        }
    };

    public void launchService(Activity root){
        Intent intent = new Intent(root, MyoBackgroundService.class);
        root.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.e("MyoTag", "Binding Started.");
    }

    public void stopService(Activity root) {
        if (mBound) {
            root.unbindService(mConnection);
            mBound = false;
        }
    }

    public void attachToAdjacent(){
        if (mBound) {
            mService.attachToAdjacent();
        }
    }

    public void attachByMacAddress(String mac){
        if (mBound) {
            mService.attachByMacAddress(mac);
        }
    }

    public void vibrateForLength(int length) {
        if (mBound) {
            mService.vibrate(length);
        }
    }
}
