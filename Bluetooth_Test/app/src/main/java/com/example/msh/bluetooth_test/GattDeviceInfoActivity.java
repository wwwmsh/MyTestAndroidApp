package com.example.msh.bluetooth_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.ParcelUuid;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class GattDeviceInfoActivity extends Activity {

    ProgressDialog progressDialog;

    TestBluetoothInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatt_device_info);
        info = (TestBluetoothInfo) getIntent().getParcelableExtra("deviceInfo");

        TextView deviceName = (TextView) findViewById(R.id.lb_DeviceName);
        deviceName.setText(info.getDevice().getName());

//        progressDialog = ProgressDialog.show(GattDeviceInfoActivity.this, "", "Conneting");
//        ParcelUuid parcelUuid = BluetoothUuid.parseUuidFrom(info.getByteUUID());
        try {
            info.getDevice().createRfcommSocketToServiceRecord(info.getParcelUuid().getUuid());
            Log.d("","createRfcommSocketToServiceRecord");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("", "" + e.getMessage());
        }
//        info.getDevice().connectGatt(this, false, gattCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gatt_menu_res, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d("", "!!!!! onConnectionStateChange");

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("", "!!!!! STATE_CONNECTED" + gatt.getServices());

                if (gatt.discoverServices()) {
                    Log.d("","!!!! discoverServices true");
                } else {
                    Log.d("","!!!! discoverServices false");
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d("", "!!!!! STATE_DISCONNECTED" + gatt.getServices());
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d("", "!!!!! onServicesDiscovered");

            final ArrayList<String> groupArray = new ArrayList<String>();
            final ArrayList<ArrayList<?>> childArray = new ArrayList<ArrayList<?>>();

            for (BluetoothGattService gattService : gatt.getServices()) {
                String serviceUUID = gattService.getUuid().toString();

                groupArray.add(serviceUUID);
                childArray.add((ArrayList<BluetoothGattCharacteristic>) gattService.getCharacteristics());

                Log.d("","!!!! gattService");
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

                    CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(GattDeviceInfoActivity.this, groupArray, childArray, R.layout.lst_expand_group_layout);
                    expandableListView.setAdapter(adapter);

                    progressDialog.dismiss();
                }
            });

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

            Log.d("", "!!!!! onCharacteristicRead");

            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d("", "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d("", "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d("", String.format("Received heart rate: %d", heartRate));
        }
    };
}
