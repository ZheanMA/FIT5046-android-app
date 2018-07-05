package com.example.yjw.assignment2;

import android.provider.BaseColumns;


public class DBStructure {
    public static abstract class tableEntry implements BaseColumns {
        public static final String TABLE_NAME = "usages";
        public static final String COLUMN_RESID = "resident";
        public static final String COLUMN_dates = "dates";
        public static final String COLUMN_hours = "hours";
        public static final String COLUMN_fridgeusage = "fridgeusage";
        public static final String COLUMN_aircondusate = "aircondusate";
        public static final String COLUMN_washmachusage = "washmachusage";
        public static final String COLUMN_temperature = "temperature";
    }



}
