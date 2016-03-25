package com.gagnon.mario.mr.incexp.app.category;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 3/24/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater context;
    private Map<String, List<String>> dataCollection;
    private List<String> childItems;
    private Activity mActivity;

    public ExpandableListAdapter(LayoutInflater context,
                                 List<String> childItems,
                                 Map<String, List<String>> dataCollection,
                                 Activity activity) {
        this.context = context;
        this.dataCollection = dataCollection;
        this.childItems = childItems;
        mActivity = activity;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return dataCollection.get(childItems.get(groupPosition)).get(
                childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition,
                             final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {

        TextView textView = new TextView(mActivity);
        textView.setText(getChild(groupPosition, childPosition).toString());

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setPadding(50, 7, 7, 7);

        return textView;
    }

    public int getChildrenCount(int groupPosition) {
        return dataCollection.get(childItems.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return childItems.get(groupPosition);
    }

    public int getGroupCount() {
        return childItems.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String childName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = context;
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.item);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(childName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
