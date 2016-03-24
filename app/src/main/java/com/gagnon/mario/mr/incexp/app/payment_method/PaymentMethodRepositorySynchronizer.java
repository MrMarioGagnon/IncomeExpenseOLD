package com.gagnon.mario.mr.incexp.app.payment_method;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ItemRepositorySynchronizerAction;
import com.gagnon.mario.mr.incexp.app.core.ItemRepositorySynchronizerException;
import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mario on 3/14/2016.
 */
public class PaymentMethodRepositorySynchronizer {

    private static final String LOG_TAG = PaymentMethodRepositorySynchronizer.class.getSimpleName();
    private final ContentResolver mContentResolver;
    private final Uri mItemUri;
    private final Map<Integer, String> mMessages;

    public PaymentMethodRepositorySynchronizer(@NonNull ContentResolver contentResolver, @NonNull Uri itemUri, @NonNull Map<Integer, String> messages) {
        mMessages = messages;
        mItemUri = itemUri;
        mContentResolver = contentResolver;
    }

    public ObjectBase Save(@NonNull ObjectBase item) throws ItemRepositorySynchronizerException {

        // region Precondition
        if (!(item instanceof PaymentMethod)) {
            throw new IllegalArgumentException("Parameter item must be an instance of Payment Method");
        }

        if ((!item.isNew()) && item.getId() == null) {
            throw new NullPointerException("Item is not new, item id is mandatory.");
        }

        if (!item.isDirty()) {
            return item;
        }
        // endregion Precondition

        final PaymentMethod itemToBeSave = (PaymentMethod) item;
        final String itemType = itemToBeSave.getClass().getSimpleName();
        Long id;

        final String selection = IncomeExpenseContract.PaymentMethodEntry.COLUMN_ID + "=?";
        final String[] selectionArgs;
        int rowsAffected;
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        ContentProviderResult[] results;

        if (itemToBeSave.isDead()) {
            // Delete item
            id = itemToBeSave.getId();
            selectionArgs = new String[]{id.toString()};

            operations.add(
                    ContentProviderOperation.newDelete(IncomeExpenseContract.PaymentMethodContributorEntry.CONTENT_URI).withSelection(
                            IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID + "=?", new String[]{String.valueOf(id)}).build());

            operations.add(
                    ContentProviderOperation.newDelete(mItemUri).withSelection(selection, selectionArgs).build()
            );

            Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_deleting_item), itemType, id));

            try {
                results = mContentResolver.applyBatch(
                        IncomeExpenseContract.CONTENT_AUTHORITY, operations);
                rowsAffected = results[0].count;
                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_number_deleted_associated_contributor), rowsAffected));

                rowsAffected = results[1].count;
                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_deleted_item), itemType, rowsAffected, id));
            } catch (Exception ex) {
                Log.e(LOG_TAG, mMessages.get(R.string.log_error_deleting_item), ex);
                throw new ItemRepositorySynchronizerException(ex, ItemRepositorySynchronizerAction.delete);
            }

        } else {
            if (itemToBeSave.isNew()) {
                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_adding_item), itemType));

                ContentValues paymentMethodValues = new ContentValues();
                paymentMethodValues.put(IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME, itemToBeSave.getName());
                paymentMethodValues.put(IncomeExpenseContract.PaymentMethodEntry.COLUMN_CURRENCY, itemToBeSave.getCurrency());
                paymentMethodValues.put(IncomeExpenseContract.PaymentMethodEntry.COLUMN_EXCHANGE_RATE, itemToBeSave.getExchangeRate());

                operations.add(
                        ContentProviderOperation.newInsert(mItemUri)
                                .withValues(paymentMethodValues)
                                .build());

                for (Contributor contributor : itemToBeSave.getContributors()) {
                    paymentMethodValues = new ContentValues();
                    paymentMethodValues.put(IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID, 0);
                    paymentMethodValues.put(IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_CONTRIBUTOR_ID, contributor.getId());
                    operations.add(
                            ContentProviderOperation.newInsert(IncomeExpenseContract.PaymentMethodContributorEntry.CONTENT_URI)
                                    .withValues(paymentMethodValues)
                                    .withValueBackReference(IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID, 0)
                                    .build());

                }

                try {

                    results = mContentResolver.applyBatch(
                            IncomeExpenseContract.CONTENT_AUTHORITY, operations);

                    id = IncomeExpenseContract.PaymentMethodEntry.getIdFromUri(results[0].uri);
                    rowsAffected = (id != null) ? 1 : 0;
                    itemToBeSave.setId(id);
                    Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_added_item), itemType, rowsAffected, id));

                    rowsAffected = 0;
                    for (int i = 1; i < results.length; i++) {
                        id = IncomeExpenseContract.PaymentMethodContributorEntry.getIdFromUri(results[i].uri);
                        rowsAffected += (id != null) ? 1 : 0;
                    }

                    Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_number_added_associated_contributor), rowsAffected));
                } catch (Exception ex) {
                    Log.e(LOG_TAG, mMessages.get(R.string.log_error_adding_item), ex);
                    throw new ItemRepositorySynchronizerException(ex, ItemRepositorySynchronizerAction.add);
                }

            } else {
                // update item
                id = itemToBeSave.getId();
                selectionArgs = new String[]{id.toString()};
                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_updating_item), itemType, id));

                ContentValues paymentMethodValues = new ContentValues();
                paymentMethodValues.put(IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME, itemToBeSave.getName());
                paymentMethodValues.put(IncomeExpenseContract.PaymentMethodEntry.COLUMN_CURRENCY, itemToBeSave.getCurrency());
                paymentMethodValues.put(IncomeExpenseContract.PaymentMethodEntry.COLUMN_EXCHANGE_RATE, itemToBeSave.getExchangeRate());

                operations.add(
                        ContentProviderOperation.newUpdate(mItemUri)
                                .withSelection(selection, selectionArgs)
                                .withValues(paymentMethodValues).build());

                operations.add(
                        ContentProviderOperation.newDelete(IncomeExpenseContract.PaymentMethodContributorEntry.CONTENT_URI)
                                .withSelection(IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID + "=?", new String[]{id.toString()})
                                .build());

                for (Contributor contributor : itemToBeSave.getContributors()) {
                    paymentMethodValues = new ContentValues();
                    paymentMethodValues.put(IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID, id);
                    paymentMethodValues.put(IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_CONTRIBUTOR_ID, contributor.getId());
                    operations.add(
                            ContentProviderOperation.newInsert(IncomeExpenseContract.PaymentMethodContributorEntry.CONTENT_URI)
                                    .withValues(paymentMethodValues)
                                    .build());
                }

                try {

                    results = mContentResolver.applyBatch(
                            IncomeExpenseContract.CONTENT_AUTHORITY, operations);

                    rowsAffected = results[0].count;
                    Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_updated_item), itemType, rowsAffected, id));

                    rowsAffected = results[1].count;
                    Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_number_deleted_associated_contributor), rowsAffected));

                    rowsAffected = 0;
                    for (int i = 2; i < results.length; i++) {
                        id = IncomeExpenseContract.PaymentMethodContributorEntry.getIdFromUri(results[i].uri);
                        rowsAffected += (id != null) ? 1 : 0;
                    }

                    Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_number_added_associated_contributor), rowsAffected));
                } catch (Exception ex) {
                    Log.e(LOG_TAG, mMessages.get(R.string.log_error_updating_item), ex);
                    throw new ItemRepositorySynchronizerException(ex, ItemRepositorySynchronizerAction.update);
                }

            }
        }

        return itemToBeSave;
    }
}
