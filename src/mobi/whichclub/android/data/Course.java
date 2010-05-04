/**
 * 
 */
package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author cdale
 *
 */
public class Course implements BaseColumns {

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.whichclub.course";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.whichclub.course";

    public static final String DEFAULT_SORT_ORDER = "name ASC";

    public static final String TABLE_NAME = "course";

    public static final String NAME = "name";

    public static final String CITY = "city";

    public static final String STATE_PROV = "state_prov";

    public static final String COUNTRY = "country";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String PAR = "par";

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://" + WhichClubProvider.AUTHORITY + "/" + TABLE_NAME);

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
    
	public static final Map<String, String> sCourseProjectionMap = new HashMap<String, String>();
    
    static {
        sCourseProjectionMap.put(Course._ID, Course._ID);
        sCourseProjectionMap.put(Course.NAME, Course.NAME);
        sCourseProjectionMap.put(Course.PAR, Course.PAR);
        sCourseProjectionMap.put(Course.CITY, Course.CITY);
        sCourseProjectionMap.put(Course.STATE_PROV, Course.STATE_PROV);
        sCourseProjectionMap.put(Course.COUNTRY, Course.COUNTRY);
        sCourseProjectionMap.put(Course.LATITUDE, Course.LATITUDE);
        sCourseProjectionMap.put(Course.LONGITUDE, Course.LONGITUDE);
    }
}
