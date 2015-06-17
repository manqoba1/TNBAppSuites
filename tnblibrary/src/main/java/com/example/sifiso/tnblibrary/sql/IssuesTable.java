package com.example.sifiso.tnblibrary.sql;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesEntry;

/**
 * Created by sifiso on 2015-05-16.
 */
public class IssuesTable {
    static String TAG = IssuesTable.class.getSimpleName();
    private static final String DATABASE_CREATE = "create table " + IssuesEntry.TABLE_NAME + " (" +
            TNBContract._ID + " integer primary key autoincrement, " +
            IssuesEntry._ID + " integer, " +
            IssuesEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            IssuesEntry.COLUMN_ICON + " TEXT NOT NULL);";

    public static void onCreate(SQLiteDatabase database) {
        Log.i(TAG, "############# --> onCreate IssueImageTable\n");
        database.execSQL(DATABASE_CREATE);
        // database.execSQL(DDL_CREATE_TRIGGER_DEL_ITEMS);
        Log.d(TAG, "--> Done creating indexes on IssueImageTable");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(TAG, "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + IssuesEntry.TABLE_NAME);
        // database.execSQL(DDL_CREATE_TRIGGER_DEL_ITEMS);
        onCreate(database);
    }
}
