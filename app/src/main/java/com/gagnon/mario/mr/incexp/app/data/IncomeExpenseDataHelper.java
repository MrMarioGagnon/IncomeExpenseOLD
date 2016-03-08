package com.gagnon.mario.mr.incexp.app.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.util.SortedList;

import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by mario on 3/7/2016.
 */
public class IncomeExpenseDataHelper {

    public static TreeSet<Contributor> getAvailableContributors(Context context) {

        TreeSet<Contributor> contributors = new TreeSet<>();

        Uri uri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, null, null, null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Long id = cursor.getLong(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_NAME));
                contributors.add(Contributor.create(id, name));
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return contributors;
    }

    public static ArrayList<String> getAvailableAccountsName(Context context, Account account) {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.AccountEntry.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = null;
        try {

            String selection = String.format("%1$s !=?", IncomeExpenseContract.AccountEntry.COLUMN_ID);
            // Si account est new le id va etre null, donc remplacer par -1
            String[] selectionArgument = new String[]{account.isNew() ? "-1" : account.getId().toString()};

            cursor = contentResolver.query(uri, new String[]{IncomeExpenseContract.AccountEntry.COLUMN_NAME}, selection, selectionArgument, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_NAME));
                names.add(name.toUpperCase());
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return names;
    }


}
