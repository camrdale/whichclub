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
public class Location implements BaseColumns {

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/location");
    
    public static final String TABLE_NAME = "location";

    public static final String HOLE = "hole";

    public static final String DESCRIPTION = "description";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";
    
}
