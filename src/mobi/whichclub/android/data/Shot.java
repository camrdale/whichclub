/**
 * 
 */
package mobi.whichclub.android.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author cdale
 *
 */
public class Shot implements BaseColumns {
    
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/shot");
    
    public static final String TABLE_NAME = "shot";

    public static final String HOLE = "hole";

    public static final String ROUND = "round";

    public static final String CLUB = "club";

    public static final String BALL = "ball";

    public static final String NUMBER = "number";

    public static final String DISTANCE = "distance";

    public static final String LATERAL = "lateral";

    public static final String WIND_DIR = "wind_dir";

    public static final String WIND_SPEED = "wind_speed";
    
    public static final String START_LATITUDE = "start_lat";
    
    public static final String END_LATITUDE = "end_lat";
    
    public static final String START_LONGITUDE = "start_long";
    
    public static final String END_LONGITUDE = "end_long";
    
}
