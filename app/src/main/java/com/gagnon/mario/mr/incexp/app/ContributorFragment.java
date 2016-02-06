package com.gagnon.mario.mr.incexp.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mario on 2/1/2016.
 */
public class ContributorFragment extends Fragment {

    ArrayAdapter<String> mContributorAdapter;

    public ContributorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Mario",
                "Nathalie"
        };

        List<String> contributors = new ArrayList<>(Arrays.asList(data));

        mContributorAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_contributor, // The name of the layout ID.
                        R.id.list_item_contributor_textview, // The ID of the textview to populate.
                        contributors);


        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_contributor, container, false);

        ListView listView = (ListView) x.findViewById(R.id.listview_contributor);
        listView.setAdapter(mContributorAdapter);

        setupFAB(x);

        return x;
    }

    private void setupFAB(View v){

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}