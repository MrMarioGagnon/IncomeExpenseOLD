package com.gagnon.mario.mr.incexp.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mario on 11/30/2015.
 */
public class IncomeExpenseDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "incomeexpense.db";

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

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
