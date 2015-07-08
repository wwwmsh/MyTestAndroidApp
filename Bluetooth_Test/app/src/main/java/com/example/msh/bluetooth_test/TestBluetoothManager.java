package com.example.msh.bluetooth_test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanRecord;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by msh on 15. 6. 23..
 */
public class TestBluetoothManager {

    Handler handler = new Handler();
    BluetoothAdapter bltManager;

    static final int ENABLE_BLUETOOTH = 1;

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    TestBluetoothManager(Activity activity, BluetoothManager manager) {
//        bltManager = BluetoothAdapter.getDefaultAdapter();
        bltManager = manager.getAdapter();

        if (bltManager == null) {
            Log.d("", "START BLUETOOTH!!!");
        } else {
            Log.d("",":::" + bltManager.toString());

            if (bltManager.isEnabled()) {
                Log.d("","::: Enable true");
            } else {
                Log.d("","::: Enable false");

                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(i, ENABLE_BLUETOOTH);
            }
        }


    }

    boolean startFindDevices() {
        return bltManager.startDiscovery();
    }

    boolean startLeDevices(BluetoothAdapter.LeScanCallback leScanCallback) {
        return bltManager.startLeScan(leScanCallback);
    }

    boolean endFindDevices() {
        return bltManager.cancelDiscovery();
    }

    boolean isDiscovering() { return bltManager.isDiscovering(); }

    void postStopLeFindDevice(final BluetoothAdapter.LeScanCallback leScanCallback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bltManager.stopLeScan(leScanCallback);
            }
        }, 5000);
    }

    void stopLeFindDevice(BluetoothAdapter.LeScanCallback leScanCallback) {
        bltManager.stopLeScan(leScanCallback);
    }

    BluetoothDevice getDevice(String address) {
        return bltManager.getRemoteDevice(address);
    }

}
