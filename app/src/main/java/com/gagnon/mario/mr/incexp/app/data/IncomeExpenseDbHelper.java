package com.gagnon.mario.mr.incexp.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mario on 11/30/2015.
 */
public class IncomeExpenseDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = IncomeExpenseDbHelper.class.getSimpleName();

    static final String DATABASE_NAME = "incexp.db";
    private static final int DATABASE_VERSION = 3;

    public IncomeExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " + IncomeExpenseContract.TransactionEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.TransactionEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.TransactionEntry.COLUMN_ACCOUNT_ID + " INTEGER NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_TYPE + " TEXT NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_DATE + " INTEGER NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_AMOUNT + " NUMERIC NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_CURRENCY + " TEXT NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_EXCHANGE_RATE + " NUMERIC NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_PAYMENT_METHOD_ID + " INTEGER NOT NULL," +
                IncomeExpenseContract.TransactionEntry.COLUMN_NOTE + " TEXT," +
                IncomeExpenseContract.TransactionEntry.COLUMN_IMAGE_PATH + " TEXT" +

                " );";

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + IncomeExpenseContract.CategoryEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.CategoryEntry.COLUMN_NAME + " TEXT NOT NULL," +
                IncomeExpenseContract.CategoryEntry.COLUMN_GROUP + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE " + IncomeExpenseContract.AccountEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.AccountEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.AccountEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
                IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY + " TEXT NOT NULL," +
                IncomeExpenseContract.AccountEntry.COLUMN_CLOSE + " INTEGER NOT NULL DEFAULT 0" +
                " );";

        final String SQL_CREATE_CONTRIBUTOR_TABLE = "CREATE TABLE " + IncomeExpenseContract.ContributorEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.ContributorEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.ContributorEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL" +
                " );";

        final String SQL_CREATE_ACCOUNT_CONTRIBUTOR_TABLE = "CREATE TABLE " + IncomeExpenseContract.AccountContributorEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.AccountContributorEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID + " INTEGER, " +
                IncomeExpenseContract.AccountContributorEntry.COLUMN_CONTRIBUTOR_ID + " INTEGER" +
                " );";

        final String SQL_CREATE_PAYMENT_METHOD_TABLE = "CREATE TABLE " + IncomeExpenseContract.PaymentMethodEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.PaymentMethodEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
                IncomeExpenseContract.PaymentMethodEntry.COLUMN_CURRENCY + " TEXT NOT NULL," +
                IncomeExpenseContract.PaymentMethodEntry.COLUMN_EXCHANGE_RATE + " NUMERIC NOT NULL DEFAULT 1," +
                IncomeExpenseContract.PaymentMethodEntry.COLUMN_CLOSE + " INTEGER NOT NULL DEFAULT 0" +
                " );";

        final String SQL_CREATE_PAYMENT_METHOD_CONTRIBUTOR_TABLE = "CREATE TABLE " + IncomeExpenseContract.PaymentMethodContributorEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.PaymentMethodContributorEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID + " INTEGER, " +
                IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_CONTRIBUTOR_ID + " INTEGER" +
                " );";

        final String SQL_CREATE_ACCOUNT_CATEGORY_TABLE = "CREATE TABLE " + IncomeExpenseContract.AccountCategoryEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.AccountCategoryEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.AccountCategoryEntry.COLUMN_ACCOUNT_ID + " INTEGER, " +
                IncomeExpenseContract.AccountCategoryEntry.COLUMN_CATEGORY_ID + " INTEGER" +
                " );";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_CONTRIBUTOR_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_CONTRIBUTOR_TABLE);
        db.execSQL(SQL_CREATE_PAYMENT_METHOD_TABLE);
        db.execSQL(SQL_CREATE_PAYMENT_METHOD_CONTRIBUTOR_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, String.format("Upgrading database from version %1$d to %2$d, which will destroy all old data.", oldVersion, newVersion) );
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.CategoryEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.AccountEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.ContributorEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.AccountContributorEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.PaymentMethodEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.PaymentMethodContributorEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.AccountCategoryEntry.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %1$s", IncomeExpenseContract.TransactionEntry.TABLE_NAME));
        onCreate(db);

    }
}
