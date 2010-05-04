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
public class Club implements BaseColumns {
    
    public static enum ClubType {
        Driver, Wood, Iron, Wedge, Putter;
    }

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/club");
    
    public static final String TABLE_NAME = "club";

    public static final String TYPE = "type";

    public static final String NUMBER = "number";

    public static final String MANUFACTURER = "manufacturer";

    public static final String MODEL = "model";

    public static final String DESCRIPTION = "description";
    
}
