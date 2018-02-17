package com.example.thebakery.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class BakeryContractClass {
    public static final String CONTENT_AUTHORITY ="com.example.thebakery";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String PATH_RECIPE ="ingredtable";

    public static final class nameClass implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String _TABLE_NAME = "ingredtable";
        public static final String _COLUMN_INGRED_KEY = "key";
        public static final String _COLUMN_INGRED_VALUE = "value";
        public static final String _COLUMN_INGRED_QUANTITY = "quantity";
        public static final String _COLUMN_INGRED_MEASURE = "measure";
    }
}
