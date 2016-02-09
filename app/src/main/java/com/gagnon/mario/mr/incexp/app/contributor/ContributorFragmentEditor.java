package com.gagnon.mario.mr.incexp.app.contributor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gagnon.mario.mr.incexp.app.R;

/**
 * Created by mario on 2/8/2016.
 */
public class ContributorFragmentEditor extends Fragment{

    // region Private Field
    private static final String LOG_TAG = ContributorFragmentEditor.class.getSimpleName();
    // endregion Private Field


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contributor_editor, container,false  );

        return view;
    }
}
