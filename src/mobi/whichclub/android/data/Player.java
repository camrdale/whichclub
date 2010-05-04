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
public class Player implements BaseColumns {

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/player");
    
    public static final String TABLE_NAME = "player";

    public static final String NAME = "name";
    
}
