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
import mobi.whichclub.android.data.Hole;
import mobi.whichclub.android.data.Player;
import mobi.whichclub.android.data.Round;

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
    /** URI matching results. */
    private static final int PLAYERS = 3;
    /** URI matching results. */
    private static final int PLAYER_ID = 4;
    /** URI matching results. */
    private static final int ROUNDS = 5;
    /** URI matching results. */
    private static final int ROUND_ID = 6;
    /** URI matching results. */
    private static final int ROUNDS_FOR_COURSE_ID = 7;
    /** URI matching results. */
    private static final int ROUNDS_FOR_PLAYER_ID = 8;
    /** URI matching results. */
    private static final int HOLES_FOR_COURSE_ID = 9;
    /** URI matching results. */
    private static final int HOLES_FOR_ROUND_ID = 10;
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
            return defaultSortOrder;
        } else {
            return sortOrder;
        }
    }
    
    @Override
    public final Cursor query(final Uri uri, final String[] projection,
    		final String selection, final String[] selectionArgs,
            final String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy;

        switch (URI_MATCHER.match(uri)) {
        case COURSE_ID:
            qb.appendWhere(Course._ID + "=" + uri.getPathSegments().get(1));
        case COURSES:
            qb.setTables(Course.TABLE_NAME);
            qb.setProjectionMap(Course.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Course.DEFAULT_SORT_ORDER);
            break;

        case PLAYER_ID:
            qb.appendWhere(Player._ID + "=" + uri.getPathSegments().get(1));
        case PLAYERS:
            qb.setTables(Player.TABLE_NAME);
            qb.setProjectionMap(Player.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Player.DEFAULT_SORT_ORDER);
            break;

        case ROUND_ID:
            qb.appendWhere(Round._ID + "=" + uri.getPathSegments().get(1));
        case ROUNDS:
            qb.setTables(Round.TABLE_NAME);
            qb.setProjectionMap(Round.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Round.DEFAULT_SORT_ORDER);
            break;

        case ROUNDS_FOR_COURSE_ID:
            qb.appendWhere(Round.COURSE + "=" + uri.getPathSegments().get(1));
            qb.setTables(Round.TABLE_NAME);
            qb.setProjectionMap(Round.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Round.DEFAULT_SORT_ORDER);
            break;

        case ROUNDS_FOR_PLAYER_ID:
            qb.appendWhere(Round.PLAYER + "=" + uri.getPathSegments().get(1));
            qb.setTables(Round.TABLE_NAME);
            qb.setProjectionMap(Round.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Round.DEFAULT_SORT_ORDER);
            break;

        case HOLES_FOR_COURSE_ID:
            qb.appendWhere(Hole.COURSE + "=" + uri.getPathSegments().get(1));
            qb.setTables(Hole.TABLE_NAME);
            qb.setProjectionMap(Hole.PROJECTION_MAP);
            orderBy = getOrderBy(sortOrder, Hole.DEFAULT_SORT_ORDER);
            break;

        case HOLES_FOR_ROUND_ID:
            qb.setProjectionMap(Hole.PROJECTION_MAP);
            qb.setTables(Hole.TABLE_NAME + " JOIN " + Round.TABLE_NAME + " ON("
                    + Hole.TABLE_NAME + "." + Hole.COURSE + "=" + Round.TABLE_NAME + "." + Round.COURSE + ")");
            qb.appendWhere(Round.TABLE_NAME + "." + Round._ID + "=" + uri.getPathSegments().get(1));
            orderBy = getOrderBy(sortOrder, Hole.TABLE_NAME + "." + Hole.NUMBER + " ASC");
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

        case PLAYERS:
            return Player.CONTENT_MULTI_TYPE;

        case PLAYER_ID:
            return Player.CONTENT_ITEM_TYPE;

        case ROUNDS:
        case ROUNDS_FOR_COURSE_ID:
        case ROUNDS_FOR_PLAYER_ID:
            return Round.CONTENT_MULTI_TYPE;

        case ROUND_ID:
            return Round.CONTENT_ITEM_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public final Uri insert(final Uri uri, final ContentValues initialValues) {
    	Log.d(TAG, "Inserting " + uri + ": " + initialValues.toString());
    	
    	String table;
    	String nullColumnHack;
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        // Validate the requested uri
        switch (URI_MATCHER.match(uri)) {
        case COURSES:
        	table = Course.TABLE_NAME;
        	nullColumnHack = Course.NAME;
            break;

        case PLAYERS:
        	table = Player.TABLE_NAME;
        	nullColumnHack = Player.NAME;
            break;

        case ROUNDS:
        	table = Round.TABLE_NAME;
        	nullColumnHack = Round.SCORE;
            if (!values.containsKey(Round.DATE)) {
            	values.put(Round.DATE, System.currentTimeMillis());
            }
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(table, nullColumnHack, values);
        if (rowId >= 0) {
            Uri courseUri =
            	ContentUris.withAppendedId(uri, rowId);
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
    private String whereWithId(final String id, final String where) {
    	if (id == null) {
    		return where;
    	}

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
    	
    	String table;
    	String recordId = null;
    	
        switch (URI_MATCHER.match(uri)) {
        case COURSE_ID:
        	recordId = uri.getPathSegments().get(1);
        case COURSES:
        	table = Course.TABLE_NAME;
            break;

        case PLAYER_ID:
        	recordId = uri.getPathSegments().get(1);
        case PLAYERS:
        	table = Player.TABLE_NAME;
            break;

        case ROUND_ID:
        	recordId = uri.getPathSegments().get(1);
        case ROUNDS:
        	table = Round.TABLE_NAME;
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Make sure the WHERE clause is non-empty, or the record count won't work
        String nonEmptyWhere = where;
    	if (TextUtils.isEmpty(nonEmptyWhere)) {
    		nonEmptyWhere = "1";
    	}

    	SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    	int count = db.delete(table, whereWithId(recordId, nonEmptyWhere), whereArgs);
        
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public final int update(final Uri uri, final ContentValues values,
    		final String where, final String[] whereArgs) {
    	Log.d(TAG, "Updating " + uri + " where "
    			+ where + "[" + whereArgs + "]");

    	String table;
    	String recordId = null;
    	
        switch (URI_MATCHER.match(uri)) {
        case COURSE_ID:
            recordId = uri.getPathSegments().get(1);
        case COURSES:
        	table = Course.TABLE_NAME;
            break;

        case PLAYER_ID:
            recordId = uri.getPathSegments().get(1);
        case PLAYERS:
        	table = Player.TABLE_NAME;
            break;

        case ROUND_ID:
            recordId = uri.getPathSegments().get(1);
        case ROUNDS:
        	table = Round.TABLE_NAME;
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

    	SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.update(table, values,
        		whereWithId(recordId, where), whereArgs);
        
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, Course.TABLE_NAME, COURSES);
        URI_MATCHER.addURI(AUTHORITY, Course.TABLE_NAME + "/#", COURSE_ID);
        URI_MATCHER.addURI(AUTHORITY, Player.TABLE_NAME, PLAYERS);
        URI_MATCHER.addURI(AUTHORITY, Player.TABLE_NAME + "/#", PLAYER_ID);
        URI_MATCHER.addURI(AUTHORITY, Round.TABLE_NAME, ROUNDS);
        URI_MATCHER.addURI(AUTHORITY, Round.TABLE_NAME + "/#", ROUND_ID);
        URI_MATCHER.addURI(AUTHORITY, Course.TABLE_NAME + "/#/" + Round.TABLE_NAME, ROUNDS_FOR_COURSE_ID);
        URI_MATCHER.addURI(AUTHORITY, Player.TABLE_NAME + "/#/" + Round.TABLE_NAME, ROUNDS_FOR_PLAYER_ID);
        URI_MATCHER.addURI(AUTHORITY, Course.TABLE_NAME + "/#/" + Hole.TABLE_NAME, HOLES_FOR_COURSE_ID);
        URI_MATCHER.addURI(AUTHORITY, Round.TABLE_NAME + "/#/" + Hole.TABLE_NAME, HOLES_FOR_ROUND_ID);
    }
}
