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
public class Round implements BaseColumns {
    
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/round");
    
    public static final String TABLE_NAME = "round";

    public static final String COURSE = "course";

    public static final String PLAYER = "player";

    public static final String SCORE = "score";

    public static final String GIR = "gir";

    public static final String DATE = "date";
    
}
