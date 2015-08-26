package com.gmail.rixx.justin.contentprovidertest.Data;

import android.provider.BaseColumns;

/**
 * Stores constants for table names, column names etc.
 */
public class Contract {

    public static final String TABLE_MAKES = "makes";
    public static final String TABLE_CARS  = "cars";

    /**
     * All the constants defined for the makes table in the database
     */
    public static final class MakeEntry implements BaseColumns {

        public static final String TABLE_NAME  = "makes";

        // column storing the name of the make as a string
        public static final String COLUMN_NAME = "name";
    }

    /**
     * All the constants defined for the cars table in the database
     */
    public static final class CarEntry implements BaseColumns {

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
