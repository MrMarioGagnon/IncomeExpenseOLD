package com.gagnon.mario.mr.incexp.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mario on 11/30/2015.
 */
public class IncomeExpenseDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "incexp.db";
    private static final int DATABASE_VERSION = 1;

    public IncomeExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + IncomeExpenseContract.CategoryEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.CategoryEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
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

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_CONTRIBUTOR_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_CONTRIBUTOR_TABLE);
        db.execSQL(SQL_CREATE_PAYMENT_METHOD_TABLE);
        db.execSQL(SQL_CREATE_PAYMENT_METHOD_CONTRIBUTOR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
