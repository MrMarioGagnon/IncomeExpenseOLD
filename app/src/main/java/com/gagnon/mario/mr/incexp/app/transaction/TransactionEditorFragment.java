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
package com.gagnon.mario.mr.incexp.app.transaction;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.account.AccountValidator;
import com.gagnon.mario.mr.incexp.app.category.Category;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeEvent;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeHandler;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeListener;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;
import com.gagnon.mario.mr.incexp.app.core.dialog.DialogUtils;
import com.gagnon.mario.mr.incexp.app.core.dialog.MultipleChoiceEventHandler;
import com.gagnon.mario.mr.incexp.app.payment_method.PaymentMethod;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A placeholder fragment containing a simple view.
 */
public class TransactionEditorFragment extends Fragment implements ItemStateChangeHandler {

    private static final String LOG_TAG = TransactionEditorFragment.class.getSimpleName();
    private static final String KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE = "key1";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION = "key2";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_ACCOUNT_POSITION = "key3";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_CATEGORY_POSITION = "key4";
    private static final String KEY_SAVE_INSTANCE_STATE_SPINNER_PAYMENT_METHOD_POSITION = "key5";

    private Spinner mSpinnerAccount;
    private Spinner mSpinnerCategory;
    private RadioGroup mRadioGroupType;
    private RadioButton mRadioButtonExpense;
    private RadioButton mRadioButtonIncome;
    private TextView mTextViewDate;
    private ImageButton mImageButtonDate;
    private EditText mEditTextAmount;
    private EditText mEditTextExchangeRate;
    private Spinner mSpinnerCurrency;
    private Spinner mSpinnerPaymentMethod;
    private EditText mEditTextNote;
    private Button mButtonSave;
    private Button mButtonBack;
    private Button mButtonDelete;
    private TextView mTextViewValidationErrorMessage;

    private Transaction mTransaction;

    private AdapterView.OnItemSelectedListener mOnAccountSelectedListener;
    private AdapterView.OnItemSelectedListener mOnCategorySelectedListener;
    private AdapterView.OnItemSelectedListener mOnPaymentMethodSelectedListener;
    private AdapterView.OnItemSelectedListener mOnCurrencySelectedListener;
    private View.OnClickListener mOnImageButtonDateClickListener;
    private View.OnClickListener mOnButtonClickListener;
    private TextWatcher mOnTextDateChangeListener;
    private TextWatcher mOnTextAmountChangeListener;
    private TextWatcher mOnTextExchangeRateChangeListener;
    private TextWatcher mOnTextNoteChangeListener;

    private ObjectValidator mObjectValidator = null;
    private List<Account> mAccounts;
    private List<Category> mCategories;
    private List<String> mCurrencies;
    private List<PaymentMethod> mPaymentMethods;

    private ArrayAdapter<CharSequence> mSpinnerCurrencyAdapter;
    private ArrayAdapter<Account> mSpinnerAccountAdapter;
    private ArrayAdapter<Category> mSpinnerCategoryAdapter;
    private ArrayAdapter<PaymentMethod> mSpinnerPaymentMethodAdapter;

    private List<ItemStateChangeListener> mListeners;

