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
public class Hole implements BaseColumns {
    
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/hole");
    
    public static final String TABLE_NAME = "hole";

    public static final String COURSE = "course";

    public static final String NUMBER = "number";

    public static final String PAR = "par";

    public static final String HANDICAP = "handicap";
    
}
