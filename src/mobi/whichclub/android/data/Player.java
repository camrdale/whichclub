package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A golf player.
 * @author camrdale
 */
public class Player implements BaseColumns {

    /** The name of the table. */
    public static final String TABLE_NAME = "player";

    /** The player's name column (String). */
    public static final String NAME = "name";
    
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
        NAME // 1
    };
    
    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(_ID, _ID);
        PROJECTION_MAP.put(NAME, NAME);
    }

    /** Protected default constructor for utility class. */
    protected Player() { }
    
}
