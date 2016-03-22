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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.account.AccountEditorFragment;
import com.gagnon.mario.mr.incexp.app.account.AccountRepositorySynchronizer;
import com.gagnon.mario.mr.incexp.app.core.ItemRepositorySynchronizerException;
import com.gagnon.mario.mr.incexp.app.core.ItemRepositorySynchronizerMessageBuilder;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeEvent;
import com.gagnon.mario.mr.incexp.app.core.ItemStateChangeListener;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseRequestWrapper;

public class PaymentMethodEditorActivity extends AppCompatActivity implements ItemStateChangeListener {

    private static final String LOG_TAG = PaymentMethodEditorActivity.class.getSimpleName();
    private PaymentMethodRepositorySynchronizer mRepositorySynchronizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_editor_activity);

        mRepositorySynchronizer = new PaymentMethodRepositorySynchronizer(getContentResolver(), IncomeExpenseContract.PaymentMethodEntry.CONTENT_URI, ItemRepositorySynchronizerMessageBuilder.build(this, PaymentMethodRepositorySynchronizer.class.getSimpleName()));

        if (savedInstanceState == null) {

            PaymentMethod paymentMethod = null;

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                paymentMethod = (PaymentMethod) bundle.getSerializable("item");
            }

            if (null == paymentMethod) {
                paymentMethod = PaymentMethod.createNew();
            }

            Bundle arguments = new Bundle();
            arguments.putSerializable("item", paymentMethod);
            arguments.putSerializable("names", IncomeExpenseRequestWrapper.getAvailablePaymentMethodsName(getContentResolver(), paymentMethod));
            //arguments.putSerializable("contributors", IncomeExpenseRequestWrapper.getAvailableContributors(getContentResolver()));

            PaymentMethodEditorFragment fragment = new PaymentMethodEditorFragment();
            fragment.addListener(this);
            fragment.setArguments(arguments);
            fragment.setRetainInstance(true);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.payment_method_editor_container, fragment)
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

        // Ici l'paymentMethod ne pas etre null
        // car il y a une validation dans le constructeur de ItemStateChangeEvent
        PaymentMethod paymentMethod = (PaymentMethod) event.getItem();

        // Pas besoin de sauvegarde, l'item n'a pas ete modifie
        if (!paymentMethod.isDirty()) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        try {
            mRepositorySynchronizer.Save(paymentMethod);
        } catch (ItemRepositorySynchronizerException ex) {
            String message;
            switch (ex.getAction()) {
                case delete:
                    message = getString(R.string.user_error_deleting_item);
                    break;
                case add:
                    message = getString(R.string.user_error_adding_item);
                    break;
                case update:
                    message = getString(R.string.user_error_updating_item);
                    break;
                default:
                    message = getString(R.string.user_error_synch_item);
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();

    }
}