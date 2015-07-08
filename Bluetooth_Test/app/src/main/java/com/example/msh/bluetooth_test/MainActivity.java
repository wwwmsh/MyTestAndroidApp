package com.example.msh.bluetooth_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bluetoothgatt.AdRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, BluetoothAdapter.LeScanCallback {

    TestBluetoothInfo selectInfo;

    TestBluetoothManager manager;

    ArrayList<TestBluetoothInfo> arrayDevices = new ArrayList<TestBluetoothInfo>();
    ArrayList<BluetoothDevice> findArrayDevices = new ArrayList<BluetoothDevice>();

    CustomListViewAdapter listAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.findBluetoothDevice);
        button.setOnClickListener(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listAdapter = new CustomListViewAdapter(this, arrayDevices, R.layout.lst_listview_layout);
        listView.setAdapter(listAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        registerReceiver(findBluetoothReceiver, filter);

        
        manager = new TestBluetoothManager(this, (BluetoothManager) getSystemService(BLUETOOTH_SERVICE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TestBluetoothManager.ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                    Log.d("","RESULT OK");
                } else {
                    Log.d("","RESULT FAIL");
                }
                break;
            default:
        }
    }

    final BroadcastReceiver findBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.d("","!!! Action : "+action);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BluetoothClass bClass = intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS);
                intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);


                Log.d("", "!!!" + device.getName() + "::" + device.getAddress() + "::" + device.toString());

                listAdapter.add(device);
                listAdapter.notifyDataSetChanged();

                if (device.getUuids() != null) {
                    for (ParcelUuid parcelUuid : device.getUuids()) {
                        Log.d("", "!!!" + parcelUuid.getUuid().toString());
                    }
                } else {
                    Log.d("","::: UUID NULL");
//                    if (device.fetchUuidsWithSdp()) {
//                        Log.d("","::: fetchUuidsWithSdp true");
//                    } else {
//                        Log.d("","::: fetchUuidsWithSdp false");
//                    }
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
//                if (manager.endFindDevices()) {
//                    Log.d("",":::EndFindDevices True");
//                } else {
//                    Log.d("",":::EndFindDevices False");
//                }
                Log.d("",":::EndFindDevices");

//                for (BluetoothDevice device : arrayDevices) {
//                    if (device.fetchUuidsWithSdp()) {
//                        Log.d("","::: fetchUuidsWithSdp true");
//                    } else {
//                        Log.d("", "::: fetchUuidsWithSdp false");
//                    }
//                }

                if (manager.isDiscovering()) {
                    Log.d("",":::isDiscovering");
                    manager.endFindDevices();
                } else {
//                    arrayDevices.get(0).fetchUuidsWithSdp();
                }

            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);

                Log.d("",":::" + String.valueOf(state));


            } else if (BluetoothDevice.ACTION_UUID.equals(action)) {
                Log.d("",":::" + BluetoothDevice.ACTION_UUID);

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getUuids() != null) {
                    for (ParcelUuid parcelUuid : device.getUuids()) {
                        Log.d("", "!!!" + parcelUuid.getUuid().toString());
                    }
                } else {
                    Log.d("","::: UUID NULL");
                }


            }
        }
    };

    @Override
    public void onClick(View v) {

//        if (manager.startFindDevices()) {
        if (manager.startLeDevices(this)) {
            Log.d("", "DEVICE FINDING!!!");

//            arrayDevices.clear();
//            listAdapter.notifyDataSetChanged();
            findArrayDevices.clear();

            listAdapter.clear();
            listAdapter.notifyDataSetChanged();

            manager.postStopLeFindDevice(this);
        } else {
            Log.d("","FIND FAIL!!!");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("", "::: itemClick");

        manager.stopLeFindDevice(this);

        TestBluetoothInfo device = (TestBluetoothInfo) parent.getAdapter().getItem(position);
        BluetoothDevice device2 = manager.getDevice(device.getDevice().getAddress());

//        BluetoothGatt gatt = device.getDevice().connectGatt(this, false, gattCallback);



        TestBluetoothInfo info = new TestBluetoothInfo();
        info.setDevice(device.getDevice());
        info.setUUID(device.getUUID());
        info.setParcelUuid(device.getParcelUuid());

        Intent i = new Intent(MainActivity.this, GattDeviceInfoActivity.class);
        i.putExtra("deviceInfo", info);

        startActivity(i);

    }

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi,final byte[] record) {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                if (!findArrayDevices.contains(device)) {
                    findArrayDevices.add(device);

                    TestBluetoothInfo info = new TestBluetoothInfo();
                    info.setDevice(device);

                    Log.d("", "!!!!!!!!!!" + device.getName() + "::::" + device.toString());

                    int startByte = 2;
                    boolean patternFound = false;
                    while (startByte <= 5) {
                        if (((int) record[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                                ((int) record[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                            patternFound = true;
                            break;
                        }
                        startByte++;
                    }

                    if (patternFound) {
                        //Convert to hex String
                        byte[] uuidBytes = new byte[16];
                        System.arraycopy(record, startByte + 4, uuidBytes, 0, 16);
                        String hexString = bytesToHex(uuidBytes);

                        //Here is your UUID
                        String uuid = hexString.substring(0, 8) + "-" +
                                hexString.substring(8, 12) + "-" +
                                hexString.substring(12, 16) + "-" +
                                hexString.substring(16, 20) + "-" +
                                hexString.substring(20, 32);

                        //Here is your Major value
                        int major = (record[startByte + 20] & 0xff) * 0x100 + (record[startByte + 21] & 0xff);

                        //Here is your Minor value
                        int minor = (record[startByte + 22] & 0xff) * 0x100 + (record[startByte + 23] & 0xff);

                        String strScanData = "";
                        String strHexScanData = bytesToHex(record);
                        int count = 0;
                        for (byte b : record) {
                            String strByte = Byte.toString(b);

                            Log.d("", "=" + String.valueOf(count++) + " ::" + strByte);

                            strScanData = strScanData + "|" + strByte;
                        }

                        String strSubHexScanData = "";
                        for (int i = 0; i < strHexScanData.length(); i += 2) {
                            strSubHexScanData = strSubHexScanData + "|" + strHexScanData.substring(i, i + 2);
                        }

                        Log.d("", "!!!!scanHexRecord : " + strHexScanData);
                        Log.d("", "!!!!strSubHexScanData : " + strSubHexScanData);
                        Log.d("", "!!!!Record : " + strScanData + " Len : " + strScanData.length());
                        Log.d("", "!!!!Record : " + new String(record));

                        ParcelUuid parcelUuid = BluetoothUuid.parseUuidFrom(uuidBytes);
//                        Map<ParcelUuid, byte[]> serviceData = new ArrayMap<ParcelUuid, byte[]>();
                        ScanRecord scanRecord = ScanRecord.parseFromBytes(record);
//                        scanRecord.toString();i
                        info.setDeviceScanRecord(scanRecord);
                        info.setUUID(uuid);
                        info.setParcelUuid(parcelUuid);

                        Log.d("", "!!!!" + device.getName() + "::" + uuid + "::" + "major : " + major + " " + "minor :" + minor);

                        listAdapter.add(info);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });




    }

    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
