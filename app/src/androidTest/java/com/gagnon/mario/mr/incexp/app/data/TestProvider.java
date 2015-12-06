package com.gagnon.mario.mr.incexp.app.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by mario on 12/6/2015.
 */
public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void deleteAllRecordsFromProvider() {

        mContext.getContentResolver().delete(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Category table during delete", 0, cursor.getCount());
        cursor.close();

    }

    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // IncomeExpenseProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                IncomeExpenseProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: IncomeExpenseProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + IncomeExpenseContract.CONTENT_AUTHORITY,
                    providerInfo.authority, IncomeExpenseContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    public void testGetType() {

        // content://com.gagnon.mario.mr.incexp.app/category/
        String type = mContext.getContentResolver().getType(IncomeExpenseContract.CategoryEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.gagnon.mario.mr.incexp.app/category
        assertEquals("Error: the CategoryEntry CONTENT_URI should return CategoryEntry.CONTENT_TYPE",
                IncomeExpenseContract.CategoryEntry.CONTENT_TYPE, type);

        long id = 12;
        // content://com.gagnon.mario.mr.incexp.app/category/12
        type = mContext.getContentResolver().getType(
                IncomeExpenseContract.CategoryEntry.buildCategoryUri(id));
        // vnd.android.cursor.item/com.gagnon.mario.mr.incexp.app/category
        assertEquals("Error: the CategoryEntry CONTENT_URI with id should return CategoryEntry.CONTENT_TYPE",
                IncomeExpenseContract.CategoryEntry.CONTENT_ITEM_TYPE, type);

    }

    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if the basic weather query functionality
        given in the ContentProvider is working correctly.
     */
    public void testBasicCategoryQuery() {
        // insert our test records into the database
        IncomeExpenseDbHelper dbHelper = new IncomeExpenseDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createAutomobileCategoryValues();
        long locationRowId = TestUtilities.insertAutomobileCategoryValues(mContext);
        assertTrue("Unable to Insert CategoryEntry into the Database", locationRowId != -1);

        db.close();

        // Test the basic content provider query
        Cursor categoryCursor = mContext.getContentResolver().query(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        TestUtilities.validateCursor("testBasicCategoryQuery", categoryCursor, testValues);
    }

    public void testUpdateLocation() {

        // Create a new map of values, where column names are the keys
        ContentValues values = TestUtilities.createAutomobileCategoryValues();

        Uri locationUri = mContext.getContentResolver().
                insert(IncomeExpenseContract.CategoryEntry.CONTENT_URI, values);
        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(IncomeExpenseContract.CategoryEntry._ID, locationRowId);
        updatedValues.put(IncomeExpenseContract.CategoryEntry.COLUMN_NAME, "Voiture");

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor categoryCursor = mContext.getContentResolver().query(IncomeExpenseContract.CategoryEntry.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        categoryCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI, updatedValues, IncomeExpenseContract.CategoryEntry.COLUMN_ID + "= ?",
                new String[] { Long.toString(locationRowId)});
        assertEquals(count, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        //
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();

        categoryCursor.unregisterContentObserver(tco);
        categoryCursor.close();

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI,
                null,   // projection
                IncomeExpenseContract.CategoryEntry.COLUMN_ID + " = " + locationRowId,
                null,   // Values for the "where" clause
                null    // sort order
        );

        TestUtilities.validateCursor("testUpdateLocation.  Error validating location entry update.",
                cursor, updatedValues);

        cursor.close();
    }

    public void testInsertReadProvider() {
        ContentValues testValues = TestUtilities.createAutomobileCategoryValues();

        // Register a content observer for our insert.  This time, directly with the content resolver
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(IncomeExpenseContract.CategoryEntry.CONTENT_URI, true, tco);
        Uri locationUri = mContext.getContentResolver().insert(IncomeExpenseContract.CategoryEntry.CONTENT_URI, testValues);

        // Did our content observer get called?  Students:  If this fails, your insert location
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating LocationEntry.",
                cursor, testValues);

    }

    public void testDeleteRecords() {
        testInsertReadProvider();

        // Register a content observer for our location delete.
        TestUtilities.TestContentObserver locationObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(IncomeExpenseContract.CategoryEntry.CONTENT_URI, true, locationObserver);

        deleteAllRecordsFromProvider();

        // Students: If either of these fail, you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
        // delete.  (only if the insertReadProvider is succeeding)
        locationObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(locationObserver);

    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
    static ContentValues[] createBulkInsertCategoryValues() {
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues weatherValues = new ContentValues();
            weatherValues.put(IncomeExpenseContract.CategoryEntry.COLUMN_NAME, "CAT" + String.valueOf(i));
            weatherValues.put(IncomeExpenseContract.CategoryEntry.COLUMN_SUBCATEGORY, "SUBCAT" + String.valueOf(i));
            returnContentValues[i] = weatherValues;
        }
        return returnContentValues;
    }

    public void testBulkInsert() {

        ContentValues[] bulkInsertContentValues = createBulkInsertCategoryValues();

        // Register a content observer for our bulk insert.
        TestUtilities.TestContentObserver weatherObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(IncomeExpenseContract.CategoryEntry.CONTENT_URI, true, weatherObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(IncomeExpenseContract.CategoryEntry.CONTENT_URI, bulkInsertContentValues);

        // Students:  If this fails, it means that you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in your BulkInsert
        // ContentProvider method.
        weatherObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(weatherObserver);

        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                IncomeExpenseContract.CategoryEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext() ) {
            TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating WeatherEntry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }

}