    public TransactionEditorFragment() {

        mListeners = new ArrayList<>();

        mOnAccountSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        mOnCategorySelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        mOnCategorySelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        mOnPaymentMethodSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        mOnCurrencySelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mButtonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        mOnTextDateChangeListener = new TextWatcher() {
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

        mOnTextAmountChangeListener = new TextWatcher() {
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

        mOnTextExchangeRateChangeListener = new TextWatcher() {
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

        mOnTextNoteChangeListener = new TextWatcher() {
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

        mOnImageButtonDateClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Show dialog
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
                        mTransaction.setDead(true);
                        notifyListener(new ItemStateChangeEvent(mTransaction));
                        break;
                    case R.id.button_save:

                        mTransaction.setAccount((Account) mSpinnerAccount
                                .getSelectedItem());

                        mTransaction.setCategory( (Category) mSpinnerCategory.getSelectedItem() );

                        // TODO Radio Group

                        ValidationStatus validationStatus = getObjectValidator().Validate(mTransaction);

                        if (validationStatus.isValid()) {
                            notifyListener(new ItemStateChangeEvent(mTransaction));
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
            mTransaction = (Transaction) arguments.getSerializable("item");
            mAccounts = (List<Account>) arguments.getSerializable("accounts");
            mCategories = (List<Category>) arguments.getSerializable("categories");
            mCurrencies = (List<String>) arguments.getSerializable("currencies");
            mPaymentMethods = (List<PaymentMethod>) arguments.getSerializable("paymentmethods");
        } else {
            mTransaction = Transaction.createNew();
            mAccounts = new ArrayList<>();
            mCategories = new ArrayList<>();
            mCurrencies = new ArrayList<>();
            mPaymentMethods = new ArrayList<>();
        }


        mSpinnerAccountAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, mAccounts);
        mSpinnerAccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategoryAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, mCategories);
        mSpinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCurrencyAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.pref_currency_values,
                android.R.layout.simple_spinner_item);
        mSpinnerCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPaymentMethodAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, mPaymentMethods);
        mSpinnerPaymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public ObjectValidator getObjectValidator() {

        if (null == mObjectValidator) {
            mObjectValidator = TransactionValidator.create(getActivity());
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

        mSpinnerAccount = (Spinner) rootView.findViewById(R.id.spinner_account);
        mSpinnerAccount.setAdapter(mSpinnerAccountAdapter);
        mSpinnerCategory = (Spinner) rootView.findViewById(R.id.spinner_category);
        mSpinnerCategory.setAdapter(mSpinnerCategoryAdapter);
        mSpinnerCurrency = (Spinner) rootView.findViewById(R.id.spinner_currency);
        mSpinnerCurrency.setAdapter(mSpinnerCurrencyAdapter);
        mSpinnerPaymentMethod = (Spinner) rootView.findViewById(R.id.spinner_payment_method);
        mSpinnerPaymentMethod.setAdapter(mSpinnerPaymentMethodAdapter);

        mRadioGroupType = (RadioGroup) rootView.findViewById(R.id.radio_group_type);
        mRadioButtonExpense = (RadioButton) rootView.findViewById(R.id.radio_button_expense);
        mRadioButtonIncome = (RadioButton) rootView.findViewById(R.id.radio_button_income);

        mTextViewDate = (TextView) rootView.findViewById(R.id.text_view_date);
        mImageButtonDate = (ImageButton) rootView.findViewById(R.id.image_button_date);

        mEditTextAmount = (EditText) rootView.findViewById(R.id.edit_text_amount);
        mEditTextExchangeRate = (EditText) rootView.findViewById(R.id.edit_text_exchange_rate);
        mEditTextNote = (EditText) rootView.findViewById(R.id.edit_text_note);

        mButtonDelete = (Button) rootView.findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(mOnButtonClickListener);

        mButtonSave = (Button) rootView.findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(mOnButtonClickListener);

        mButtonBack = (Button) rootView.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(mOnButtonClickListener);

        if (null == savedInstanceState) {

            if (mTransaction.isNew()) {
                mButtonDelete.setVisibility(View.GONE);
                mButtonSave.setText(R.string.button_label_add);
            } else {
                mButtonSave.setText(R.string.button_label_save);
            }

            mSpinnerAccount.setSelection(((ArrayAdapter<Account>) mSpinnerAccount.getAdapter()).getPosition(mTransaction.getAccount()), false);
            mSpinnerCategory.setSelection(((ArrayAdapter<Category>) mSpinnerCategory.getAdapter()).getPosition(mTransaction.getCategory()), false);
            mSpinnerCurrency.setSelection(((ArrayAdapter<String>) mSpinnerCurrency.getAdapter()).getPosition(mTransaction.getCurrency()), false);
            mSpinnerPaymentMethod.setSelection(((ArrayAdapter<PaymentMethod>) mSpinnerPaymentMethod.getAdapter()).getPosition(mTransaction.getPaymentMethod()), false);

            mTextViewDate.setText(mTransaction.getDate());
            mEditTextAmount.setText(mTransaction.getAmount().toString());
            mEditTextExchangeRate.setText(mTransaction.getExchangeRate().toString());
            mEditTextNote.setText(mTransaction.getNote());

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

        mSpinnerAccount.setOnItemSelectedListener(mOnAccountSelectedListener);
        mSpinnerCategory.setOnItemSelectedListener(mOnCategorySelectedListener);
        mSpinnerCurrency.setOnItemSelectedListener(mOnCurrencySelectedListener);
        mSpinnerPaymentMethod.setOnItemSelectedListener(mOnPaymentMethodSelectedListener);

        mEditTextAmount.addTextChangedListener(mOnTextAmountChangeListener);
        mEditTextExchangeRate.addTextChangedListener(mOnTextExchangeRateChangeListener);
        mEditTextNote.addTextChangedListener(mOnTextNoteChangeListener);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SAVE_INSTANCE_STATE_BUTTON_SAVE_STATE, mButtonSave.isEnabled());
        outState.putInt(KEY_SAVE_INSTANCE_STATE_SPINNER_CURRENCY_POSITION, mSpinnerCurrency.getSelectedItemPosition());
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