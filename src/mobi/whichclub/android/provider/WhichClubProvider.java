/**
 * 
 */

package mobi.whichclub.android.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import mobi.whichclub.android.data.Course;

/**
 * Provides access to a database of notes. Each note has a title, the note
 * itself, a creation date and a modified data.
 */
public class WhichClubProvider extends ContentProvider {

    private static final String TAG = "WhichClubProvider";

    private static HashMap<String, String> sCourseProjectionMap;

    private static final String AUTHORITY = "mobi.whichclub.android.data";
    private static final int COURSES = 1;
    private static final int COURSE_ID = 2;

    private static final UriMatcher sUriMatcher;

    private DbHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Course.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        case COURSES:
            qb.setProjectionMap(sCourseProjectionMap);
            break;

        case COURSE_ID:
            qb.setProjectionMap(sCourseProjectionMap);
            qb.appendWhere(Course._ID + "=" + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Course.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case COURSES:
            return Course.CONTENT_TYPE;

        case COURSE_ID:
            return Course.CONTENT_ITEM_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != COURSES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

//        Long now = Long.valueOf(System.currentTimeMillis());

        // Make sure that the fields are all set
//        if (values.containsKey(NotePad.Notes.CREATED_DATE) == false) {
//            values.put(NotePad.Notes.CREATED_DATE, now);
//        }
//
//        if (values.containsKey(NotePad.Notes.MODIFIED_DATE) == false) {
//            values.put(NotePad.Notes.MODIFIED_DATE, now);
//        }
//
//        if (values.containsKey(NotePad.Notes.TITLE) == false) {
//            Resources r = Resources.getSystem();
//            values.put(NotePad.Notes.TITLE, r.getString(android.R.string.untitled));
//        }
//
//        if (values.containsKey(NotePad.Notes.NOTE) == false) {
//            values.put(NotePad.Notes.NOTE, "");
//        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Course.TABLE_NAME, Course.NAME, values);
        if (rowId > 0) {
            Uri courseUri = ContentUris.withAppendedId(Course.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(courseUri, null);
            return courseUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case COURSES:
            count = db.delete(Course.TABLE_NAME, where, whereArgs);
            break;

        case COURSE_ID:
            String courseId = uri.getPathSegments().get(1);
            count = db.delete(Course.TABLE_NAME, Course._ID + "=" + courseId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case COURSES:
            count = db.update(Course.TABLE_NAME, values, where, whereArgs);
            break;

        case COURSE_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(Course.TABLE_NAME, values, Course._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, Course.TABLE_NAME, COURSES);
        sUriMatcher.addURI(AUTHORITY, Course.TABLE_NAME + "/#", COURSE_ID);

        sCourseProjectionMap = new HashMap<String, String>();
        sCourseProjectionMap.put(Course._ID, Course._ID);
        sCourseProjectionMap.put(Course.CITY, Course.CITY);
        sCourseProjectionMap.put(Course.COUNTRY, Course.COUNTRY);
        sCourseProjectionMap.put(Course.LATITUDE, Course.LATITUDE);
        sCourseProjectionMap.put(Course.LONGITUDE, Course.LONGITUDE);
        sCourseProjectionMap.put(Course.NAME, Course.NAME);
        sCourseProjectionMap.put(Course.PAR, Course.PAR);
        sCourseProjectionMap.put(Course.STATE_PROV, Course.STATE_PROV);
    }
}
