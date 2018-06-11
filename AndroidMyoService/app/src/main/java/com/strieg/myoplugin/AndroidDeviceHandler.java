package com.strieg.myoplugin;

/**
 * Created by florian strieg on 10.09.2015.
 * Updated by Ikhor on 06.11.2017
 */

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.XDirection;
import com.unity3d.player.UnityPlayer;

public class AndroidDeviceHandler extends AbstractDeviceListener {
    private Myo myo;

    private static Myo.VibrationType getType(int x) {
        switch (x) {
            case 0:
                return Myo.VibrationType.SHORT;
            case 1:
                return Myo.VibrationType.MEDIUM;
            case 2:
                return Myo.VibrationType.LONG;
        }
        return null;
    }

    @Override
    public void onAttach(Myo myo, long timestamp) {
        this.myo = myo;
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnAttach", "");
    }

    @Override
    public void onDetach(Myo myo, long timestamp) {
        this.myo = null;
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnDetach", "");
    }

    @Override
    public void onConnect(Myo myo, long timestamp) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnConnect", "");
    }

    @Override
    public void onDisconnect(Myo myo, long timestamp) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnDisconnect", "");
    }

    @Override
    public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnArmSync", "");
    }

    @Override
    public void onArmUnsync(Myo myo, long timestamp) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnArmUnsync", "");
    }

    @Override
    public void onUnlock(Myo myo, long timestamp) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnUnlock", "");
    }

    @Override
    public void onLock(Myo myo, long timestamp) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnLock", "");
    }

    @Override
    public void onPose(Myo myo, long timestamp, Pose pose) {
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnPose", pose.toString());
    }

    @Override
    public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
        String quaternion = String.valueOf(rotation.x()) + "," + String.valueOf(rotation.y()) + ","
                + String.valueOf(rotation.z()) + "," + String.valueOf(rotation.w());

        UnityPlayer.UnitySendMessage("MyoAndroidManager", "OnOrientationData", quaternion);
    }

    @Override
    public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
        String acellData = String.valueOf(accel.x()) + "," + String.valueOf(accel.y()) + "," + String.valueOf(accel.z());
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "onAccelerometerData", acellData);
    }
    /*
    @Override
    public void onRssi (Myo myo, long timestamp, int rssi){}*/

    @Override
    public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
        String gyroData = String.valueOf(gyro.x()) + "," + String.valueOf(gyro.y()) + "," + String.valueOf(gyro.z());
        UnityPlayer.UnitySendMessage("MyoAndroidManager", "onGyroscopeData", gyroData);
    }

    public void vibrate(int length) {
        Myo.VibrationType type = getType(length);
        if (type != null) myo.vibrate(type);
    }
}
