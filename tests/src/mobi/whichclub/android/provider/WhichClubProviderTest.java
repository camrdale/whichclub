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
public final class WhichClubProviderTest extends ProviderTestCase2<WhichClubProvider> {
    
    /** Logging tag. */
    private static final String TAG = "WhichClubProviderTest";
    
    /** The position of the course's id in the cursor. */
    private static final int COURSE_ID = 0;
    /** The position of the course's name in the cursor. */
    private static final int COURSE_NAME = 1;
    /** The position of the course's par in the cursor. */
    private static final int COURSE_PAR = 2;
    /** The columns to get when querying for courses. */
    public static final String[] COURSE_PROJECTION = new String[3];
    static {
        COURSE_PROJECTION[COURSE_ID] = Course._ID;
        COURSE_PROJECTION[COURSE_NAME] = Course.NAME;
        COURSE_PROJECTION[COURSE_PAR] = Course.PAR;
    }
    
    /** The position of the player's id in the cursor. */
    private static final int PLAYER_ID = 0;
    /** The position of the player's name in the cursor. */
    private static final int PLAYER_NAME = 1;
    /** The columns to get when querying for players. */
    public static final String[] PLAYER_PROJECTION = new String[2];
    static {
        PLAYER_PROJECTION[PLAYER_ID] = Player._ID;
        PLAYER_PROJECTION[PLAYER_NAME] = Player.NAME;
    }
    
    /** The position of the round's id in the cursor. */
    private static final int ROUND_ID = 0;
    /** The position of the round's score in the cursor. */
    private static final int ROUND_SCORE = 1;
    /** The position of the round's GIR in the cursor. */
    private static final int ROUND_GIR = 2;
    /** The columns to get when querying for rounds. */
    public static final String[] ROUND_PROJECTION = new String[3];
    static {
        ROUND_PROJECTION[ROUND_ID] = Round._ID;
        ROUND_PROJECTION[ROUND_SCORE] = Round.SCORE;
        ROUND_PROJECTION[ROUND_GIR] = Round.GIR;
    }
    
    /** The position of the hole's id in the cursor. */
    private static final int HOLE_ID = 0;
    /** The position of the hole's course in the cursor. */
    private static final int HOLE_COURSE = 1;
    /** The position of the hole's number in the cursor. */
    private static final int HOLE_NUMBER = 2;
    /** The columns to get when querying for holes. */
    private static final String[] HOLE_PROJECTION = new String[3];
    static {
        HOLE_PROJECTION[HOLE_ID] = Hole._ID;
        HOLE_PROJECTION[HOLE_COURSE] = Hole.COURSE;
        HOLE_PROJECTION[HOLE_NUMBER] = Hole.NUMBER;
    }
    
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
                Course.CONTENT_URI, COURSE_PROJECTION,
                Course.NAME + "=?", new String[] {"Default"},
                Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        long courseId = -1;
        while (!cursor.isAfterLast()) {
            assertEquals("Default", cursor.getString(COURSE_NAME));
            courseId = cursor.getLong(COURSE_ID);
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
                Player.CONTENT_URI, PLAYER_PROJECTION,
                Player.NAME + "=?", new String[] {"Default"},
                Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        long playerId = -1;
        while (!cursor.isAfterLast()) {
            assertEquals("Default", cursor.getString(PLAYER_NAME));
            playerId = cursor.getLong(PLAYER_ID);
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertEquals("Default", cursor.getString(COURSE_NAME));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertEquals("Default", cursor.getString(COURSE_NAME));
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
                COURSE_PROJECTION, Course.NAME + "=?", new String[] {"Default"},
                Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertEquals(courseId, cursor.getInt(COURSE_ID));
            assertEquals("Default", cursor.getString(COURSE_NAME));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertEquals("NewCourse", cursor.getString(COURSE_NAME));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertEquals("Default", cursor.getString(COURSE_NAME));
            assertEquals(32, cursor.getInt(COURSE_PAR));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertEquals(32, cursor.getInt(COURSE_PAR));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            if ("Default".equals(cursor.getString(COURSE_NAME))) {
                assertEquals(32, cursor.getInt(COURSE_PAR));
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
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
                COURSE_PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found course: " + cursor.getString(COURSE_NAME));
            assertFalse("Default".equals(cursor.getString(COURSE_NAME)));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertEquals("Default", cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertEquals("Default", cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, Player.NAME + "=?", new String[] {"Default"},
                Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertEquals(playerId, cursor.getInt(PLAYER_ID));
            assertEquals("Default", cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertEquals("NewPlayer", cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertEquals("UpdatedName", cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertEquals("UpdatedName", cursor.getString(PLAYER_NAME));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            if (playerId == cursor.getLong(PLAYER_ID)) {
                assertEquals("UpdatedName", cursor.getString(PLAYER_NAME));
            } else {
                assertFalse("Default".equals(cursor.getString(PLAYER_NAME)));
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
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
                PLAYER_PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found player: " + cursor.getString(PLAYER_NAME));
            assertFalse("Default".equals(cursor.getString(PLAYER_NAME)));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(72, cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(73, cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, Round.SCORE + "=73", null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(roundId, cursor.getLong(ROUND_ID));
            assertEquals(73, cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(72, cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(72, cursor.getInt(ROUND_SCORE));
            assertEquals(12, cursor.getInt(ROUND_GIR));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(12, cursor.getInt(ROUND_GIR));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            if (roundId == cursor.getLong(ROUND_ID)) {
                assertEquals(12, cursor.getInt(ROUND_GIR));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertFalse(73 == cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(73, cursor.getInt(ROUND_SCORE));
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
                ROUND_PROJECTION, null, null, Round.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found round: " + cursor.getInt(ROUND_SCORE));
            assertEquals(73, cursor.getInt(ROUND_SCORE));
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
                HOLE_PROJECTION, null, null, Hole.DEFAULT_SORT_ORDER);
        assertNotNull(cursor);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "Found hole: " + cursor.getInt(HOLE_NUMBER));
            assertEquals(courseId, cursor.getInt(HOLE_COURSE));
            cursor.moveToNext();
        }
        Log.d(TAG, "Found " + cursor.getCount() + " holes");
        assertEquals(18, cursor.getCount());
        cursor.close();
    }

}
