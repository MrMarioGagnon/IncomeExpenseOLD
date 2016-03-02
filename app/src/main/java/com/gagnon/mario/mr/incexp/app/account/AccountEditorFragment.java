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
package com.gagnon.mario.mr.incexp.app.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountEditorFragment extends Fragment {

    // region Private Field

    private static final String LOG_TAG = AccountEditorFragment.class.getSimpleName();
    private static final String KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE = "key1";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION = "key2";

    private EditText mEditTextName;
    private Button mButtonSave;
    private Button mButtonBack;
    private Button mButtonDelete;
    private Account mAccount;
    private TextView mTextViewValidationErrorMessage;
    private Spinner mSpinnerCurrency;
    private View.OnClickListener mOnButtonClickListener;
    private TextWatcher mOnTextChangeListener;
    private ObjectValidator mObjectValidator = null;
    private ArrayList<String> mNames;
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private ArrayAdapter<CharSequence> mSpinnerCurrencyAdapter;

    // endregion Private Field

// region Constructor

    public AccountEditorFragment() {

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
                        ((AccountEditorFragment.OnButtonClickListener) getActivity()).onBackButtonClick();
                        break;
                    case R.id.button_delete:
                        mAccount.setDead(true);
                        ((AccountEditorFragment.OnButtonClickListener) getActivity()).onDeleteButtonClick(mAccount);
                        break;
                    case R.id.button_save:

                        mAccount.setName(mEditTextName.getText().toString());
                        mAccount.setCurrency((String) mSpinnerCurrency
                                .getSelectedItem());

                        try {
                            ValidationStatus validationStatus = getObjectValidator().Validate(mAccount);

                            if (validationStatus.isValid()) {
                                ((AccountEditorFragment.OnButtonClickListener) getActivity()).onSaveButtonClick(mAccount);
                            } else {
                                mTextViewValidationErrorMessage.setText(validationStatus.getMessage());
                                mTextViewValidationErrorMessage.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ex) {
                            Log.e(LOG_TAG, getString(R.string.error_log_saving_item, getString(R.string.account)), ex);
                            mTextViewValidationErrorMessage.setText(getString(R.string.error_to_user_saving_item, getString(R.string.account)));
                            mTextViewValidationErrorMessage.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            }
        };

    }

    // endregion Constructor

    // region Private Method

    // endregion Private method

    // region Public Method

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mAccount = (Account) arguments.getSerializable("item");
            mNames = (ArrayList<String>) arguments.getSerializable("names");
        } else {
            mAccount = Account.createNew();
            mNames = new ArrayList<>();
        }

        mSpinnerCurrencyAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.currency_array,
                android.R.layout.simple_spinner_item);
        mSpinnerCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public ObjectValidator getObjectValidator() {

        if (null == mObjectValidator) {
            mObjectValidator = AccountValidator.create(getActivity(), mNames);
        }

        return mObjectValidator;
    }

    public void setObjectValidator(ObjectValidator mObjectValidator) {
        this.mObjectValidator = mObjectValidator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.account_editor_fragment, container, false);

        mTextViewValidationErrorMessage = (TextView) rootView.findViewById(R.id.textViewValidationErrorMessage);

        mEditTextName = (EditText) rootView.findViewById(R.id.edittext_account_name);

        mSpinnerCurrency = (Spinner) rootView.findViewById(R.id.spinner_currency);
        mSpinnerCurrency.setAdapter(mSpinnerCurrencyAdapter);

        mButtonDelete = (Button) rootView.findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(mOnButtonClickListener);

        mButtonSave = (Button) rootView.findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(mOnButtonClickListener);

        mButtonBack = (Button) rootView.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(mOnButtonClickListener);

        if (null == savedInstanceState) {

            mEditTextName.setText(mAccount.getName());

            if (mAccount.isNew()) {
                mButtonDelete.setVisibility(View.GONE);
                mButtonSave.setText(R.string.button_label_add);
            } else {
                mButtonSave.setText(R.string.button_label_save);
            }

            mSpinnerCurrency.setSelection(((ArrayAdapter<String>) mSpinnerCurrency.getAdapter()).getPosition(mAccount.getCurrency()), false);

            mButtonSave.setEnabled(false);

        }else{
            if(savedInstanceState.containsKey(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE)){
                mButtonSave.setEnabled(savedInstanceState.getBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE));
            }
            if(savedInstanceState.containsKey(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION)){
                int position = savedInstanceState.getInt(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION);
                mSpinnerCurrency.setOnItemSelectedListener(null);
                mSpinnerCurrency.setSelection(position, false);
            }

        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mEditTextName.addTextChangedListener(mOnTextChangeListener);
        mSpinnerCurrency.setOnItemSelectedListener(mOnItemSelectedListener);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE, mButtonSave.isEnabled());
        outState.putInt(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION, mSpinnerCurrency.getSelectedItemPosition());
    }

    // endregion Public Method

    // region Public Interface

    public interface OnButtonClickListener {
        void onBackButtonClick();

        void onSaveButtonClick(Account account);

        void onDeleteButtonClick(Account account);
    }

    // endregion Public Interface

}