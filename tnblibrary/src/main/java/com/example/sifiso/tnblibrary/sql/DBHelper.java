package com.example.sifiso.tnblibrary.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, TNBContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    public void onCreate(SQLiteDatabase database) {
        IssuesReportedTable.onCreate(database);
        StatusReportedIssueTable.onCreate(database);
        IssueImageTable.onCreate(database);
        IssuesTable.onCreate(database);
        Log.i("DBHelper", "--> DupMed tables onCreate complete");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        IssuesReportedTable.onUpgrade(database, oldVersion, newVersion);
        StatusReportedIssueTable.onUpgrade(database, oldVersion, newVersion);
        IssueImageTable.onUpgrade(database, oldVersion, newVersion);
        IssuesTable.onUpgrade(database, oldVersion, newVersion);
    }


}
