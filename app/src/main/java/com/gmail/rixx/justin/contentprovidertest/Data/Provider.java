package com.gmail.rixx.justin.contentprovidertest.Data;

import android.content.ContentProvider;
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

    /* Set up the URI matcher */
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

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // These are used to query and are different for each URI type
        String table;
        String[] columns = null;

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

        Cursor result = db.query(table, columns, selection, selectionArgs, null, null, null);

        db.close();

        return result;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
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
