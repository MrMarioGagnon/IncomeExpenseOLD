package com.gagnon.mario.mr.incexp.app.transaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;

/**
 * Created by mario on 2/1/2016.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.main_fragment, container, false);

        Bundle bundle = getArguments();
        String title="";
        if(null != bundle){
            title = bundle.getString("tag");
        }

        TextView textView = (TextView)x.findViewById(R.id.section_label);
        if(null != textView){
            textView.setText(title);
        }

        return x;
    }

}