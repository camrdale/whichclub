package mobi.whichclub.android.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import mobi.whichclub.android.data.Course;

/**
 * Provides access to the WhichClub database of shots.
 * @author camrdale
 */
public class WhichClubProvider extends ContentProvider {

	/** Logging tag. */
    private static final String TAG = "WhichClubProvider";
    /** The authority for the provider's URIs. */
    public static final String AUTHORITY = "mobi.whichclub.android.data";
    
    /** URI matching results. */
    private static final int COURSES = 1;
    /** URI matching results. */
    private static final int COURSE_ID = 2;
    /** URI matcher. */
    private static final UriMatcher URI_MATCHER;
    /** The database helper to use to open it. */
    private SQLiteOpenHelper mOpenHelper;

    @Override
    public final boolean onCreate() {
        mOpenHelper = new DbHelper(getContext());
        return true;
    }
    
    /**
     * If no sort order is specified use the default.
     * @param sortOrder the user supplied sort order
     * @param defaultSortOrder the default sort order
     * @return the sort order to use
     */
    private String getOrderBy(final String sortOrder, final String defaultSortOrder) {
        if (TextUtils.isEmpty(sortOrder)) {
            return Course.DEFAULT_SORT_ORDER;
        } else {
            return sortOrder;
        }
    }
    
    @Override
    public final Cursor query(final Uri uri, final String[] projection,
    		final String selection, final String[] selectionArgs,
            final String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Course.TABLE_NAME);
        String orderBy;

        switch (URI_MATCHER.match(uri)) {
        case COURSE_ID:
            qb.appendWhere(Course._ID + "=" + uri.getPathSegments().get(1));
        case COURSES:
            qb.setProjectionMap(Course.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Course.DEFAULT_SORT_ORDER);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs,
        		null, null, orderBy);

        // Tell the cursor what URI to watch for source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public final String getType(final Uri uri) {
        switch (URI_MATCHER.match(uri)) {
        case COURSES:
            return Course.CONTENT_MULTI_TYPE;

        case COURSE_ID:
            return Course.CONTENT_ITEM_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public final Uri insert(final Uri uri, final ContentValues initialValues) {
    	Log.d(TAG, "Inserting " + uri + ": " + initialValues.toString());

        // Validate the requested uri
        if (URI_MATCHER.match(uri) != COURSES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        Log.d(TAG, "Inserting URI: " + uri);

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
//            values.put(NotePad.Notes.TITLE,
//        			r.getString(android.R.string.untitled));
//        }
//
//        if (values.containsKey(NotePad.Notes.NOTE) == false) {
//            values.put(NotePad.Notes.NOTE, "");
//        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Course.TABLE_NAME, Course.NAME, values);
        if (rowId > 0) {
            Uri courseUri =
            	ContentUris.withAppendedId(Course.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(courseUri, null);
            return courseUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    /**
     * Build the where clause, including selection by record ID.
     * @param id the ID of the record to select
     * @param where the user supplied where clause
     * @return the new where clause, including the record ID
     */
    private String buildWhereWithId(final String id, final String where) {
    	// All _ID's come from BaseColumns, so use Course for any table
        String whereWithId = Course._ID + "=" + id;
        if (!TextUtils.isEmpty(where)) {
        	whereWithId += " AND (" + where + ')';
        }
        return whereWithId;
    }
    
    @Override
    public final int delete(final Uri uri,
    		final String where, final String[] whereArgs) {
    	Log.d(TAG, "Deleting " + uri + " where "
    			+ where + "[" + whereArgs + "]");
    	
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (URI_MATCHER.match(uri)) {
        case COURSES:
        	if (TextUtils.isEmpty(where)) {
        		count = db.delete(Course.TABLE_NAME, "1", whereArgs);
        	} else {
        		count = db.delete(Course.TABLE_NAME, where, whereArgs);
        	}
            break;

        case COURSE_ID:
            String courseId = uri.getPathSegments().get(1);
            count = db.delete(Course.TABLE_NAME, buildWhereWithId(courseId, where), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public final int update(final Uri uri, final ContentValues values,
    		final String where, final String[] whereArgs) {
    	Log.d(TAG, "Updating " + uri + " where "
    			+ where + "[" + whereArgs + "]");

    	SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (URI_MATCHER.match(uri)) {
        case COURSES:
            count = db.update(Course.TABLE_NAME, values, where, whereArgs);
            break;

        case COURSE_ID:
            String courseId = uri.getPathSegments().get(1);
            count = db.update(Course.TABLE_NAME, values,
            		buildWhereWithId(courseId, where), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, Course.TABLE_NAME, COURSES);
        URI_MATCHER.addURI(AUTHORITY, Course.TABLE_NAME + "/#", COURSE_ID);
    }
}
