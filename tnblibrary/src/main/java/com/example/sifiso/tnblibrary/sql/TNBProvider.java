package com.example.sifiso.tnblibrary.sql;

import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesReportedEntry;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;

import com.example.sifiso.tnblibrary.sql.TNBContract.StatusReportedIssueEntry;

import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesImagesEntry;

import com.example.sifiso.tnblibrary.sql.TNBContract.IssuesEntry;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


/**
 * Created by sifiso on 3/28/2015.
 */
public class TNBProvider extends ContentProvider {
    private static SQLiteQueryBuilder liteQueryBuilder;
    private static final int ISSUES = 301;
    private static final int REPORTED_ISSUES = 302;
    private static final int STATUS_REPORTED_ISSUES = 303;
    private static final int ISSUES_IMAGE = 304;
    private static final int IMAGE_ISSUE_BY_REPORTED_ISSUES_ID = 305;
    private static final int STATUS_BY_REPORTED_ISSUES_ID = 306;
    private SQLiteOpenHelper mOpenHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();//assign

    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        liteQueryBuilder = new SQLiteQueryBuilder();

        int type = uriMatcher.match(uri);
        switch (type) {

            case ISSUES:
                // looking up all categories
                liteQueryBuilder.setTables(IssuesEntry.TABLE_NAME);
                break;
            case REPORTED_ISSUES:
                // looking up all articles by category id
                liteQueryBuilder.setTables(IssuesReportedEntry.TABLE_NAME);
                break;
            case IMAGE_ISSUE_BY_REPORTED_ISSUES_ID:
                // looking up all articles by category id
                liteQueryBuilder.setTables(IssuesImagesEntry.TABLE_NAME);
                liteQueryBuilder.appendWhere(IssuesImagesEntry.COLUMN_REPORTED_ISSUE_ID + " = " + uri.getLastPathSegment());
                break;
            case STATUS_BY_REPORTED_ISSUES_ID:
                // looking up all articles by category id
                liteQueryBuilder.setTables(StatusReportedIssueEntry.TABLE_NAME);
                liteQueryBuilder.appendWhere(StatusReportedIssueEntry.COLUMN_REPORTED_ISSUE_ID + " = " + uri.getLastPathSegment());
                break;

        }
        if (mOpenHelper == null) {
            return null;
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = liteQueryBuilder.query(db, projection, null, null, null, null, null);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int type = uriMatcher.match(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        Uri returnUri;
        switch (type) {
            case ISSUES:
                long id = db.insert(IssuesEntry.TABLE_NAME, null, values);
                returnUri = IssuesEntry.buildIssuesUriId(id);
                break;
            case REPORTED_ISSUES:
                long ids = db.insert(IssuesReportedEntry.TABLE_NAME, null, values);
                returnUri = IssuesReportedEntry.buildIssuesReportedUriId(ids);
                break;
            case STATUS_REPORTED_ISSUES:
                long ids1 = db.insert(StatusReportedIssueEntry.TABLE_NAME, null, values);
                returnUri = StatusReportedIssueEntry.buildStatusReportedIssueUriId(ids1);
                break;
            case ISSUES_IMAGE:
                long ids2 = db.insert(IssuesEntry.TABLE_NAME, null, values);
                returnUri = IssuesEntry.buildIssuesUriId(ids2);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(returnUri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    // star(*) for anything string or date
    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TNBContract.CONTENT_AUTHORITY;

        //uri to get all categories
        matcher.addURI(authority, TNBContract.PATH_ISSUES, ISSUES);

        //uri to get all articles
        matcher.addURI(authority, TNBContract.PATH_ISSUES_IMAGES, ISSUES_IMAGE);

        //uri to get all categories
        matcher.addURI(authority, TNBContract.PATH_ISSUES_REPORTED, REPORTED_ISSUES);

        //uri to get all articles
        matcher.addURI(authority, TNBContract.PATH_STATUS_REPORTED_ISSUES, STATUS_REPORTED_ISSUES);

        //get articles by category id article/category/21
        matcher.addURI(authority, TNBContract.PATH_STATUS_REPORTED_ISSUES + "/" + TNBContract.PATH_ISSUES_REPORTED + "/#", STATUS_BY_REPORTED_ISSUES_ID);
        matcher.addURI(authority, TNBContract.PATH_ISSUES_IMAGES + "/" + TNBContract.PATH_ISSUES_REPORTED + "/#", IMAGE_ISSUE_BY_REPORTED_ISSUES_ID);
        return matcher;
    }


}
