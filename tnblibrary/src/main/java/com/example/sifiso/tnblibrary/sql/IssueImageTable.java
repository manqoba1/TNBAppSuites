package com.example.sifiso.tnblibrary.sql;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesImagesEntry;
import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesReportedEntry;

/**
 * Created by sifiso on 2015-05-16.
 */
public class IssueImageTable {
    static String TAG = IssueImageTable.class.getSimpleName();
    private static final String DATABASE_CREATE = "create table " + IssuesImagesEntry.TABLE_NAME + " (" +
            TNBContract._ID + " integer primary key autoincrement, " +
            IssuesImagesEntry._ID + " integer, " +
            IssuesImagesEntry.COLUMN_DATE_TAKEN + " TEXT NOT NULL, " +
            IssuesImagesEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
            IssuesImagesEntry.COLUMN_REPORTED_ISSUE_ID + " integer, " +
            "FOREIGN KEY (" + IssuesImagesEntry.COLUMN_REPORTED_ISSUE_ID + ") REFERENCES " +
            IssuesReportedEntry.TABLE_NAME + " (" + IssuesReportedEntry._ID + "));";

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
        database.execSQL("DROP TABLE IF EXISTS " + IssuesImagesEntry.TABLE_NAME);
        // database.execSQL(DDL_CREATE_TRIGGER_DEL_ITEMS);
        onCreate(database);
    }
}
