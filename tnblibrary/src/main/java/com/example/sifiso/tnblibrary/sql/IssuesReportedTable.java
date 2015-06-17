package com.example.sifiso.tnblibrary.sql;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesReportedEntry;

/**
 * Created by sifiso on 2015-05-16.
 */
public class IssuesReportedTable {
    static String TAG = IssuesReportedTable.class.getSimpleName();
    private static final String DATABASE_CREATE = "create table " + IssuesReportedEntry.TABLE_NAME + " (" +
            TNBContract._ID + " integer primary key autoincrement, " +
            IssuesReportedEntry._ID + " integer, " +
            IssuesReportedEntry.COLUMN_DATE_REPORTED + " TEXT NOT NULL, " +
            IssuesReportedEntry.COLUMN_MEMBER_ID + " TEXT NOT NULL, " +
            IssuesReportedEntry.COLUMN_REVIEWS + " TEXT NOT NULL unique, " +
            IssuesReportedEntry.COLUMN_LATITUDE + " double, " +
            IssuesReportedEntry.COLUMN_LONGITUDE + " double, " +
            IssuesReportedEntry.COLUMN_MUNICIPALITY_ID + " integer, " +
            IssuesReportedEntry.COLUMN_ISSUES_ID + " integer, " +
            IssuesReportedEntry.COLUMN_DATE_CREATED + " TEXT NOT NULL, " +
            IssuesReportedEntry.COLUMN_REF_NUMBER + " TEXT NOT NULL, " +
            IssuesReportedEntry.COLUMN_ISSUE_NAME + " TEXT NOT NULL, " +
            IssuesReportedEntry.COLUMN_ICON + " TEXT NOT NULL);";

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
        database.execSQL("DROP TABLE IF EXISTS " + IssuesReportedEntry.TABLE_NAME);
        // database.execSQL(DDL_CREATE_TRIGGER_DEL_ITEMS);
        onCreate(database);
    }

}
