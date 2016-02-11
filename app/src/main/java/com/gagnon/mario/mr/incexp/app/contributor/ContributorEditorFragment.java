/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gagnon.mario.mr.incexp.app.contributor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContributorEditorFragment extends Fragment {

    private static final String LOG_TAG = ContributorEditorFragment.class.getSimpleName();

    private static final String[] DETAIL_COLUMNS = {
            IncomeExpenseContract.ContributorEntry._ID,
            IncomeExpenseContract.ContributorEntry.COLUMN_NAME
    };

    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;

    private TextView mTextViewName;

    public ContributorEditorFragment() {
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
//            mUri = arguments.getParcelable(DetailFragment.DETAIL_URI);
        }

        View rootView = inflater.inflate(R.layout.fragment_contributor_editor, container, false);
        mTextViewName = (TextView) rootView.findViewById(R.id.textview_contributor_name);

        //final android.app.ActionBar actionBar = getActivity().getActionBar();
        //actionBar.setDisplayUseLogoEnabled(true);


        return rootView;
    }

}