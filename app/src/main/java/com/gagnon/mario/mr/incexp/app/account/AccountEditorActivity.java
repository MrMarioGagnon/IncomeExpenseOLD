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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;

import java.util.ArrayList;

public class AccountEditorActivity extends AppCompatActivity implements AccountEditorFragment.OnButtonClickListener {

    // region Private Fields

    private static final String LOG_TAG = AccountEditorActivity.class.getSimpleName();

    // endregion

    // region Protected Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_editor_activity);

        if (savedInstanceState == null) {

            Account account = null;

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                account = (Account)bundle.getSerializable("item");
            }

            if(null == account) {
                account = Account.createNew();
            }

            Bundle arguments = new Bundle();
            arguments.putSerializable("item", account);
            arguments.putSerializable("names", getAvailableAccountsName(account));

            AccountEditorFragment fragment = new AccountEditorFragment();
            fragment.setArguments(arguments);
            fragment.setRetainInstance(true);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.account_editor_container, fragment)
                    .commit();
        }
    }

    // endregion Protected Method

    // region Private Method
    private ArrayList<String> getAvailableAccountsName(Account account)
    {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.AccountEntry.CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = null;
        try {

            String selection = String.format("%1$s !=?", IncomeExpenseContract.AccountEntry.COLUMN_ID);
            // Si account est new le id va etre null, donc remplacer par -1
            String[] selectionArgument = new String[] { account.isNew() ? "-1" : account.getId().toString()};

            cursor = contentResolver.query(uri, new String[] {IncomeExpenseContract.AccountEntry.COLUMN_NAME}, selection, selectionArgument, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_NAME));
                names.add(name.toUpperCase());
            }
        }finally {
            if(null != cursor) {
                cursor.close();
            }
        }

        return names;
    }
    // endregion Private Method

    // region Public Method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            //  startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    // endregion Public Method

    // region ContributorEditorFragment.OnButtonClickListener

    @Override
    public void onBackButtonClick() {
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    public void onDeleteButtonClick(Account account) {

        if (null == account || null == account.getId()) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        if (account.isDirty()) {

            Uri accountUri = IncomeExpenseContract.AccountEntry.CONTENT_URI;

            ContentResolver contentResolver = getContentResolver();

            if (account.isDead()) {
                // Delete account
                long id = account.getId();
                String name = account.getName();
                try {

                    Log.i(LOG_TAG, getString(R.string.log_info_deleting_account, id));
                    int rowsDeleted = contentResolver.delete(accountUri, IncomeExpenseContract.AccountEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                    Log.i(LOG_TAG, getString(R.string.log_info_number_deleted_account, rowsDeleted));

                } catch (Exception ex) {
                    String message_log = this.getString(R.string.error_log_deleting_item, getString(R.string.account), id);
                    Log.e(LOG_TAG, message_log, ex);

                    String message_user = this.getString(R.string.error_to_user_deleting_item, getString(R.string.account), name);
                    Toast.makeText(this, message_user, Toast.LENGTH_SHORT).show();

                }
            }

        }

        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onSaveButtonClick(Account account) {

        if (null == account)
            return;

        if (account.isDirty()) {

            Uri accountUri = IncomeExpenseContract.AccountEntry.CONTENT_URI;
            ContentResolver contentResolver = getContentResolver();

            if (account.isNew()) {
                // Add account

                try {
                    ContentValues accountValues = new ContentValues();
                    accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_NAME, account.getName());
                    accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY, account.getCurrency());
                    Uri newUri = contentResolver.insert(accountUri, accountValues);
                    long newID = IncomeExpenseContract.AccountEntry.getIdFromUri(newUri);
                    account.setId(newID);
                } catch (Exception ex) {

                    String message_log = this.getString(R.string.error_log_adding_item, getString(R.string.account));
                    Log.e(LOG_TAG, message_log, ex);

                    String message_user = this.getString(R.string.error_to_user_adding_item, getString(R.string.account));
                    Toast.makeText(this, message_user, Toast.LENGTH_SHORT).show();

                }
            } else {

                // Update account

                long id = account.getId();

                ContentValues accountValues = new ContentValues();
                accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_NAME, account.getName());
                accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY, account.getCurrency());
                Log.i(LOG_TAG, getString(R.string.log_info_updating_account, id));
                int rowsUpdated = contentResolver.update(accountUri, accountValues, IncomeExpenseContract.AccountEntry.COLUMN_ID + "=?", new String[]{account.getId().toString()});
                Log.i(LOG_TAG, getString(R.string.log_info_number_updated_account, rowsUpdated));
            }

        }

        setResult(RESULT_OK);
        finish();
    }

    // endregion ContributorEditorFragment.OnButtonClickListener

}