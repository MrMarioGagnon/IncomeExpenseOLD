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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.core.ItemRepositorySynchronizerMessageBuilder;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeEvent;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeListener;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseRequestWrapper;

public class ContributorEditorActivity extends AppCompatActivity implements ItemStateChangeListener {

    private static final String LOG_TAG = ContributorEditorActivity.class.getSimpleName();
    private ContributorRepositorySynchronizer mRepositorySynchronizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contributor_editor_activity);

        mRepositorySynchronizer = new ContributorRepositorySynchronizer(getContentResolver(), IncomeExpenseContract.ContributorEntry.CONTENT_URI, ItemRepositorySynchronizerMessageBuilder.build(this, ContributorRepositorySynchronizer.class.getSimpleName()));

        if (savedInstanceState == null) {

            Contributor contributor = null;

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                contributor = (Contributor) bundle.getSerializable("item");
            }

            if (null == contributor) {
                contributor = Contributor.createNew();
            }

            Bundle arguments = new Bundle();
            arguments.putSerializable("item", contributor);
            arguments.putSerializable("names", IncomeExpenseRequestWrapper.getAvailableContributorsName(getContentResolver(), contributor));

            ContributorEditorFragment fragment = new ContributorEditorFragment();
            fragment.addListener(this);
            fragment.setArguments(arguments);
            fragment.setRetainInstance(true);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contributor_editor_container, fragment)
                    .commit();
        }
    }

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

    @Override
    public void onItemStateChange(ItemStateChangeEvent event) {

        if (event == null) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        if (event.isCancelled()) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        // Ici le contributor ne pas etre null
        // car il y a une validation dans le constructeur de ItemStateChangeEvent

        Contributor contributor = (Contributor) event.getItem();

        // Pas besoin de sauvegarde, l'item n'a pas ete modifie
        if (!contributor.isDirty()) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        mRepositorySynchronizer.Save(contributor);

        setResult(RESULT_OK);
        finish();

    }

}