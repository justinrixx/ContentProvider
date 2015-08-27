package com.gmail.rixx.justin.contentprovidertest.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * A content provider to access the database
 */
public class Provider extends ContentProvider {

    /* The integer codes for the various queries */
    private static final int MAKES = 1;
    private static final int MAKES_ID = 2;
    private static final int CARS = 3;
    private static final int CARS_ID = 4;
    private static final int CARS_COLOR = 5;

    /* Set up the UriMatcher */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.MakeEntry.TABLE_NAME, MAKES);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.MakeEntry.TABLE_NAME + "/#", MAKES_ID);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.CarEntry.TABLE_NAME, CARS);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.CarEntry.TABLE_NAME + "/#", CARS_ID);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.CarEntry.TABLE_NAME + "/color/*", CARS_COLOR);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    /**
     * Query for data in the database
     * @param uri What type of data to look for. Valid URIs can be obtained from the Contract class
     * @param projection Which columns to look for. The column names are found in the Contract class
     * @param selection Selection statement for an SQL query
     * @param selectionArgs Arguments to match the selection statement
     * @param sortOrder Not used
     * @return A cursor pointing to the data specified by the URI
     *
     * @throws IllegalStateException Be sure to use a valid URI
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Which table to look in
        String table;

        /* Figure out what to do based on the type of URI */
        switch (sUriMatcher.match(uri)) {

            case MAKES:
                table = Contract.MakeEntry.TABLE_NAME;
                break;

            case MAKES_ID:
                table = Contract.MakeEntry.TABLE_NAME;
                selection = selection + Contract.MakeEntry._ID + " = " + uri.getLastPathSegment();
                break;

            case CARS:
                table = Contract.CarEntry.TABLE_NAME;
                break;

            case CARS_ID:
                table = Contract.CarEntry.TABLE_NAME;
                selection = selection + Contract.CarEntry._ID + " = " + uri.getLastPathSegment();
                break;

            case CARS_COLOR:
                table = Contract.CarEntry.TABLE_NAME;
                selection = selection + Contract.CarEntry.COLUMN_MAKE + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Invalid URI");

        }

        // Do the actual query
        DBHelper mDBHelper = new DBHelper(getContext());

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor result = db.query(table, projection, selection, selectionArgs, null, null, null);

        db.close();

        return result;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * Insert an item into the database
     * @param uri A URI specifying which table to insert into
     * @param values The data to insert. This must include all the COLUMN_* constants for the entry
     *               <p/> specified in the Contract class
     * @return A URI pointing to the new item
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Which table does the URI specify?
        switch (sUriMatcher.match(uri)) {

            case CARS: {

                // Check for incomplete data
                if (!values.containsKey(Contract.CarEntry.COLUMN_MAKE)
                        || !values.containsKey(Contract.CarEntry.COLUMN_MODEL)
                        || !values.containsKey(Contract.CarEntry.COLUMN_YEAR)) {
                    throw new IllegalArgumentException("Provided ContentValues object does not contain all the necessary columns. See the Contract class for details");
                }

                // insert the item into the database
                DBHelper mDBHelper = new DBHelper(getContext());
                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                long id = db.insert(Contract.CarEntry.TABLE_NAME, null, values);

                db.close();

                // return the URI to the new item
                return ContentUris.withAppendedId(uri, id);
            }

            case MAKES: {

                // Check for incomplete data
                if (!values.containsKey(Contract.MakeEntry.COLUMN_NAME)) {
                    throw new IllegalArgumentException("Provided ContentValues object does not contain all the necessary columns. See the Contract class for details");
                }

                // insert the item into the database
                DBHelper mDBHelper = new DBHelper(getContext());
                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                long id = db.insert(Contract.MakeEntry.TABLE_NAME, null, values);

                db.close();

                // return the URI to the new item
                return ContentUris.withAppendedId(uri, id);
            }
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
