package com.example.sifiso.tnblibrary.sql;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sifiso on 3/28/2015.
 */
public final class TNBContract {
    public static final String CONTENT_AUTHORITY = "com.example.sifiso.tnblibrary";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String DATABASE_NAME = "noticeboard.db";
    public static final String PATH_ISSUES_REPORTED = "issuesReported";
    public static final String PATH_ISSUES = "issues";
    public static final String PATH_MEETING = "meeting";
    public static final String PATH_ISSUES_IMAGES = "issuesImages";
    public static final String PATH_STATUS_REPORTED_ISSUES = "statusReportedIssue";
    public static final String _ID = "_id";

    public TNBContract() {
    }

    public static class StatusReportedIssueEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATUS_REPORTED_ISSUES).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_STATUS_REPORTED_ISSUES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_STATUS_REPORTED_ISSUES;

        public static final String TABLE_NAME = "statusReportedIssue";
        public static final String _ID = "statusReportedIssueID";
        public static final String COLUMN_STATUS_REPORTED_DATE = "statusReportedDate";
        public static final String COLUMN_STATUS_NAME = "statusName";
        public static final String COLUMN_REPORTED_ISSUE_ID = "reportedIssueID";

        public static Uri buildStatusReportedIssueUriId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static class IssuesImagesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ISSUES_IMAGES).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ISSUES_IMAGES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ISSUES_IMAGES;

        public static final String TABLE_NAME = "issuesImages";
        public static final String _ID = "issueImageID";
        public static final String COLUMN_DATE_TAKEN = "dateTaken";
        public static final String COLUMN_IMAGE_URL = "imageUrl";
        public static final String COLUMN_REPORTED_ISSUE_ID = "reportedIssueID";

        public static Uri buildIssuesImagesUriId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static class MeetingEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEETING).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MEETING;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MEETING;

        public static final String TABLE_NAME = "meeting";
        public static final String _ID = "meetingID";
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_UPLOAD_FLYER_URL = "uploadflyerUrl";
        public static final String COLUMN_MEETING_DATE = "meetingDate";
        public static final String COLUMN_CLERK_ID = "clerkID";
        public static final String COLUMN_MUNICIPALITY_ID = "municipalityID";
        public static final String COLUMN_ISSUES_ID = "issuesID";
        public static final String COLUMN_WARD_ID = "wardsID";

        public static Uri buildMeetingUriId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static class IssuesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ISSUES).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ISSUES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ISSUES;

        public static final String TABLE_NAME = "issues";
        public static final String _ID = "issuesID";
        public static final String ROW_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ICON = "icon";

        public static Uri buildIssuesUriId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class IssuesReportedEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ISSUES_REPORTED).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ISSUES_REPORTED;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ISSUES_REPORTED;

        public static final String TABLE_NAME = "issuesReported";
        public static final String _ID = "reportedIssueID";
        public static final String COLUMN_DATE_REPORTED = "dateReported";
        public static final String COLUMN_MEMBER_ID = "memberID";
        public static final String COLUMN_REVIEWS = "reviews";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_MUNICIPALITY_ID = "municipalityID";
        public static final String COLUMN_ISSUES_ID = "issuesID";
        public static final String COLUMN_DATE_CREATED = "dateCreated";
        public static final String COLUMN_REF_NUMBER = "refNumber";
        public static final String COLUMN_ISSUE_NAME = "issueName";
        public static final String COLUMN_ICON = "icon";


        public static Uri buildIssuesReportedUriId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
