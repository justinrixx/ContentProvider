package com.gmail.rixx.justin.contentprovidertest.Data;

import android.content.ContentUris;
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

        /**
         * Append an id to the URI. Used by outside methods to get valid URIs to use with the content
         * <p/> provider
         * @param Id The desired item's ID
         * @return A valid URI pointing to that item
         */
        public static Uri getContentUriWithID(int Id) {
            return ContentUris.withAppendedId(CONTENT_URI, Id);
        }

        public static final String TABLE_NAME  = "makes";

        // column storing the name of the make as a string
        public static final String COLUMN_NAME = "name";
    }

    /**
     * All the constants defined for the cars table in the database
     */
    public static final class CarEntry implements BaseColumns {

        // The URI to access the cars table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARS).build();

        /**
         * Append an id to the URI. Used by outside methods to get valid URIs to use with the content
         * <p/> provider
         * @param Id The desired item's ID
         * @return A valid URI pointing to that item
         */
        public static Uri getContentUriWithID(int Id) {
            return ContentUris.withAppendedId(CONTENT_URI, Id);
        }

        /**
         * Get a URI to all cars with a specific color
         * @param color The color to look for
         * @return A valid URI to get them
         */
        public static Uri getContentUriWithColor(String color) {
            return CONTENT_URI.buildUpon().appendPath("color").appendPath(color).build();
        }

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
