package com.gagnon.mario.mr.incexp.app.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.category.Category;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.payment_method.PaymentMethod;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by mario on 3/7/2016.
 */
public class IncomeExpenseRequestWrapper {

    public static ArrayList<String> getAvailableContributorsName(@NonNull ContentResolver contentResolver, @NonNull Contributor contributor) {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;

        Cursor cursor = null;
        try {

            String selection = String.format("%1$s !=?", IncomeExpenseContract.ContributorEntry.COLUMN_ID);
            // Si contributor est new le id va etre null, donc remplacer par -1
            String[] selectionArgument = new String[]{contributor.isNew() ? "-1" : contributor.getId().toString()};

            cursor = contentResolver.query(uri, new String[]{IncomeExpenseContract.ContributorEntry.COLUMN_NAME}, selection, selectionArgument, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.ContributorEntry.COLUMN_NAME));
                names.add(name.toUpperCase());
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return names;
    }

    public static ArrayList<String> getAvailableCategoryName(@NonNull ContentResolver contentResolver, @NonNull Category category) {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.CategoryEntry.CONTENT_URI;

        Cursor cursor = null;
        try {

            String selection = String.format("%1$s !=?", IncomeExpenseContract.CategoryEntry.COLUMN_ID);
            // Si contributor est new le id va etre null, donc remplacer par -1
            String[] selectionArgument = new String[]{category.isNew() ? "-1" : category.getId().toString()};

            cursor = contentResolver.query(uri, new String[]{IncomeExpenseContract.CategoryEntry.COLUMN_NAME}, selection, selectionArgument, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.CategoryEntry.COLUMN_NAME));
                names.add(name.toUpperCase());
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return names;
    }

    public static TreeSet<Contributor> getAvailableContributors(@NonNull ContentResolver contentResolver) {

        TreeSet<Contributor> contributors = new TreeSet<>();

        Uri uri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;

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

    public static ArrayList<String> getAvailableAccountsName(@NonNull ContentResolver contentResolver, Account account) {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.AccountEntry.CONTENT_URI;

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

    public static ArrayList<String> getAvailablePaymentMethodsName(@NonNull ContentResolver contentResolver, PaymentMethod paymentMethod) {

        ArrayList<String> names = new ArrayList<>();

        Uri uri = IncomeExpenseContract.PaymentMethodEntry.CONTENT_URI;

        Cursor cursor = null;
        try {

            String selection = String.format("%1$s !=?", IncomeExpenseContract.PaymentMethodEntry.COLUMN_ID);
            // Si paymentMethod est new le id va etre null, donc remplacer par -1
            String[] selectionArgument = new String[]{paymentMethod.isNew() ? "-1" : paymentMethod.getId().toString()};

            cursor = contentResolver.query(uri, new String[]{IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME}, selection, selectionArgument, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME));
                names.add(name.toUpperCase());
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return names;
    }


    public static ArrayList<Contributor> getAccountContributors(@NonNull ContentResolver contentResolver, Long accountId){

        ArrayList<Contributor> contributors = new ArrayList<>();
        Cursor cursor = null;
        try {

            String[] projection  = new String[] {IncomeExpenseContract.AccountContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.AccountContributorEntry.COLUMN_ID
                       ,IncomeExpenseContract.ContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.ContributorEntry.COLUMN_ID
                        ,IncomeExpenseContract.ContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.ContributorEntry.COLUMN_NAME};

            String selection = IncomeExpenseContract.AccountContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.AccountContributorEntry.COLUMN_ACCOUNT_ID + "=?";
            String[] selectionArgs = new String[] {accountId.toString()};

            cursor = contentResolver.query(IncomeExpenseContract.AccountContributorEntry.CONTENT_URI,projection, selection, selectionArgs, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Long id = cursor.getLong(1);
                String name = cursor.getString(2);
                contributors.add(Contributor.create(id, name));
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return contributors;



    }

    public static ArrayList<Contributor> getPaymentMethodContributors(@NonNull ContentResolver contentResolver, Long paymentMethodId){

        ArrayList<Contributor> contributors = new ArrayList<>();
        Cursor cursor = null;
        try {

            String[] projection  = new String[] {IncomeExpenseContract.PaymentMethodContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_ID
                    ,IncomeExpenseContract.ContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.ContributorEntry.COLUMN_ID
                    ,IncomeExpenseContract.ContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.ContributorEntry.COLUMN_NAME};

            String selection = IncomeExpenseContract.PaymentMethodContributorEntry.TABLE_NAME + "." + IncomeExpenseContract.PaymentMethodContributorEntry.COLUMN_PAYMENT_METHOD_ID + "=?";
            String[] selectionArgs = new String[] {paymentMethodId.toString()};

            cursor = contentResolver.query(IncomeExpenseContract.PaymentMethodContributorEntry.CONTENT_URI,projection, selection, selectionArgs, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Long id = cursor.getLong(1);
                String name = cursor.getString(2);
                contributors.add(Contributor.create(id, name));
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return contributors;

    }


}
