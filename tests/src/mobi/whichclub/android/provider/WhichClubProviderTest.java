package mobi.whichclub.android.provider;

import mobi.whichclub.android.data.Course;
import mobi.whichclub.android.data.Hole;
import mobi.whichclub.android.data.Player;
import mobi.whichclub.android.data.Round;

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
     * Find the ID for the Default player.
     * @return the ID
     */
    private long getDefaultPlayerId() {
        Cursor cursor = getMockContentResolver().query(
                Player.CONTENT_URI, Player.PROJECTION,
                Player.NAME + "=?", new String[] {"Default"},
                Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        long playerId = -1;
        while (!cursor.isAfterLast()) {
            assertEquals("Default", cursor.getString(1));
            playerId = cursor.getLong(0);
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(1, cursor.getCount());
        cursor.close();
        assertTrue(playerId != -1);
        return playerId;
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
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
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
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
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
    public void testQueryCourseWithWhere() {
        ContentValues values = new ContentValues();
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
        getMockContentResolver().insert(Course.CONTENT_URI, values);

        long courseId = getDefaultCourseId();

        Cursor cursor = getMockContentResolver().query(Course.CONTENT_URI,
                Course.PROJECTION, Course.NAME + "=?", new String[] {"Default"},
                Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(1));
            assertEquals(courseId, cursor.getInt(0));
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
        values.put(Course.NAME, "NewCourse");
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
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
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
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
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
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
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
        values.put(Course.NAME, "NewCourse");
        getMockContentResolver().insert(Course.CONTENT_URI, values);
        values.put(Course.NAME, "NewCourse2");
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

    /**
     * Test querying for a single player.
     */
    @SmallTest
    public void testQuerySinglePlayer() {
        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertEquals("Default", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for all of multiple players.
     */
    @SmallTest
    public void testQueryMultiplePlayers() {
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }
    
    /**
     * Test querying for a single of many players.
     */
    @SmallTest
    public void testQueryOneOfManyPlayers() {
        long playerId = getDefaultPlayerId();

        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);

        Uri result = ContentUris.withAppendedId(Player.CONTENT_URI, playerId);
        Cursor cursor = getMockContentResolver().query(result,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertEquals("Default", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for a single player using a WHERE clause.
     */
    @SmallTest
    public void testQueryPlayerWithWhere() {
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);

        long playerId = getDefaultPlayerId();

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, Player.NAME + "=?", new String[] {"Default"},
                Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertEquals(playerId, cursor.getInt(0));
            assertEquals("Default", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test inserting a new player.
     */
    @SmallTest
    public void testInsertingPlayer() {
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        Uri result =
            getMockContentResolver().insert(Player.CONTENT_URI, values);
        
        Cursor cursor = getMockContentResolver().query(result,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertEquals("NewPlayer", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating a single player.
     */
    @SmallTest
    public void testUpdatingSinglePlayer() {
        long playerId = getDefaultPlayerId();
        
        Uri result = ContentUris.withAppendedId(Player.CONTENT_URI, playerId);
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "UpdatedName");
        int updated =
            getMockContentResolver().update(result, values, null, null);
        assertEquals(1, updated);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertEquals("UpdatedName", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating multiple players.
     */
    @SmallTest
    public void testUpdatingMultiplePlayers() {
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        
        values = new ContentValues();
        values.put(Player.NAME, "UpdatedName");
        int updated = getMockContentResolver().update(Player.CONTENT_URI,
                values, null, null);
        assertEquals(3, updated);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertEquals("UpdatedName", cursor.getString(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating one of multiple players.
     */
    @SmallTest
    public void testUpdatingOneOfManyPlayers() {
        long playerId = getDefaultPlayerId();
        
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);

        Uri result = ContentUris.withAppendedId(Player.CONTENT_URI, playerId);
        values = new ContentValues();
        values.put(Player.NAME, "UpdatedName");
        int updated =
            getMockContentResolver().update(result, values, null, null);
        assertEquals(1, updated);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            if (playerId == cursor.getLong(0)) {
                assertEquals("UpdatedName", cursor.getString(1));
            } else {
                assertFalse("Default".equals(cursor.getString(1)));
            }
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting a single player.
     */
    @SmallTest
    public void testDeletingSinglePlayer() {
        long playerId = getDefaultPlayerId();
        
        Uri result = ContentUris.withAppendedId(Player.CONTENT_URI, playerId);
        int deleted = getMockContentResolver().delete(result, null, null);
        assertEquals(1, deleted);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            fail("There should be 0 players after deleting the default one");
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting multiple players.
     */
    @SmallTest
    public void testDeletingMultiplePlayers() {
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);

        int deleted =
            getMockContentResolver().delete(Player.CONTENT_URI, null, null);
        Log.d(TAG, "Deleted " + deleted + " players");
        assertEquals(3, deleted);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            fail("There should be 0 players after deleting the default one");
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting one of multiple players.
     */
    @SmallTest
    public void testDeletingOneOfManyPlayers() {
        long playerId = getDefaultPlayerId();
        
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        getMockContentResolver().insert(Player.CONTENT_URI, values);
        values.put(Player.NAME, "NewPlayer2");
        getMockContentResolver().insert(Player.CONTENT_URI, values);

        Uri result = ContentUris.withAppendedId(Player.CONTENT_URI, playerId);
        int deleted = getMockContentResolver().delete(result, null, null);
        assertEquals(1, deleted);

        Cursor cursor = getMockContentResolver().query(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(1));
            assertFalse("Default".equals(cursor.getString(1)));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " players");
        assertEquals(2, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for a single round.
     */
    @SmallTest
    public void testQuerySingleRound() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        
        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(72, cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for all of multiple rounds.
     */
    @SmallTest
    public void testQueryMultipleRounds() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }
    
    /**
     * Test querying for a single of many rounds.
     */
    @SmallTest
    public void testQueryOneOfManyRounds() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        result = ContentUris.withAppendedId(Round.CONTENT_URI, roundId);
        Cursor cursor = getMockContentResolver().query(result,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(73, cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for a single round using a WHERE clause.
     */
    @SmallTest
    public void testQueryRoundWithWhere() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        Cursor cursor = getMockContentResolver().query(result,
                Round.PROJECTION, Round.SCORE + "=73", null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(roundId, cursor.getLong(0));
            assertEquals(73, cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test inserting a new round.
     */
    @SmallTest
    public void testInsertingRound() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        
        Cursor cursor = getMockContentResolver().query(result,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(72, cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating a single round.
     */
    @SmallTest
    public void testUpdatingSingleRound() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        
        result = ContentUris.withAppendedId(Round.CONTENT_URI, roundId);
        values = new ContentValues();
        values.put(Round.GIR, 12);
        int updated = getMockContentResolver().update(result, values, null, null);
        assertEquals(1, updated);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(72, cursor.getInt(3));
            assertEquals(12, cursor.getInt(4));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating multiple rounds.
     */
    @SmallTest
    public void testUpdatingMultipleRounds() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        
        values = new ContentValues();
        values.put(Round.GIR, 12);
        int updated = getMockContentResolver().update(Round.CONTENT_URI,
                values, null, null);
        assertEquals(3, updated);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(12, cursor.getInt(4));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    /**
     * Test updating one of multiple rounds.
     */
    @SmallTest
    public void testUpdatingOneOfManyRounds() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        result = ContentUris.withAppendedId(Round.CONTENT_URI, roundId);
        values = new ContentValues();
        values.put(Round.GIR, 12);
        int updated =
            getMockContentResolver().update(result, values, null, null);
        assertEquals(1, updated);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            if (roundId == cursor.getLong(0)) {
                assertEquals(12, cursor.getInt(4));
            } else {
                assertTrue(cursor.isNull(4));
            }
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(3, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting a single round.
     */
    @SmallTest
    public void testDeletingSingleRound() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        
        result = ContentUris.withAppendedId(Round.CONTENT_URI, roundId);
        int deleted = getMockContentResolver().delete(result, null, null);
        assertEquals(1, deleted);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            fail("There should be 0 rounds after deleting the default one");
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting multiple rounds.
     */
    @SmallTest
    public void testDeletingMultipleRounds() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        int deleted =
            getMockContentResolver().delete(Round.CONTENT_URI, null, null);
        Log.d(TAG, "Deleted " + deleted + " rounds");
        assertEquals(3, deleted);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            fail("There should be 0 rounds after deleting the default one");
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    /**
     * Test deleting one of multiple rounds.
     */
    @SmallTest
    public void testDeletingOneOfManyRounds() {
        ContentValues values = new ContentValues();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        Uri result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        values.put(Round.SCORE, 74);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        result = ContentUris.withAppendedId(Round.CONTENT_URI, roundId);
        int deleted = getMockContentResolver().delete(result, null, null);
        assertEquals(1, deleted);

        Cursor cursor = getMockContentResolver().query(Round.CONTENT_URI,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertFalse(73 == cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(2, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for all of a course's rounds.
     */
    @SmallTest
    public void testQueryRoundsByCourse() {
        long courseId = getDefaultCourseId();
        
        ContentValues values = new ContentValues();
        values.put(Course.NAME, "NewCourse");
        Uri result = getMockContentResolver().insert(Course.CONTENT_URI, values);
        long otherCourseId = Long.valueOf(result.getPathSegments().get(1));

        values.clear();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        values.put(Round.COURSE, courseId);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 74);
        values.put(Round.COURSE, otherCourseId);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        result = ContentUris.withAppendedId(Course.CONTENT_URI, courseId);
        result = Uri.withAppendedPath(result, Round.TABLE_NAME);
        Cursor cursor = getMockContentResolver().query(result,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(73, cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for all of a player's rounds.
     */
    @SmallTest
    public void testQueryRoundsByPlayer() {
        long playerId = getDefaultPlayerId();
        
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "NewPlayer");
        Uri result = getMockContentResolver().insert(Player.CONTENT_URI, values);
        long otherPlayerId = Long.valueOf(result.getPathSegments().get(1));

        values.clear();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        values.put(Round.PLAYER, playerId);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 74);
        values.put(Round.PLAYER, otherPlayerId);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        result = ContentUris.withAppendedId(Player.CONTENT_URI, playerId);
        result = Uri.withAppendedPath(result, Round.TABLE_NAME);
        Cursor cursor = getMockContentResolver().query(result,
                Round.PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(3));
            assertEquals(73, cursor.getInt(3));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " rounds");
        assertEquals(1, cursor.getCount());
        cursor.close();
    }

    /**
     * Test querying for all of a course's rounds.
     */
    @SmallTest
    public void testQueryHolesByRound() {
        long courseId = getDefaultCourseId();
        
        ContentValues values = new ContentValues();
        values.put(Course.NAME, "NewCourse");
        Uri result = getMockContentResolver().insert(Course.CONTENT_URI, values);
        long otherCourseId = Long.valueOf(result.getPathSegments().get(1));

        values.clear();
        values.put(Round.SCORE, 72);
        getMockContentResolver().insert(Round.CONTENT_URI, values);
        values.put(Round.SCORE, 73);
        values.put(Round.COURSE, courseId);
        result = getMockContentResolver().insert(Round.CONTENT_URI, values);
        long roundId = Long.parseLong(result.getPathSegments().get(1));
        values.put(Round.SCORE, 74);
        values.put(Round.COURSE, otherCourseId);
        getMockContentResolver().insert(Round.CONTENT_URI, values);

        result = ContentUris.withAppendedId(Round.CONTENT_URI, roundId);
        result = Uri.withAppendedPath(result, Hole.TABLE_NAME);
        Cursor cursor = getMockContentResolver().query(result,
                Hole.PROJECTION, null, null, Hole.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found hole: " + cursor.getInt(2));
            assertEquals(courseId, cursor.getInt(1));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " holes");
        assertEquals(18, cursor.getCount());
        cursor.close();
    }

}
