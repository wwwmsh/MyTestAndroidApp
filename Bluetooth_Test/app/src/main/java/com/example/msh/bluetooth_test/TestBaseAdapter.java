package com.example.msh.bluetooth_test;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by msh on 15. 6. 24..
 */
public class TestBaseAdapter<T> extends BaseAdapter {

    Activity activity;
    ArrayList<T> arrayList;
    int resourceid;

    TestBaseAdapter (Activity act, ArrayList<T> list, int resid) {
        activity = act;
        arrayList = list;
        resourceid = resid;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
