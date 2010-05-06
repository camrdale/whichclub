package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A location on a golf hole.
 * @author camrdale
 */
public class Location implements BaseColumns {

    /** The name of the table. */
    public static final String TABLE_NAME = "location";

    /** The link to the hole this location is on (Long). */
    public static final String HOLE = "hole";
    /** The description of the location column (String). */
    public static final String DESCRIPTION = "description";
    /** The latitude of the location column (Double). */
    public static final String LATITUDE = "latitude";
    /** The longitude of the location column (Double). */
    public static final String LONGITUDE = "longitude";
    
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
    public static final String DEFAULT_SORT_ORDER = DESCRIPTION + " ASC";

    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(_ID, _ID);
        PROJECTION_MAP.put(DESCRIPTION, DESCRIPTION);
        PROJECTION_MAP.put(LATITUDE, LATITUDE);
        PROJECTION_MAP.put(LONGITUDE, LONGITUDE);
    }

    /** Protected default constructor for utility class. */
    protected Location() { }
    
}
