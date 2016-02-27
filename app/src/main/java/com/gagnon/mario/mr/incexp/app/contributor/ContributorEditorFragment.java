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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

import java.util.ArrayList;

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

    private static final String LOG_TAG = ContributorEditorFragment.class.getSimpleName();

    private EditText mEditTextName;
    private Button mButtonSave;
    private Button mButtonBack;
    private Button mButtonDelete;
    private Contributor mContributor;

    private TextView mTextViewValidationErrorMessage;

    private View.OnClickListener mOnButtonClickListener;
    private TextWatcher mOnTextChangeListener;

    private ObjectValidator mObjectValidator = null;
    private ArrayList<String> mNames;

    // endregion Private Field

    // region Constructor

    public ContributorEditorFragment() {

        mOnTextChangeListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

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

                        mContributor.setName(mEditTextName.getText().toString());

                        try {
                            ValidationStatus validationStatus = getObjectValidator().Validate(mContributor);

                            if (validationStatus.isValid()) {
                                ((ContributorEditorFragment.OnButtonClickListener) getActivity()).onSaveButtonClick(mContributor);
                            } else {
                                mTextViewValidationErrorMessage.setText(validationStatus.getMessage());
                                mTextViewValidationErrorMessage.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception ex){
                            Log.e(LOG_TAG, getString(R.string.error_log_saving_item, getString(R.string.contributor)), ex);
                            mTextViewValidationErrorMessage.setText( getString(R.string.error_to_user_saving_item, getString(R.string.contributor)));
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
            mObjectValidator = ContributorValidator.create(getActivity(), mNames);
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
            mNames = (ArrayList<String>)arguments.getSerializable("names");
        }else{
            mNames = new ArrayList<>();
        }

        if(null == mContributor) {
            mContributor = Contributor.createNew();
        }

        View rootView = inflater.inflate(R.layout.contributor_editor_fragment, container, false);
        mEditTextName = (EditText) rootView.findViewById(R.id.edittext_contributor_name);
        mEditTextName.setText(mContributor.getName());
        mEditTextName.addTextChangedListener(mOnTextChangeListener);

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
        }else{
            mButtonSave.setEnabled(false);
        }

        return rootView;
    }

    // endregion Public Method

}