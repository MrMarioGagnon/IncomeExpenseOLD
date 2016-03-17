package com.gagnon.mario.mr.incexp.app.account;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mario on 3/14/2016.
 */
public class AccountRepositorySynchronizer {

    private static final String LOG_TAG = AccountRepositorySynchronizer.class.getSimpleName();
    private final ContentResolver mContentResolver;
    private final Uri mItemUri;
    private final Map<Integer, String> mMessages;

    public AccountRepositorySynchronizer(@NonNull ContentResolver contentResolver, @NonNull Uri itemUri, @NonNull Map<Integer, String> messages) {
        mMessages = messages;
        mItemUri = itemUri;
        mContentResolver = contentResolver;
    }

    public ObjectBase Save(@NonNull ObjectBase item) {

        // region Precondition
        if (!(item instanceof Account)) {
            throw new IllegalArgumentException("Parameter item must be an instance of Account");
        }

        if ((!item.isNew()) && item.getId() == null) {
            throw new NullPointerException("Item is not new, item id is mandatory.");
        }

        if(!item.isDirty()){
            return item;
        }
        // endregion Precondition

        final Account itemToBeSave = (Account) item;
        final String itemType = itemToBeSave.getClass().getSimpleName();
        Long id;

        final String selection = IncomeExpenseContract.AccountEntry.COLUMN_ID + "=?";
        final String[] selectionArgs;
        int rowsAffected;

        if (itemToBeSave.isDead()) {
            // Delete item
            id = itemToBeSave.getId();
            selectionArgs = new String[]{id.toString()};

            int rowsDeleted = mContentResolver.delete(IncomeExpenseContract.AccountContributorEntry.CONTENT_URI,
                    IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID + "=?", new String[]{String.valueOf(id)});
            Log.i(LOG_TAG, String.format("Associated contributor deleted : %1$d", rowsDeleted));

            Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_deleting_item), itemType, id));
            rowsAffected = mContentResolver.delete(mItemUri, selection, selectionArgs);
            Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_deleted_item), itemType, rowsAffected, id));

        } else {
            if (itemToBeSave.isNew()) {
                // add item
//                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_adding_item), itemType));
//                ContentValues itemValues = new ContentValues();
//                itemValues.put(IncomeExpenseContract.ContributorEntry.COLUMN_NAME, itemToBeSave.getName());
//                Uri newUri = mContentResolver.insert(mItemUri, itemValues);
//                id = IncomeExpenseContract.ContributorEntry.getIdFromUri(newUri);
//                rowsAffected = (id != null) ? 1 : 0;
//                itemToBeSave.setId(id);
//                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_added_item), itemType, rowsAffected, id));

                //////

                ContentValues accountValues = new ContentValues();
                accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_NAME, itemToBeSave.getName());
                accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY, itemToBeSave.getCurrency());

                ArrayList<ContentProviderOperation> operations = new ArrayList<>();

                operations.add(
                        ContentProviderOperation.newInsert(mItemUri)
                                .withValues(accountValues)
                                .build());

                for (Contributor contributor : itemToBeSave.getContributors()) {
                    accountValues = new ContentValues();
                    accountValues.put(IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID, 0  );
                    accountValues.put(IncomeExpenseContract.AccountContributorEntry.COLUMN_CONTRIBUTOR_ID, contributor.getId());
                    operations.add(
                            ContentProviderOperation.newInsert(IncomeExpenseContract.AccountContributorEntry.CONTENT_URI)
                                    .withValues(accountValues)
                                    .withValueBackReference(IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID, 0)
                                    .build());

                }

                ContentProviderResult[] results = null;
                try {
                    results = mContentResolver.applyBatch(
                            IncomeExpenseContract.CONTENT_AUTHORITY, operations);

                    long newId = IncomeExpenseContract.AccountEntry.getIdFromUri(results[0].uri);
                    itemToBeSave.setId(newId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }



            } else {
                // update item
                id = itemToBeSave.getId();
                selectionArgs = new String[]{id.toString()};
                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_updating_item), itemType, id));
                ContentValues itemValues = new ContentValues();
                itemValues.put(IncomeExpenseContract.ContributorEntry.COLUMN_NAME, itemToBeSave.getName());
                rowsAffected = mContentResolver.update(mItemUri, itemValues, selection, selectionArgs);
                Log.i(LOG_TAG, String.format(mMessages.get(R.string.log_info_updated_item), itemType, rowsAffected, id));

/////////////////////////////
                id = itemToBeSave.getId();

                ContentValues accountValues = new ContentValues();
                accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_NAME, itemToBeSave.getName());
                accountValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY, itemToBeSave.getCurrency());

                ArrayList<ContentProviderOperation> operations = new ArrayList<>();

                operations.add(
                        ContentProviderOperation.newUpdate(mItemUri)
                                .withSelection(selection, selectionArgs)
                                .withValues(accountValues).build());

                operations.add(
                        ContentProviderOperation.newDelete(IncomeExpenseContract.AccountContributorEntry.CONTENT_URI)
                                .withSelection(IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID + "=?", new String[]{id.toString()})
                                .build());


                for (Contributor contributor : itemToBeSave.getContributors()) {
                    accountValues = new ContentValues();
                    accountValues.put(IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID, id  );
                    accountValues.put(IncomeExpenseContract.AccountContributorEntry.COLUMN_CONTRIBUTOR_ID, contributor.getId());
                    operations.add(
                            ContentProviderOperation.newInsert(IncomeExpenseContract.AccountContributorEntry.CONTENT_URI)
                                    .withValues(accountValues)
                                    .build());

                }

                ContentProviderResult[] results = null;
                try {
                    results = mContentResolver.applyBatch(
                            IncomeExpenseContract.CONTENT_AUTHORITY, operations);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
                //Log.i(LOG_TAG, getString(R.string.log_info_updating_account, id));

            }
        }

        return itemToBeSave;
    }
}
