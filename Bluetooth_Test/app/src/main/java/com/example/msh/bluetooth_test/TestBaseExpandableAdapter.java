package com.example.msh.bluetooth_test;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

import java.util.ArrayList;

/**
 * Created by msh on 15. 7. 7..
 */
public class TestBaseExpandableAdapter implements ExpandableListAdapter {

    Context context;
    ArrayList<?> arrayGroupList;
    ArrayList<ArrayList<?>> arrayChildList;

    int resourceid;

    public TestBaseExpandableAdapter(Context context, ArrayList<?> arrayGroupList, ArrayList<ArrayList<?>> arrayChildList, int resourceid) {
        this.context = context;
        this.arrayGroupList = arrayGroupList;
        this.arrayChildList = arrayChildList;
        this.resourceid = resourceid;
    }

    @Override
    public int getGroupCount() {
        return arrayGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * @see DataSetObservable#notifyInvalidated()
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    /**
     * @see DataSetObservable#notifyChanged()
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

}
