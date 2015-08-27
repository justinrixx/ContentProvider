package com.gmail.rixx.justin.contentprovidertest.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Stores constants for table names, column names etc.
 */
public class Contract {

    /**
     * Content authority for the content provider. This is the package name
     */
    public static final String CONTENT_AUTHORITY = "com.gmail.rixx.justin.contentprovidertest.provider";

    /**
     * The base URI to fetch content from the content provider
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible paths from the base URI. These refer to their respective database tables
     */
    public static final String PATH_MAKES = "makes";
    public static final String PATH_CARS = "cars";

    /**
     * All the constants defined for the makes table in the database
     */
    public static final class MakeEntry implements BaseColumns {

        // The URI to access the makes table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MAKES).build();

        public static final String TABLE_NAME  = "makes";

        // column storing the name of the make as a string
        public static final String COLUMN_NAME = "name";
    }

    /**
     * All the constants defined for the cars table in the database
     */
    public static final class CarEntry implements BaseColumns {

        // The URI to access the makes table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARS).build();

        public static final String TABLE_NAME  = "cars";

        // column storing the name of the make as a string
        // this corresponds to a make in the makes table
        public static final String COLUMN_MAKE = "make";

        // column storing the model of the car as a string
        public static final String COLUMN_MODEL = "model";

        // column storing the year of the car as an integer
        public static final String COLUMN_YEAR = "year";
    }
}
