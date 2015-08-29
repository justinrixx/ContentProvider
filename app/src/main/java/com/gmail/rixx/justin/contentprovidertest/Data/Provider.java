package com.gmail.rixx.justin.contentprovidertest.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
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

    private DBHelper mDBHelper;

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


    /**
     * Just initialize the SQLiteOpenHelper object
     * @return true when done
     */
    @Override
    public boolean onCreate() {

        mDBHelper = new DBHelper(getContext());

        return true;
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
                throw new IllegalArgumentException("Invalid URI " + uri.toString());

        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor result = db.query(table, projection, selection, selectionArgs, null, null, null);

        db.close();

        return result;
    }

    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {

            case MAKES:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/Make";
            case MAKES_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Make";
            case CARS:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/Car";
            case CARS_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Car";
            default:
                return null;
        }
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
                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                long id = db.insert(Contract.MakeEntry.TABLE_NAME, null, values);

                db.close();

                // return the URI to the new item
                return ContentUris.withAppendedId(uri, id);
            }
        }

        return null;
    }

    /**
     * Delete an entry from the database. This only allows deleting single entries. The URI must
     * <p/> include the id of the entry to delete
     * @param uri The URI pointing to the entry to delete
     * @param selection Ignored
     * @param selectionArgs Ignored
     * @return The number of rows deleted
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (sUriMatcher.match(uri)) {

            case CARS_ID: {

                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                // only delete the one that matches the ID
                int numAffected = db.delete(Contract.CarEntry.TABLE_NAME, Contract.CarEntry._ID + " = ? ",
                        new String[] { uri.getLastPathSegment() });

                db.close();

                return numAffected;
            }

            case MAKES_ID: {

                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                int numAffected = db.delete(Contract.MakeEntry.TABLE_NAME, Contract.MakeEntry._ID + " = ? ",
                        new String[] { uri.getLastPathSegment() });

                db.close();

                return numAffected;
            }

            // any other type of URI is not supported
            default:
                throw new IllegalArgumentException("Unsupported delete URI " + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        switch (sUriMatcher.match(uri)) {

            case CARS: {

                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                int numAffected = db.update(Contract.CarEntry.TABLE_NAME, values, selection, selectionArgs);

                db.close();

                return numAffected;
            }

            case MAKES: {

                SQLiteDatabase db = mDBHelper.getWritableDatabase();

                int numAffected = db.update(Contract.MakeEntry.TABLE_NAME, values, selection, selectionArgs);

                db.close();

                return numAffected;
            }

            default:
                throw new IllegalArgumentException("Unsupported update URI " + uri.toString());
        }
    }
}
