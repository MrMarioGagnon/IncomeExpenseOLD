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
package com.gagnon.mario.mr.incexp.app.payment_method;

import android.app.Dialog;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeEvent;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeHandler;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeListener;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;
import com.gagnon.mario.mr.incexp.app.core.dialog.DialogUtils;
import com.gagnon.mario.mr.incexp.app.core.dialog.MultipleChoiceEventHandler;
import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentMethodEditorFragment extends Fragment implements ItemStateChangeHandler {

    private static final String LOG_TAG = PaymentMethodEditorFragment.class.getSimpleName();
    private static final String KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE = "key1";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION = "key2";

    private EditText mEditTextName;
    private EditText mEditTextExchangeRate;
    private Button mButtonSave;
    private Button mButtonBack;
    private Button mButtonDelete;
    private ImageButton mImageButtonContributors;
    private PaymentMethod mPaymentMethod;
    private TextView mTextViewValidationErrorMessage;
    private Spinner mSpinnerCurrency;
    private View.OnClickListener mOnButtonClickListener;
    private View.OnClickListener mOnImageButtonClickListener;
    private TextWatcher mOnTextChangeListener;
    private ObjectValidator mObjectValidator = null;
    private ArrayList<String> mNames;
    private SortedSet<Contributor> mContributors;
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private ArrayAdapter<CharSequence> mSpinnerCurrencyAdapter;
    private MultipleChoiceEventHandler mContributorMultipleChoiceEventHandler;
    private boolean[] mSelectedContributor;
    private TextView mTextViewContributors;

    private List<ItemStateChangeListener> mListeners;

    public PaymentMethodEditorFragment() {

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
                        mPaymentMethod.setDead(true);
                        notifyListener(new ItemStateChangeEvent(mPaymentMethod));
                        break;
                    case R.id.button_save:

                        mPaymentMethod.setName(mEditTextName.getText().toString());
                        mPaymentMethod.setCurrency((String) mSpinnerCurrency
                                .getSelectedItem());

                        String exchangeRateStr = mEditTextExchangeRate.getText().toString();
                        Double exchangeRate = Doubles.tryParse(exchangeRateStr) ;

                        mPaymentMethod.setExchangeRate( exchangeRate == null ? 0 : exchangeRate);

                        // if not null, Contributors Dialog Box was call
                        if (mSelectedContributor != null) {
                            Contributor[] a = new Contributor[mContributors.size()];
                            mContributors.toArray(a);

                            mPaymentMethod.clearContributor();
                            for (int i = 0; i < mSelectedContributor.length; i++) {
                                if (mSelectedContributor[i]) {
                                    mPaymentMethod.addContributor(a[i]);
                                }
                            }
                        }

                        ValidationStatus validationStatus = getObjectValidator().Validate(mPaymentMethod);

                        if (validationStatus.isValid()) {
                            notifyListener(new ItemStateChangeEvent(mPaymentMethod));
                        } else {
                            mTextViewValidationErrorMessage.setText(validationStatus.getMessage());
                            mTextViewValidationErrorMessage.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            }
        };

        mOnImageButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContributorSetterDialog();
            }
        };

        mContributorMultipleChoiceEventHandler = new MultipleChoiceEventHandler() { // Creating an anonymous Class Object
            @Override
            public void execute(boolean[] idSelected) {
                Contributor[] a = new Contributor[mContributors.size()];
                mContributors.toArray(a);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < idSelected.length; i++) {
                    if (idSelected[i]) {
                        sb.append(String.format("%1$s%2$s", (sb.length() == 0 ? "" : ","), a[i].getName()));
                    }
                }
                mTextViewContributors.setText(sb.toString());
                mSelectedContributor = idSelected;
            }
        };

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mPaymentMethod = (PaymentMethod) arguments.getSerializable("item");
            mNames = (ArrayList<String>) arguments.getSerializable("names");
            mContributors = (SortedSet<Contributor>) arguments.getSerializable("contributors");
        } else {
            mPaymentMethod = PaymentMethod.createNew();
            mNames = new ArrayList<>();
            mContributors = new TreeSet<>();
        }

        mSpinnerCurrencyAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.pref_currency_values,
                android.R.layout.simple_spinner_item);
        mSpinnerCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public ObjectValidator getObjectValidator() {

        if (null == mObjectValidator) {
            mObjectValidator = PaymentMethodValidator.create(getActivity(), mNames);
        }

        return mObjectValidator;
    }

    public void setObjectValidator(ObjectValidator mObjectValidator) {
        this.mObjectValidator = mObjectValidator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.payment_method_editor_fragment, container, false);

        mTextViewValidationErrorMessage = (TextView) rootView.findViewById(R.id.textViewValidationErrorMessage);

        mEditTextName = (EditText) rootView.findViewById(R.id.edittext_payment_method_name);

        mSpinnerCurrency = (Spinner) rootView.findViewById(R.id.spinner_currency);
        mSpinnerCurrency.setAdapter(mSpinnerCurrencyAdapter);

        mEditTextExchangeRate = (EditText) rootView.findViewById(R.id.edittext_exchange_rate);

        mButtonDelete = (Button) rootView.findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(mOnButtonClickListener);

        mButtonSave = (Button) rootView.findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(mOnButtonClickListener);

        mButtonBack = (Button) rootView.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(mOnButtonClickListener);

        mImageButtonContributors = (ImageButton) rootView.findViewById(R.id.imagebutton_contributors);
        mImageButtonContributors.setOnClickListener(mOnImageButtonClickListener);

        mTextViewContributors = (TextView) rootView.findViewById(R.id.textview_contributors);

        if (null == savedInstanceState) {

            mEditTextName.setText(mPaymentMethod.getName());
            mEditTextExchangeRate.setText(mPaymentMethod.getExchangeRate().toString());

            if (mPaymentMethod.isNew()) {
                mButtonDelete.setVisibility(View.GONE);
                mButtonSave.setText(R.string.button_label_add);
            } else {
                mButtonSave.setText(R.string.button_label_save);
            }

            mSpinnerCurrency.setSelection(((ArrayAdapter<String>) mSpinnerCurrency.getAdapter()).getPosition(mPaymentMethod.getCurrency()), false);

            mTextViewContributors.setText(mPaymentMethod.getContributorsForDisplay());

            mButtonSave.setEnabled(false);

        } else {
            if (savedInstanceState.containsKey(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE)) {
                mButtonSave.setEnabled(savedInstanceState.getBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE));
            }
            if (savedInstanceState.containsKey(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION)) {
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
        mTextViewContributors.addTextChangedListener(mOnTextChangeListener);
        mSpinnerCurrency.setOnItemSelectedListener(mOnItemSelectedListener);
        mEditTextExchangeRate.addTextChangedListener(mOnTextChangeListener);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE, mButtonSave.isEnabled());
        outState.putInt(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION, mSpinnerCurrency.getSelectedItemPosition());
    }

    private boolean[] buildContributorsCheckedArray(SortedSet<Contributor> contributors,
                                                    String contributorsName) {

        boolean[] checked = new boolean[contributors.size()];

        int i = 0;
        for (Contributor contributor : contributors) {

            checked[i] = contributorsName.contains(contributor.getName());
            i++;
        }

        return checked;

    }

    private void showContributorSetterDialog() {

        try {

            CharSequence[] contributorArray = new CharSequence[mContributors.size()];
            int i = 0;
            for (Contributor contributor : mContributors) {
                contributorArray[i++] = contributor.getName();
            }

            Dialog dialog = DialogUtils.childSetterDialog(
                    this.getContext(),
                    contributorArray,
                    mContributorMultipleChoiceEventHandler,
                    buildContributorsCheckedArray(mContributors, mPaymentMethod.getContributorsForDisplay()),
                    getString(R.string.dialog_title_contributor_setter));

            dialog.setOwnerActivity(this.getActivity());
            dialog.show();
        } catch (Exception exception) {
            DialogUtils.messageBox(this.getContext(),
                    getString(R.string.error_unable_to_fetch_all_contributor),
                    getString(R.string.dialog_title_contributor_setter)).show();

        }

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