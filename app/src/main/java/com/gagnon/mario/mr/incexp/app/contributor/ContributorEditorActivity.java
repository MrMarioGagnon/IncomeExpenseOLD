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
import java.util.List;

public class ContributorEditorActivity extends AppCompatActivity implements ContributorEditorFragment.OnButtonClickListener {

    // region Private Fields

    private static final String LOG_TAG = ContributorEditorActivity.class.getSimpleName();

    // endregion

    // region Protected Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_editor);

        if (savedInstanceState == null) {

            Contributor contributor = null;

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                contributor = (Contributor)bundle.getSerializable("item");
            }

            if(null == contributor) {
                contributor = Contributor.createNew();
            }

            Bundle arguments = new Bundle();
            arguments.putSerializable("item", contributor);
            arguments.putSerializable("names", getAvailableContributorsName(contributor));

            ContributorEditorFragment fragment = new ContributorEditorFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contributor_editor_container, fragment)
                    .commit();
        }
    }

    // endregion Protected Method

    // region Private Method
    private ArrayList<String> getAvailableContributorsName(Contributor contributor)
    {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = null;
        try {

            String selection = String.format("%1$s !=?", IncomeExpenseContract.ContributorEntry.COLUMN_ID);
            // Si contributor est new le id va etre null, donc remplacer par -1
            String[] selectionArgument = new String[] { contributor.isNew() ? "-1" : contributor.getId().toString()};

            cursor = contentResolver.query(uri, new String[] {IncomeExpenseContract.ContributorEntry.COLUMN_NAME}, selection, selectionArgument, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.ContributorEntry.COLUMN_NAME));
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
    public void onDeleteButtonClick(Contributor contributor) {

        if (null == contributor || null == contributor.getId()) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        if (contributor.isDirty()) {

            Uri contributorUri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;

            ContentResolver contentResolver = getContentResolver();

            if (contributor.isDead()) {
                // Delete contributor
                long id = contributor.getId();
                String name = contributor.getName();
                try {

                    Log.i(LOG_TAG, getString(R.string.log_info_deleting_contributor, id));
                    int rowsDeleted = contentResolver.delete(contributorUri, IncomeExpenseContract.ContributorEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                    Log.i(LOG_TAG, getString(R.string.log_info_number_deleted_contributor, rowsDeleted));

                } catch (Exception ex) {
                    String message_log = this.getString(R.string.error_log_deleting_item, getString(R.string.contributor), id);
                    Log.e(LOG_TAG, message_log, ex);

                    String message_user = this.getString(R.string.error_to_user_deleting_item, getString(R.string.contributor), name);
                    Toast.makeText(this, message_user, Toast.LENGTH_SHORT).show();

                }
            }

        }

        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onSaveButtonClick(Contributor contributor) {

        if (null == contributor)
            return;

        if (contributor.isDirty()) {

            Uri contributorUri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;
            ContentResolver contentResolver = getContentResolver();

            if (contributor.isNew()) {
                // Add contributor

                try {
                    ContentValues contributorValues = new ContentValues();
                    contributorValues.put(IncomeExpenseContract.ContributorEntry.COLUMN_NAME, contributor.getName());
                    Uri newUri = contentResolver.insert(contributorUri, contributorValues);
                    long newID = IncomeExpenseContract.ContributorEntry.getIdFromUri(newUri);
                    contributor.setId(newID);
                } catch (Exception ex) {

                    String message_log = this.getString(R.string.error_log_adding_item, getString(R.string.contributor));
                    Log.e(LOG_TAG, message_log, ex);

                    String message_user = this.getString(R.string.error_to_user_adding_item, getString(R.string.contributor));
                    Toast.makeText(this, message_user, Toast.LENGTH_SHORT).show();

                }
            } else {

                // Update contributor

                long id = contributor.getId();

                ContentValues contributorValues = new ContentValues();
                contributorValues.put(IncomeExpenseContract.ContributorEntry.COLUMN_NAME, contributor.getName());
                Log.i(LOG_TAG, getString(R.string.log_info_updating_contributor, id));
                int rowsUpdated = contentResolver.update(contributorUri, contributorValues, IncomeExpenseContract.ContributorEntry.COLUMN_ID + "=?", new String[]{contributor.getId().toString()});
                Log.i(LOG_TAG, getString(R.string.log_info_number_updated_contributor, rowsUpdated));
            }

        }

        setResult(RESULT_OK);
        finish();
    }

    // endregion ContributorEditorFragment.OnButtonClickListener

}