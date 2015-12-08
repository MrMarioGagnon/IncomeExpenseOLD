package com.gagnon.mario.mr.incexp.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mario on 11/30/2015.
 */
public class IncomeExpenseDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "incexp.db";

    public IncomeExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + IncomeExpenseContract.CategoryEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.CategoryEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
                IncomeExpenseContract.CategoryEntry.COLUMN_SUBCATEGORY + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE " + IncomeExpenseContract.AccountEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.AccountEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.AccountEntry.COLUMN_NAME + " TEXT NOT NULL," +
                IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_CONTRIBUTOR_TABLE = "CREATE TABLE " + IncomeExpenseContract.ContributorEntry.TABLE_NAME + " (" +
                IncomeExpenseContract.ContributorEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeExpenseContract.ContributorEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL" +
                " );";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_CONTRIBUTOR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
