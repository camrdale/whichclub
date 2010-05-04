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
public class Ball implements BaseColumns{

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/ball");
    
    public static final String TABLE_NAME = "ball";

    public static final String MANUFACTURER = "manufacturer";

    public static final String MODEL = "model";
    
}
