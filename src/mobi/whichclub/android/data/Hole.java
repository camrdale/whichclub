package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A golf hole.
 * @author camrdale
 */
public class Hole implements BaseColumns {
    
    /** The name of the table. */
    public static final String TABLE_NAME = "hole";

    /** The link to the course (Long). */
    public static final String COURSE = "course";
    /** The number of the hole column (Integer). */
    public static final String NUMBER = "number";
    /** The par of the hole column (Integer). */
    public static final String PAR = "par";
    /** The handicap of the hole column (Integer). */
    public static final String HANDICAP = "handicap";
    
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
    public static final String DEFAULT_SORT_ORDER = NUMBER + " ASC";

    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(_ID, TABLE_NAME + "." + _ID);
        PROJECTION_MAP.put(COURSE, TABLE_NAME + "." + COURSE);
        PROJECTION_MAP.put(NUMBER, TABLE_NAME + "." + NUMBER);
        PROJECTION_MAP.put(PAR, TABLE_NAME + "." + PAR);
        PROJECTION_MAP.put(HANDICAP, TABLE_NAME + "." + HANDICAP);
    }

    /** Protected default constructor for utility class. */
    protected Hole() { }
    
}
