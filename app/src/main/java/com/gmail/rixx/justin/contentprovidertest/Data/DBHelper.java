package com.gmail.rixx.justin.contentprovidertest.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A database helper to create and access the database
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    static final String DATABASE_NAME = "cars.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MAKES_TABLE = "CREATE TABLE "
                + Contract.MakeEntry.TABLE_NAME + " ("
                + Contract.MakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contract.MakeEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ");";

        final String SQL_CREATE_CARS_TABLE = "CREATE TABLE "
                + Contract.CarEntry.TABLE_NAME + " ("
                + Contract.CarEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contract.CarEntry.COLUMN_MAKE + " TEXT NOT NULL REFERENCES "
                + Contract.MakeEntry.TABLE_NAME + "(" + Contract.MakeEntry.COLUMN_NAME + ") "
                + " ON UPDATE CASCADE ON DELETE CASCADE,"
                + Contract.CarEntry.COLUMN_MODEL + "TEXT NOT NULL,"
                + Contract.CarEntry.COLUMN_YEAR + "INTEGER NOT NULL);";

        Log.v("DB", "Makes: " + SQL_CREATE_MAKES_TABLE);
        Log.v("DBHelper", "Cars: " + SQL_CREATE_CARS_TABLE);

        db.execSQL(SQL_CREATE_MAKES_TABLE);
        db.execSQL(SQL_CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Contract.CarEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.MakeEntry.TABLE_NAME);

        onCreate(db);
    }
}
