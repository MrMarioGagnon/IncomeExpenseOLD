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
import android.support.v7.app.AppCompatActivity;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.core.ItemRepositorySynchronizerMessageBuilder;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeEvent;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeListener;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseRequestWrapper;

public class CategoryEditorActivity extends AppCompatActivity implements ItemStateChangeListener {

    private static final String LOG_TAG = CategoryEditorActivity.class.getSimpleName();
    private CategoryRepositorySynchronizer mRepositorySynchronizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_editor_activity);

        mRepositorySynchronizer = new CategoryRepositorySynchronizer(getContentResolver(), IncomeExpenseContract.CategoryEntry.CONTENT_URI, ItemRepositorySynchronizerMessageBuilder.build(this, CategoryRepositorySynchronizer.class.getSimpleName()));

        if (savedInstanceState == null) {

            Category category = null;

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                category = (Category) bundle.getSerializable("item");
            }

            if (null == category) {
                category = Category.createNew();
            }

            Bundle arguments = new Bundle();
            arguments.putSerializable("item", category);
            arguments.putSerializable("names", IncomeExpenseRequestWrapper.getAvailableCategoryNameForValidation(getContentResolver(), category));

            CategoryEditorFragment fragment = new CategoryEditorFragment();
            fragment.addListener(this);
            fragment.setArguments(arguments);
            fragment.setRetainInstance(true);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.category_editor_container, fragment)
                    .commit();
        }
    }

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

        Category category = (Category) event.getItem();

        // Pas besoin de sauvegarde, l'item n'a pas ete modifie
        if (!category.isDirty()) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        mRepositorySynchronizer.Save(category);

        setResult(RESULT_OK);
        finish();

    }

}