package mobi.whichclub.android.provider;

import mobi.whichclub.android.data.Course;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

/**
 * @author camrdale
 *
 */
public final class WhichClubProviderTest
			extends ProviderTestCase2<WhichClubProvider> {
    
	/** Logging tag. */
	private static final String TAG = "WhichClubProviderTest";
    
    /**
     * Initialize the ProviderTestCase for the WhichClubProvider.
     */
    public WhichClubProviderTest() {
		super(WhichClubProvider.class, WhichClubProvider.AUTHORITY);
	}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Find the ID for the Default course.
     * @return the ID
     */
    private long getDefaultCourseId() {
    	Cursor cursor = getMockContentResolver().query(
    			Course.CONTENT_URI, Course.PROJECTION,
    			Course.NAME + "=?", new String[] {"Default"},
    			Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
    	long courseId = -1;
        while (!cursor.isAfterLast()) {
            assertEquals("Default", cursor.getString(1));
            courseId = cursor.getLong(0);
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(1, cursor.getCount());
        cursor.close();
        assertTrue(courseId != -1);
        return courseId;
    }

    /**
     * Test querying for a single course.
     */
    @SmallTest
    public void testQuerySingleCourse() {
    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals("Default", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for all of multiple courses.
     */
    @SmallTest
    public void testQueryMultipleCourses() {
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }
    
    /**
     * Test querying for a single of many courses.
     */
    @SmallTest
    public void testQueryOneOfManyCourses() {
    	long courseId = getDefaultCourseId();

    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);

        Uri result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        Cursor cursor = getMockContentResolver().query(result,
        		Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals("Default", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for a single course using a WHERE clause.
     */
    @SmallTest
    public void testQueryWithWhere() {
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);

    	long courseId = getDefaultCourseId();

        Uri result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        Cursor cursor = getMockContentResolver().query(result,
        		Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals("Default", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test inserting a new course.
     */
    @SmallTest
    public void testInsertingCourse() {
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	Uri result =
    		getMockContentResolver().insert(Course.CONTENT_URI, values);
    	
    	Cursor cursor = getMockContentResolver().query(result,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals("NewCourse", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating a single course.
     */
    @SmallTest
    public void testUpdatingSingleCourse() {
    	long courseId = getDefaultCourseId();
        
        Uri result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        ContentValues values = new ContentValues();
        values.put(Course.PAR, 32);
        int updated =
        	getMockContentResolver().update(result, values, null, null);
        assertEquals(1, updated);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals("Default", cursor.getString(1));
            assertEquals(32, cursor.getInt(2));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating multiple courses.
     */
    @SmallTest
    public void testUpdatingMultipleCourses() {
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
        
        values = new ContentValues();
        values.put(Course.PAR, 32);
        int updated = getMockContentResolver().update(Course.CONTENT_URI,
        		values, null, null);
        assertEquals(3, updated);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals(32, cursor.getInt(2));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating one of multiple courses.
     */
    @SmallTest
    public void testUpdatingOneOfManyCourses() {
    	long courseId = getDefaultCourseId();
        
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);

    	Uri result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        values = new ContentValues();
        values.put(Course.PAR, 32);
        int updated =
        	getMockContentResolver().update(result, values, null, null);
        assertEquals(1, updated);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            if ("Default".equals(cursor.getString(1))) {
            	assertEquals(32, cursor.getInt(2));
            } else {
            	assertTrue(cursor.isNull(2));
            }
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting a single course.
     */
    @SmallTest
    public void testDeletingSingleCourse() {
    	long courseId = getDefaultCourseId();
        
        Uri result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        int deleted = getMockContentResolver().delete(result, null, null);
        assertEquals(1, deleted);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	fail("There should be 0 courses after deleting the default one");
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting multiple courses.
     */
    @SmallTest
    public void testDeletingMultipleCourses() {
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);

    	int deleted =
    		getMockContentResolver().delete(Course.CONTENT_URI, null, null);
    	Log.d(TAG, "Deleted " + deleted + " courses");
        assertEquals(3, deleted);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	fail("There should be 0 courses after deleting the default one");
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting one of multiple courses.
     */
    @SmallTest
    public void testDeletingOneOfManyCourses() {
    	long courseId = getDefaultCourseId();
        
    	ContentValues values = new ContentValues();
    	values.put("name", "NewCourse");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);
    	values.put("name", "NewCourse2");
    	getMockContentResolver().insert(Course.CONTENT_URI, values);

        Uri result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        int deleted = getMockContentResolver().delete(result, null, null);
        assertEquals(1, deleted);

    	Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
    			Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
    	assertNotNull(cursor);
    	cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
        	assertFalse("Default".equals(cursor.getString(1)));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " courses");
        assertEquals(2, cursor.getCount());
        cursor.close();
    }

}
