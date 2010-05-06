package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A single golf shot.
 * @author camrdale
 *
 */
public class Shot implements BaseColumns {
    
    /** The name of the table. */
    public static final String TABLE_NAME = "shot";

    /** The link to the round (Long). */
    public static final String ROUND = "round";
    /** The link to the hole (Long). */
    public static final String HOLE = "hole";
    /** The link to the club (Long). */
    public static final String CLUB = "club";
    /** The link to the ball (Long). */
    public static final String BALL = "ball";
    /** The number of the shot on the hole (Integer). */
    public static final String NUMBER = "number";
    /** The distance of the shot, in meters (Double). */
    public static final String DISTANCE = "distance";
    /** The lateral distance the shot missed by, in meters (Double). */
    public static final String LATERAL = "lateral";
    /** The starting latitude of the shot (Double). */
    public static final String START_LATITUDE = "start_lat";
    /** The starting longitude of the shot (Double). */
    public static final String START_LONGITUDE = "start_long";
    /** The ending latitude of the shot (Double). */
    public static final String END_LATITUDE = "end_lat";
    /** The ending longitude of the shot (Double). */
    public static final String END_LONGITUDE = "end_long";
    /** The wind direction, in degrees (Double). */
    public static final String WIND_DIR = "wind_dir";
    /** The wind speed, in Km/h (Double). */
    public static final String WIND_SPEED = "wind_speed";
    
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

    /** A default column projection to get all the columns. */
    public static final String[] PROJECTION = new String[] {
        _ID, // 0
        ROUND, // 1
        HOLE, // 2
        CLUB, // 3
        BALL, // 4
        NUMBER, // 5
        DISTANCE, // 6
        LATERAL, // 7
        START_LATITUDE, // 8
        START_LONGITUDE, // 9
        END_LATITUDE, // 10
        END_LONGITUDE, // 11
        WIND_DIR, // 12
        WIND_SPEED // 13
    };
    
    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(_ID, _ID);
        PROJECTION_MAP.put(ROUND, ROUND);
        PROJECTION_MAP.put(HOLE, HOLE);
        PROJECTION_MAP.put(CLUB, CLUB);
        PROJECTION_MAP.put(BALL, BALL);
        PROJECTION_MAP.put(NUMBER, NUMBER);
        PROJECTION_MAP.put(DISTANCE, DISTANCE);
        PROJECTION_MAP.put(LATERAL, LATERAL);
        PROJECTION_MAP.put(START_LATITUDE, START_LATITUDE);
        PROJECTION_MAP.put(START_LONGITUDE, START_LONGITUDE);
        PROJECTION_MAP.put(END_LATITUDE, END_LATITUDE);
        PROJECTION_MAP.put(END_LONGITUDE, END_LONGITUDE);
        PROJECTION_MAP.put(WIND_DIR, WIND_DIR);
        PROJECTION_MAP.put(WIND_SPEED, WIND_SPEED);
    }

    /** Protected default constructor for utility class. */
    protected Shot() { }
    
}
