package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A single golf course.
 * @author camrdale
 */
public class Course implements BaseColumns {

    /** The name of the table. */
    public static final String TABLE_NAME = "course";

    /** The name of the course column (String). */
    public static final String NAME = "name";
    /** The name of the city column (String). */
    public static final String CITY = "city";
    /** The name of the state or province column (String). */
    public static final String STATE_PROV = "state_prov";
    /** The name of the country column (String). */
    public static final String COUNTRY = "country";
    /** The latitude of the course column (Double). */
    public static final String LATITUDE = "latitude";
    /** The longitude of the course column (Double). */
    public static final String LONGITUDE = "longitude";
    /** The par of the course column (Integer). */
    public static final String PAR = "par";

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
    public static final String DEFAULT_SORT_ORDER = NAME + " ASC";

    /** A default column projection to get all the columns. */
    public static final String[] PROJECTION = new String[] {
        _ID, // 0
        NAME, // 1
        PAR, // 2
        CITY, // 3
        STATE_PROV, // 4
        COUNTRY, // 5
        LATITUDE, // 6
        LONGITUDE // 7
    };
    
    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(Course._ID, Course._ID);
        PROJECTION_MAP.put(Course.NAME, Course.NAME);
        PROJECTION_MAP.put(Course.PAR, Course.PAR);
        PROJECTION_MAP.put(Course.CITY, Course.CITY);
        PROJECTION_MAP.put(Course.STATE_PROV, Course.STATE_PROV);
        PROJECTION_MAP.put(Course.COUNTRY, Course.COUNTRY);
        PROJECTION_MAP.put(Course.LATITUDE, Course.LATITUDE);
        PROJECTION_MAP.put(Course.LONGITUDE, Course.LONGITUDE);
    }

    /** Protected default constructor for utility class. */
    protected Course() { }
    
}
