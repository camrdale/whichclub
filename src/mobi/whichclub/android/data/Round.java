package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A single round of golf on a course.
 * @author camrdale
 */
public class Round implements BaseColumns {
    
    /** The name of the table. */
    public static final String TABLE_NAME = "round";

    /** The link to the course (Long). */
    public static final String COURSE = "course";
    /** The link to the player (Long). */
    public static final String PLAYER = "player";
    /** The round's score column (Integer). */
    public static final String SCORE = "score";
    /** The number of greens in regulation column (Integer). */
    public static final String GIR = "gir";
    /** The date of the round in milliseconds since 1970 column (Long). */
    public static final String DATE = "date";
    
    /** The content:// style URL for this table. */
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + WhichClubProvider.AUTHORITY + "/" + TABLE_NAME);

    /** The content type for multiple records from this table. */
    public static final String CONTENT_MULTI_TYPE =
        "vnd.android.cursor.dir/vnd.whichclub." + TABLE_NAME;

    /** The content type for a single record from this table. */
    public static final String CONTENT_ITEM_TYPE =
        "vnd.android.cursor.item/vnd.whichclub." + TABLE_NAME;

    /** The default sort order for records returned from this table. */
    public static final String DEFAULT_SORT_ORDER = DATE + " ASC";

    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(_ID, _ID);
        PROJECTION_MAP.put(COURSE, COURSE);
        PROJECTION_MAP.put(PLAYER, PLAYER);
        PROJECTION_MAP.put(SCORE, SCORE);
        PROJECTION_MAP.put(GIR, GIR);
        PROJECTION_MAP.put(DATE, DATE);
    }

    /** Protected default constructor for utility class. */
    protected Round() { }
    
}
