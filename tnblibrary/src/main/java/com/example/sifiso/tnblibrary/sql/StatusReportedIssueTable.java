package com.example.sifiso.tnblibrary.sql;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sifiso.tnblibrary.sql.TNBContract.StatusReportedIssueEntry;
import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesReportedEntry;

/**
 * Created by sifiso on 2015-05-16.
 */
public class StatusReportedIssueTable {
    static String TAG = StatusReportedIssueTable.class.getSimpleName();
    private static final String DATABASE_CREATE = "create table " + StatusReportedIssueEntry.TABLE_NAME + " (" +
            TNBContract._ID + " integer primary key autoincrement, " +
            StatusReportedIssueEntry._ID + " integer, " +
            StatusReportedIssueEntry.COLUMN_STATUS_REPORTED_DATE + " TEXT NOT NULL, " +
            StatusReportedIssueEntry.COLUMN_STATUS_NAME + " TEXT NOT NULL, " +
            StatusReportedIssueEntry.COLUMN_REPORTED_ISSUE_ID + " integer, " +
            "FOREIGN KEY (" + StatusReportedIssueEntry.COLUMN_REPORTED_ISSUE_ID + ") REFERENCES " +
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
        database.execSQL("DROP TABLE IF EXISTS " + StatusReportedIssueEntry.TABLE_NAME);
        // database.execSQL(DDL_CREATE_TRIGGER_DEL_ITEMS);
        onCreate(database);
    }
}
