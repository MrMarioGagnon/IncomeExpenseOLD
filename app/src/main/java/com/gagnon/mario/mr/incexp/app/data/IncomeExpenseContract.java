package com.gagnon.mario.mr.incexp.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mario on 11/30/2015.
 */
public class IncomeExpenseContract {

    public static final String CONTENT_AUTHORITY = "com.gagnon.mario.mr.incexp.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //    public static final String PATH_CATEGORY = "category";
//    public static final String PATH_SUBCATEGORY = "subcategory";
    public static final String PATH_ACCOUNT = "account";
    public static final String PATH_CONTRIBUTOR = "contributor";
    public static final String PATH_ACCOUNT_CONTRIBUTOR = "account_contributor";
    public static final String PATH_PAYMENT_METHOD = "paymentmethod";
//    public static final String PATH_ACCOUNT_CONTRIBUTOR = "accountcontributor";

//    public static final class CategoryEntry implements BaseColumns{
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;
//
//        // Table name
//        public static final String TABLE_NAME = "category";
//
//        public static final String COLUMN_ID = _ID;
//        public static final String COLUMN_NAME = "name";
//
//        public static Uri buildInstanceUri(long id){
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//        public static long getIdFromUri(Uri uri) {
//            return Integer.parseInt(uri.getPathSegments().get(1));
//        }
//
//    }

//    public static final class SubCategoryEntry implements BaseColumns{
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBCATEGORY).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBCATEGORY;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBCATEGORY;
//
//        // Table name
//        public static final String TABLE_NAME = "subcategory";
//
//        public static final String COLUMN_ID = _ID;
//        public static final String COLUMN_CATEGORYID = "categoryId";
//        public static final String COLUMN_NAME = "name";
//
//        public static Uri buildInstanceUri(long id){
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//        public static long getIdFromUri(Uri uri) {
//            return Integer.parseInt(uri.getPathSegments().get(1));
//        }
//
//    }

    public static final class AccountEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACCOUNT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACCOUNT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACCOUNT;

        public static final String TABLE_NAME = "account";

        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_CLOSE = "close";

        public static Uri buildInstanceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

    }

    public static final class AccountContributorEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACCOUNT_CONTRIBUTOR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACCOUNT_CONTRIBUTOR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACCOUNT_CONTRIBUTOR;

        public static final String TABLE_NAME = "account_contributor";

        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_ACCOUNT_ID = "accountId";
        public static final String COLUMN_CONTRIBUTOR_ID = "contributorId";

        public static Uri buildInstanceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

    }

    public static final class ContributorEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTRIBUTOR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTRIBUTOR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTRIBUTOR;

        public static final String TABLE_NAME = "contributor";

        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_NAME = "name";

        public static Uri buildInstanceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }


    }

    public static final class PaymentMethodEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PAYMENT_METHOD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PAYMENT_METHOD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PAYMENT_METHOD;

        public static final String TABLE_NAME = "payment_method";

        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_EXCHANGE_RATE = "exchangeRate";
        public static final String COLUMN_CLOSE = "close";

        public static Uri buildInstanceUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }


    }

//    public static final class AccountContributorEntry implements BaseColumns{
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACCOUNT_CONTRIBUTOR).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACCOUNT_CONTRIBUTOR;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACCOUNT_CONTRIBUTOR;
//
//        public static final String TABLE_NAME = "account_contributor";
//
//        public static final String COLUMN_ID = _ID;
//        public static final String COLUMN_ACCOUNTID = "accountId";
//        public static final String COLUMN_CONTRIBUTORID = "contributorId";
//
//        public static Uri buildInstanceUri(long id){
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//        public static long getIdFromUri(Uri uri) {
//            return Integer.parseInt(uri.getPathSegments().get(1));
//        }
//
//    }
}
