package com.example.msh.bluetooth_test;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by msh on 15. 7. 6..
 */
public class TestBluetoothInfo implements Parcelable {

    BluetoothDevice device;
    String UUID;
    ScanRecord deviceScanRecord;
    ParcelUuid parcelUuid;

    public TestBluetoothInfo() {

    }

    public TestBluetoothInfo(Parcel source) {
        device = (BluetoothDevice) source.readValue(BluetoothDevice.class.getClassLoader());
        UUID = source.readString();
        parcelUuid = source.readParcelable(ParcelUuid.class.getClassLoader());
//        parcelUuid = (ParcelUuid) source.readValue(ParcelUuid.class.getClassLoader());
        Log.d("","...");
    }

    public ParcelUuid getParcelUuid() {
        return parcelUuid;
    }

    public void setParcelUuid(ParcelUuid parcelUuid) {
        this.parcelUuid = parcelUuid;
    }

    public ScanRecord getDeviceScanRecord() {
        return deviceScanRecord;
    }

    public void setDeviceScanRecord(ScanRecord deviceScanRecord) {
        this.deviceScanRecord = deviceScanRecord;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(device);
        dest.writeString(UUID);
//        dest.writeValue(parcelUuid);
        dest.writeParcelable(parcelUuid,flags);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public TestBluetoothInfo createFromParcel(Parcel source) {
            return new TestBluetoothInfo(source);
        }

        @Override
        public TestBluetoothInfo[] newArray(int size) {
            return new TestBluetoothInfo[size];
        }
    };
}
