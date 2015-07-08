package com.example.msh.bluetooth_test;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by msh on 15. 7. 7..
 */
public class CustomExpandableListAdapter extends TestBaseExpandableAdapter {

    public CustomExpandableListAdapter(Context context, ArrayList<?> arrayGroupList, ArrayList<ArrayList<?>> arrayChildList, int resourceid) {
        super(context, arrayGroupList, arrayChildList, resourceid);
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(resourceid, null);
            v.setTag(R.id.lst_group_uuid,v.findViewById(R.id.lst_group_uuid));
            v.setTag(R.id.lst_group_nm,v.findViewById(R.id.lst_group_nm));
        }

        TextView text1 = (TextView) v.getTag(R.id.lst_group_uuid);
        text1.setText((String)arrayGroupList.get(groupPosition));

        TextView text2 = (TextView) v.getTag(R.id.lst_group_nm);
        text2.setText("Service");

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(resourceid, null);
            v.setTag(R.id.lst_group_uuid,v.findViewById(R.id.lst_group_uuid));
            v.setTag(R.id.lst_group_nm,v.findViewById(R.id.lst_group_nm));
        }

        BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) arrayChildList.get(groupPosition).get(childPosition);

        TextView text1 = (TextView) v.getTag(R.id.lst_group_uuid);
        text1.setText(characteristic.getUuid().toString());

        TextView text2 = (TextView) v.getTag(R.id.lst_group_nm);
        text2.setText("Characteristic");


        return v;
    }
}
