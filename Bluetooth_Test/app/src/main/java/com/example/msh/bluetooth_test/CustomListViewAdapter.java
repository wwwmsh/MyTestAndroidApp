package com.example.msh.bluetooth_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by msh on 15. 6. 23..
 */
public class CustomListViewAdapter extends TestBaseAdapter {

    CustomListViewAdapter (Activity act, ArrayList list, int resid) {
        super(act,list,resid);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            v = LayoutInflater.from(activity).inflate(resourceid, null);
            v.setTag(R.id.lb_DeviceName, v.findViewById(R.id.lb_DeviceName));
            v.setTag(R.id.lb_DeviceMac, v.findViewById(R.id.lb_DeviceMac));
            v.setTag(R.id.lb_DeviceUUID, v.findViewById(R.id.lb_DeviceUUID));
            v.setTag(R.id.lb_DeviceService, v.findViewById(R.id.lb_DeviceService));


        }



        TestBluetoothInfo device = (TestBluetoothInfo) arrayList.get(position);

        if (device != null) {

            Log.d("",""+ device.getDevice().getName() + "/" + " position : "+position);

            if (position%2 == 0) {
                v.setBackgroundColor(Color.DKGRAY);
            } else {
                v.setBackgroundColor(Color.BLACK);
            }

            TextView deviceNameTextView = (TextView) v.getTag(R.id.lb_DeviceName);
            deviceNameTextView.setText(device.getDevice().getName());

            TextView deviceMacTextView = (TextView) v.getTag(R.id.lb_DeviceMac);
            deviceMacTextView.setText(device.getDevice().getAddress());

            TextView deviceUUID= (TextView) v.getTag(R.id.lb_DeviceUUID);
            deviceUUID.setText(device.getUUID());

            TextView deviceService= (TextView) v.getTag(R.id.lb_DeviceService);
            deviceService.setText("Service Count : " + device.getDeviceScanRecord().getServiceData().size());

        }

        return v;
    }

    boolean add(Object ob) {

        if (arrayList.contains(ob)) {
            return false;
        }

        arrayList.add(ob);

        return true;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    public void clear() {
        arrayList.clear();
    }

    Comparator<BluetoothDevice> deviceSorter = new Comparator<BluetoothDevice>() {
        @Override
        public int compare(BluetoothDevice device1, BluetoothDevice device2) {

            return device1.getName().compareTo(device2.getName());
        }
    };
}
