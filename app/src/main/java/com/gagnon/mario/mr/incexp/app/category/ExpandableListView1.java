package com.gagnon.mario.mr.incexp.app.category;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.gagnon.mario.mr.incexp.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 3/24/2016.
 */
public class ExpandableListView1 extends Fragment {

    private View rootView;
    private Map<String, ArrayList<String>> data =
            new LinkedHashMap<String, ArrayList<String>>();
    private List<String> groupList;
    private List<String> childList;
    private Map<String, List<String>> dataCollection;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;

        rootView = inflater.inflate(R.layout.list_view_main, null);
        // you must fill the linkedhashmap data before this 2 methods
        data.put("Group1", new ArrayList<String>(Arrays.asList("Item 1-1", "Item 1-2")));
        data.put("Group2", new ArrayList<String>(Arrays.asList("Item 2-1", "Item 2-2")));
        data.put("Group3", new ArrayList<String>(Arrays.asList("Item 3-1", "Item 3-2", "Item 3-3")));

        createGroupList();
        createCollection();

        ExpandableListView elv =
                (ExpandableListView) rootView.findViewById(R.id.list);
        elv.setAdapter(new ExpandableListAdapter
                (inflater, groupList,dataCollection,this.getActivity()));

        for(int i = 0; i < elv.getAdapter().getCount(); i++){
            elv.setItemChecked(i, (i % 2) == 0 ? true : false );
        }

        return rootView;
    }



    private void createGroupList() {
        groupList = new ArrayList<String>();
        for (String groupItem : data.keySet()) {
            groupList.add(groupItem);
        }
    }

    private void createCollection() {

        dataCollection = new LinkedHashMap<String, List<String>>();

        for (String childItem : groupList) {
            ArrayList<String> l = data.get(childItem);
            loadChild(l.toArray(new String[l.size()]));

            dataCollection.put(childItem, childList);
        }
    }

    private void loadChild(String[] childModels) {
        childList = new ArrayList<String>();
        for (String model : childModels)
            childList.add(model);
    }


}
