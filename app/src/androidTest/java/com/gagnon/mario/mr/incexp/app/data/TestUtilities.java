package com.gagnon.mario.mr.incexp.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.gagnon.mario.mr.incexp.app.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

/**
 * Created by mario on 12/5/2015.
 */
public class TestUtilities extends AndroidTestCase {

    static final String AUTOMOBILE_CATEGORY = "Automobile";
    static final String AUTOMOBILE_SUBCATEGORY = "Insurance|Parking|Pay Toll";

    static final String HOUSE_ACCOUNT = "House";
    static final String CURRENCY_ACCOUNT = "CAD";

    static final String NATHALIE_CONTRIBUTOR = "Nathalie";

    static ContentValues createHouseAccountValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(IncomeExpenseContract.AccountEntry.COLUMN_NAME, HOUSE_ACCOUNT);
        testValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY, CURRENCY_ACCOUNT);
        return testValues;
    }

    static ContentValues createNathalieContributorValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(IncomeExpenseContract.ContributorEntry.COLUMN_NAME, NATHALIE_CONTRIBUTOR);
        return testValues;
    }


    static ContentValues createAutomobileCategoryValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(IncomeExpenseContract.CategoryEntry.COLUMN_NAME, AUTOMOBILE_CATEGORY);
        testValues.put(IncomeExpenseContract.CategoryEntry.COLUMN_SUBCATEGORY, AUTOMOBILE_SUBCATEGORY);
        return testValues;
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static long insertAutomobileCategoryValues(Context context) {
        // insert our test records into the database
        IncomeExpenseDbHelper dbHelper = new IncomeExpenseDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createAutomobileCategoryValues();

        long locationRowId;
        locationRowId = db.insert(IncomeExpenseContract.CategoryEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert Automobile Category Values", locationRowId != -1);

        return locationRowId;
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }


}
