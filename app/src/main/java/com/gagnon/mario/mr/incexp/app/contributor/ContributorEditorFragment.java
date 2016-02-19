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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContributorEditorFragment extends Fragment {

    // region Public Interface

    public interface OnButtonClickListener{
        void onBackButtonClick();
        void onSaveButtonClick(Contributor contributor);
        void onDeleteButtonClick(Contributor contributor);
    }

    // endregion Public Interface

    // region Private Field

    //private static final String LOG_TAG = ContributorEditorFragment.class.getSimpleName();

    private TextView mTextViewName;
    private Button mButtonSave;
    private Button mButtonBack;
    private Button mButtonDelete;
    private Contributor mContributor;

    private TextView mTextViewValidationErrorMessage;

    private View.OnClickListener mOnButtonClickListener;


    private ObjectValidator mObjectValidator = null;

    // endregion Private Field

    // region Constructor

    public ContributorEditorFragment() {

        mOnButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button button = (Button)v;

                switch(button.getId()){
                    case R.id.button_back:
                        ((ContributorEditorFragment.OnButtonClickListener)getActivity()).onBackButtonClick();
                        break;
                    case R.id.button_delete:
                        mContributor.setDead(true);
                        ((ContributorEditorFragment.OnButtonClickListener)getActivity()).onDeleteButtonClick(mContributor);
                        break;
                    case R.id.button_save:

                        mContributor.setName(mTextViewName.getText().toString());

                        ValidationStatus validationStatus = getObjectValidator().Validate(mContributor);

                        if(validationStatus.isValid()) {
                            ((ContributorEditorFragment.OnButtonClickListener) getActivity()).onSaveButtonClick(mContributor);
                        }else{
                            mTextViewValidationErrorMessage.setText(validationStatus.getMessage());
                            mTextViewValidationErrorMessage.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            }
        };

    }

    // endregion Constructor

    // region Public Method

    public ObjectValidator getObjectValidator() {

        if(null == mObjectValidator){
            mObjectValidator = new ContributorValidator(getActivity().getContentResolver());
        }

        return mObjectValidator;
    }

    public void setObjectValidator(ObjectValidator mObjectValidator) {
        this.mObjectValidator = mObjectValidator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mContributor = (Contributor)arguments.getSerializable("item");
        }

        if(null == mContributor) {
            mContributor = Contributor.createNew();
        }

        View rootView = inflater.inflate(R.layout.fragment_contributor_editor, container, false);
        mTextViewName = (TextView) rootView.findViewById(R.id.textview_contributor_name);
        mTextViewName.setText(mContributor.getName());

        mTextViewValidationErrorMessage = (TextView) rootView.findViewById(R.id.textViewValidationErrorMessage);

        mButtonSave = (Button) rootView.findViewById(R.id.button_save);
        mButtonSave.setText(mContributor.isNew() ? R.string.button_label_add : R.string.button_label_save);
        mButtonSave.setOnClickListener(mOnButtonClickListener);

        mButtonBack = (Button) rootView.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(mOnButtonClickListener);

        mButtonDelete = (Button) rootView.findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(mOnButtonClickListener);

        if(mContributor.isNew()){
            mButtonDelete.setVisibility(View.GONE);
        }

        return rootView;
    }

    // endregion Public Method

}