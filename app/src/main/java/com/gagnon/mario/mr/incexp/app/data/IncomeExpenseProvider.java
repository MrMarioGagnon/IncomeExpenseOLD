package com.gagnon.mario.mr.incexp.app.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by mario on 12/2/2015.
 */
public class IncomeExpenseProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private IncomeExpenseDbHelper mOpenHelper;

//    static final int CATEGORY = 100;
//    static final int CATEGORY_WITH_ID = 101;
//
    static final int ACCOUNT = 200;
    static final int ACCOUNT_WITH_ID = 201;

    static final int CONTRIBUTOR = 300;
    static final int CONTRIBUTOR_WITH_ID = 301;

    static final int ACCOUNT_CONTRIBUTOR = 400;
    static final int ACCOUNT_CONTRIBUTOR_WITH_ID = 401;

    private static final SQLiteQueryBuilder mAccountContributorQueryBuilder;

    static{
        mAccountContributorQueryBuilder = new SQLiteQueryBuilder();

        mAccountContributorQueryBuilder.setTables(
                IncomeExpenseContract.AccountContributorEntry.TABLE_NAME + " INNER JOIN " +
                        IncomeExpenseContract.ContributorEntry.TABLE_NAME +
                        " ON " + IncomeExpenseContract.AccountContributorEntry.TABLE_NAME +
                        "." + IncomeExpenseContract.AccountContributorEntry.COLUMN_CONTRIBUTOR_ID +
                        "=" + IncomeExpenseContract.ContributorEntry.COLUMN_ID);
    }


//    private static final String sCategoryIdSelection =
//            IncomeExpenseContract.CategoryEntry.TABLE_NAME +
//                    "." + IncomeExpenseContract.CategoryEntry.COLUMN_ID + " = ? ";
//
    private static final String sAccountIdSelection =
            IncomeExpenseContract.AccountEntry.TABLE_NAME +
                    "." + IncomeExpenseContract.AccountEntry.COLUMN_ID + " = ? ";

    private static final String sContributorIdSelection =
            IncomeExpenseContract.ContributorEntry.TABLE_NAME +
                    "." + IncomeExpenseContract.ContributorEntry.COLUMN_ID + " = ? ";

    private static final String sAccountContributorIdSelection =
            IncomeExpenseContract.AccountContributorEntry.TABLE_NAME +
                    "." + IncomeExpenseContract.AccountContributorEntry.COLUMN_ID + " = ? ";

    static UriMatcher buildUriMatcher() {

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = IncomeExpenseContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
//        matcher.addURI(authority, IncomeExpenseContract.PATH_CATEGORY, CATEGORY);
//        matcher.addURI(authority, IncomeExpenseContract.PATH_CATEGORY + "/#", CATEGORY_WITH_ID);
//
        matcher.addURI(authority, IncomeExpenseContract.PATH_ACCOUNT, ACCOUNT);
        matcher.addURI(authority, IncomeExpenseContract.PATH_ACCOUNT + "/#", ACCOUNT_WITH_ID);

        matcher.addURI(authority, IncomeExpenseContract.PATH_CONTRIBUTOR, CONTRIBUTOR);
        matcher.addURI(authority, IncomeExpenseContract.PATH_CONTRIBUTOR + "/#", CONTRIBUTOR_WITH_ID);

        matcher.addURI(authority, IncomeExpenseContract.PATH_ACCOUNT_CONTRIBUTOR, ACCOUNT_CONTRIBUTOR);
        matcher.addURI(authority, IncomeExpenseContract.PATH_ACCOUNT_CONTRIBUTOR + "/#", ACCOUNT_CONTRIBUTOR_WITH_ID);

        return matcher;
    }

