package com.example.yjw.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jyan289 on 16/4/18.
 */

public class DBManager {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usages.db";
    private final Context context;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBStructure.tableEntry.TABLE_NAME + " (" +
                    DBStructure.tableEntry.COLUMN_RESID + TEXT_TYPE + COMMA_SEP +
                    DBStructure.tableEntry.COLUMN_dates + TEXT_TYPE + COMMA_SEP +
                    DBStructure.tableEntry.COLUMN_hours + TEXT_TYPE + COMMA_SEP +
                    DBStructure.tableEntry.COLUMN_fridgeusage + TEXT_TYPE + COMMA_SEP +
                    DBStructure.tableEntry.COLUMN_aircondusate + TEXT_TYPE + COMMA_SEP +
                    DBStructure.tableEntry.COLUMN_washmachusage + TEXT_TYPE + COMMA_SEP +
                    DBStructure.tableEntry.COLUMN_temperature + TEXT_TYPE +
                    " );";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBStructure.tableEntry.TABLE_NAME;

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// This database is only a cache for online data, so its upgrade policy is // to simply to discard the data and start over db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    private MySQLiteOpenHelper myDBHelper;
    private static SQLiteDatabase db;
    private String[] columns = {
            DBStructure.tableEntry.COLUMN_RESID,
            DBStructure.tableEntry.COLUMN_dates,
            DBStructure.tableEntry.COLUMN_hours,
            DBStructure.tableEntry.COLUMN_fridgeusage,
            DBStructure.tableEntry.COLUMN_aircondusate,
            DBStructure.tableEntry.COLUMN_washmachusage,
            DBStructure.tableEntry.COLUMN_temperature };

    public DBManager(Context ctx) {
        this.context = ctx;
        myDBHelper = new MySQLiteOpenHelper(context);
    }

    public DBManager open() throws SQLException {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        myDBHelper.close();
    }

    public static long insertUsage(String resident, String dates, String hours, String fridgeUsage, String ariconnUsage, String washmachUsage, String temp) {
        ContentValues values = new ContentValues();
        values.put(DBStructure.tableEntry.COLUMN_RESID, resident);
        values.put(DBStructure.tableEntry.COLUMN_dates, dates);
        values.put(DBStructure.tableEntry.COLUMN_hours, hours);
        values.put(DBStructure.tableEntry.COLUMN_fridgeusage, fridgeUsage);
        values.put(DBStructure.tableEntry.COLUMN_aircondusate, ariconnUsage);
        values.put(DBStructure.tableEntry.COLUMN_washmachusage, washmachUsage);
        values.put(DBStructure.tableEntry.COLUMN_temperature, temp);
        return db.insert(DBStructure.tableEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllUsages() {
        return db.query(DBStructure.tableEntry.TABLE_NAME, columns, null, null, null, null, null);
    }

    public int deleteUsage(String resid) {
        String[] selectionArgs = { String.valueOf(resid) };
        String selection = DBStructure.tableEntry.COLUMN_RESID + " LIKE ?";
        return db.delete(DBStructure.tableEntry.TABLE_NAME, selection,selectionArgs );
    }


    public Cursor getUser(String hour) throws SQLException{
        String[] selectionArgs = {String.valueOf(hour)};
        String selection = DBStructure.tableEntry.COLUMN_hours + " = ?";
        Cursor cursor = db.query(DBStructure.tableEntry.TABLE_NAME, columns,selection,selectionArgs,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
