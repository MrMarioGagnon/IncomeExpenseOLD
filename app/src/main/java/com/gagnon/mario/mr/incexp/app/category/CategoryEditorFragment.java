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
package com.gagnon.mario.mr.incexp.app.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeEvent;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeHandler;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeListener;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryEditorFragment extends Fragment implements ItemStateChangeHandler {

    private static final String LOG_TAG = CategoryEditorFragment.class.getSimpleName();
    private static final String KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE = "key1";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION = "key2";

    private EditText mEditTextName;
    private Button mButtonSave;
    private Button mButtonBack;
    private Button mButtonDelete;
    private Category mCategory;
    private TextView mTextViewValidationErrorMessage;
    private Spinner mSpinnerGroup;
    private View.OnClickListener mOnButtonClickListener;
    private TextWatcher mOnTextChangeListener;
    private ObjectValidator mObjectValidator = null;
    private ArrayList<String> mNames;
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private ArrayAdapter<CharSequence> mSpinnerGroupAdapter;

    private List<ItemStateChangeListener> mListeners;

    public CategoryEditorFragment() {

        mListeners = new ArrayList<>();

        mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

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

                Button button = (Button) v;

                switch (button.getId()) {
                    case R.id.button_back:
                        notifyListener(new ItemStateChangeEvent(null, true));
                        break;
                    case R.id.button_delete:
                        mCategory.setDead(true);
                        notifyListener(new ItemStateChangeEvent(mCategory));
                        break;
                    case R.id.button_save:

                        mCategory.setName(mEditTextName.getText().toString());
                        mCategory.setGroup((String) mSpinnerGroup
                                .getSelectedItem());

                        ValidationStatus validationStatus = getObjectValidator().Validate(mCategory);

                        if (validationStatus.isValid()) {
                            notifyListener(new ItemStateChangeEvent(mCategory));
                        } else {
                            mTextViewValidationErrorMessage.setText(validationStatus.getMessage());
                            mTextViewValidationErrorMessage.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            }
        };

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mCategory = (Category) arguments.getSerializable("item");
            mNames = (ArrayList<String>) arguments.getSerializable("names");
        } else {
            mCategory = Category.createNew();
            mNames = new ArrayList<>();
        }

        mSpinnerGroupAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.category_group_names,
                android.R.layout.simple_spinner_item);
        mSpinnerGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public ObjectValidator getObjectValidator() {

        if (null == mObjectValidator) {
            mObjectValidator = CategoryValidator.create(getActivity(), mNames);
        }

        return mObjectValidator;
    }

    public void setObjectValidator(ObjectValidator mObjectValidator) {
        this.mObjectValidator = mObjectValidator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.category_editor_fragment, container, false);

        mTextViewValidationErrorMessage = (TextView) rootView.findViewById(R.id.textViewValidationErrorMessage);

        mEditTextName = (EditText) rootView.findViewById(R.id.edittext_category_name);

        mSpinnerGroup = (Spinner) rootView.findViewById(R.id.spinner_group);
        mSpinnerGroup.setAdapter(mSpinnerGroupAdapter);

        mButtonDelete = (Button) rootView.findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(mOnButtonClickListener);

        mButtonSave = (Button) rootView.findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(mOnButtonClickListener);

        mButtonBack = (Button) rootView.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(mOnButtonClickListener);

        if (null == savedInstanceState) {

            mEditTextName.setText(mCategory.getName());

            if (mCategory.isNew()) {
                mButtonDelete.setVisibility(View.GONE);
                mButtonSave.setText(R.string.button_label_add);
            } else {
                mButtonSave.setText(R.string.button_label_save);
            }

            mSpinnerGroup.setSelection(((ArrayAdapter<String>) mSpinnerGroup.getAdapter()).getPosition(mCategory.getGroup()), false);

            mButtonSave.setEnabled(false);

        } else {
            if (savedInstanceState.containsKey(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE)) {
                mButtonSave.setEnabled(savedInstanceState.getBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE));
            }
            if (savedInstanceState.containsKey(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION)) {
                int position = savedInstanceState.getInt(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION);
                mSpinnerGroup.setOnItemSelectedListener(null);
                mSpinnerGroup.setSelection(position, false);
            }

        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mEditTextName.addTextChangedListener(mOnTextChangeListener);
        mSpinnerGroup.setOnItemSelectedListener(mOnItemSelectedListener);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE, mButtonSave.isEnabled());
        outState.putInt(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION, mSpinnerGroup.getSelectedItemPosition());
    }

    @Override
    public void addListener(@NonNull ItemStateChangeListener listener) {
        if (!mListeners.contains(listener))
            mListeners.add(listener);
    }

    @Override
    public void notifyListener(@NonNull ItemStateChangeEvent event) {
        for (ItemStateChangeListener listener : mListeners) {
            listener.onItemStateChange(event);
        }
    }

}