//    private Cursor getCategoryById(Uri uri, String[] projection) {
//
//        long id = IncomeExpenseContract.CategoryEntry.getIdFromUri(uri);
//
//        String[] selectionArgs;
//        String selection;
//
//        selection = sCategoryIdSelection;
//        selectionArgs = new String[]{String.valueOf(id)};
//
//        return mOpenHelper.getReadableDatabase().query(IncomeExpenseContract.CategoryEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null
//        );
//    }

    private Cursor getAccountById(Uri uri, String[] projection) {

        long id = IncomeExpenseContract.AccountEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sAccountIdSelection;
        selectionArgs = new String[]{String.valueOf(id)};

        return mOpenHelper.getReadableDatabase().query(IncomeExpenseContract.AccountEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    private Cursor getContributorById(Uri uri, String[] projection) {

        long id = IncomeExpenseContract.ContributorEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sContributorIdSelection;
        selectionArgs = new String[]{String.valueOf(id)};

        return mOpenHelper.getReadableDatabase().query(IncomeExpenseContract.ContributorEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    private Cursor getAccountContributorById(Uri uri, String[] projection) {

        long id = IncomeExpenseContract.AccountContributorEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sAccountContributorIdSelection;
        selectionArgs = new String[]{String.valueOf(id)};

        return mOpenHelper.getReadableDatabase().query(IncomeExpenseContract.AccountContributorEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new IncomeExpenseDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
//            case CATEGORY:
//            {
//                retCursor = mOpenHelper.getReadableDatabase().query(
//                      IncomeExpenseContract.CategoryEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
//                break;
//            }
//            case CATEGORY_WITH_ID:
//            {
//                retCursor = getCategoryById(uri, projection);
//                break;
//            }
            case ACCOUNT:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        IncomeExpenseContract.AccountEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ACCOUNT_WITH_ID:
                retCursor = getAccountById(uri, projection);
                break;
            case ACCOUNT_CONTRIBUTOR:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        IncomeExpenseContract.AccountContributorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ACCOUNT_CONTRIBUTOR_WITH_ID:
                retCursor = getAccountContributorById(uri, projection);
                break;
            case CONTRIBUTOR:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        IncomeExpenseContract.ContributorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CONTRIBUTOR_WITH_ID:
                retCursor = getContributorById(uri, projection);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri( getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match){
//            case CATEGORY:
//                return IncomeExpenseContract.CategoryEntry.CONTENT_TYPE;
//            case CATEGORY_WITH_ID:
//                return IncomeExpenseContract.CategoryEntry.CONTENT_ITEM_TYPE;
            case ACCOUNT:
                return IncomeExpenseContract.AccountEntry.CONTENT_TYPE;
            case ACCOUNT_WITH_ID:
                return IncomeExpenseContract.AccountEntry.CONTENT_ITEM_TYPE;
            case ACCOUNT_CONTRIBUTOR:
                return IncomeExpenseContract.AccountContributorEntry.CONTENT_TYPE;
            case ACCOUNT_CONTRIBUTOR_WITH_ID:
                return IncomeExpenseContract.AccountContributorEntry.CONTENT_ITEM_TYPE;
            case CONTRIBUTOR:
                return IncomeExpenseContract.ContributorEntry.CONTENT_TYPE;
            case CONTRIBUTOR_WITH_ID:
                return IncomeExpenseContract.ContributorEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " +uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
//            case CATEGORY: {
//                long _id = db.insert(IncomeExpenseContract.CategoryEntry.TABLE_NAME, null, values);
//                if ( _id > 0 )
//                    returnUri = IncomeExpenseContract.CategoryEntry.buildInstanceUri(_id);
//                else
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                break;
//            }
            case ACCOUNT: {
                long _id = db.insert(IncomeExpenseContract.AccountEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = IncomeExpenseContract.AccountEntry.buildInstanceUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case ACCOUNT_CONTRIBUTOR: {
                long _id = db.insert(IncomeExpenseContract.AccountContributorEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = IncomeExpenseContract.AccountContributorEntry.buildInstanceUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CONTRIBUTOR: {
                long _id = db.insert(IncomeExpenseContract.ContributorEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = IncomeExpenseContract.ContributorEntry.buildInstanceUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
//            case CATEGORY:
//                rowsDeleted = db.delete(
//                        IncomeExpenseContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
//                break;
            case ACCOUNT:
                rowsDeleted = db.delete(
                        IncomeExpenseContract.AccountEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ACCOUNT_CONTRIBUTOR:
                rowsDeleted = db.delete(
                        IncomeExpenseContract.AccountContributorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTRIBUTOR:
                rowsDeleted = db.delete(
                        IncomeExpenseContract.ContributorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
//            case CATEGORY:
//                rowsUpdated = db.update(IncomeExpenseContract.CategoryEntry.TABLE_NAME, values, selection,
//                        selectionArgs);
//                break;
            case ACCOUNT:
                rowsUpdated = db.update(IncomeExpenseContract.AccountEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case ACCOUNT_CONTRIBUTOR:
                rowsUpdated = db.update(IncomeExpenseContract.AccountContributorEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CONTRIBUTOR:
                rowsUpdated = db.update(IncomeExpenseContract.ContributorEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
//            case CATEGORY:
//                db.beginTransaction();
//                try {
//                    for (ContentValues value : values) {
//                        long _id = db.insert(IncomeExpenseContract.CategoryEntry.TABLE_NAME, null, value);
//                        if (_id != -1) {
//                            returnCount++;
//                        }
//                    }
//                    db.setTransactionSuccessful();
//                } finally {
//                    db.endTransaction();
//                }
//                getContext().getContentResolver().notifyChange(uri, null);
//                return returnCount;
            case ACCOUNT:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(IncomeExpenseContract.AccountEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case ACCOUNT_CONTRIBUTOR:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(IncomeExpenseContract.AccountContributorEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case CONTRIBUTOR:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(IncomeExpenseContract.ContributorEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }

    public Cursor getAccountContributor(Long accountId){

        String selection = IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID + "=?";
        String[] selectionArgs = new String[] {accountId.toString()};

        return mAccountContributorQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

}
