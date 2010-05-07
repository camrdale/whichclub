package mobi.whichclub.android.data;

import java.util.HashMap;
import java.util.Map;

import mobi.whichclub.android.provider.WhichClubProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * A golf club.
 * @author camrdale
 */
public class Club implements BaseColumns {
    
    /**
     * The type of the club.
     */
    public static enum ClubType {
        /** Club type. */
        Driver(100), Wood(200), Iron(300), Wedge(400), Putter(500);
        
        /** The default order to sort the club types in.*/
        private int sortOrder;

        /**
         * @param sort the sorting order
         */
        ClubType(final int sort) {
            sortOrder = sort;
        }

        /**
         * @return the sorting order
         */
        public int getSortOrder() {
            return sortOrder;
        }
    }

    /** The name of the table. */
    public static final String TABLE_NAME = "club";

    /** The type of the club column (String of ClubType). */
    public static final String TYPE = "type";
    /** The number of the club column (Integer). */
    public static final String NUMBER = "number";
    /** The manufacturer of the club column (String). */
    public static final String MANUFACTURER = "manufacturer";
    /** The model of the club column (String). */
    public static final String MODEL = "model";
    /** The description of the club column (String). */
    public static final String DESCRIPTION = "description";
    /** The abbreviated description of the club column (String). */
    public static final String ABBR = "abbr";
    /** The sorting order of the club column (Integer). */
    public static final String SORT_ORDER = "sort_order";
    
    /** The content:// style URL for this table. */
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + WhichClubProvider.AUTHORITY + "/" + TABLE_NAME);

    /** The content type for multiple records from this table. */
    public static final String CONTENT_MULTI_TYPE =
        "vnd.android.cursor.dir/vnd.which" + TABLE_NAME;

    /** The content type for a single record from this table. */
    public static final String CONTENT_ITEM_TYPE =
        "vnd.android.cursor.item/vnd.which" + TABLE_NAME;

    /** The default sort order for records returned from this table. */
    public static final String DEFAULT_SORT_ORDER = SORT_ORDER + " ASC, " + NUMBER + " ASC";

    /** A default column projection map to get all the columns. */
    public static final Map<String, String> PROJECTION_MAP =
        new HashMap<String, String>();
    
    static {
        PROJECTION_MAP.put(_ID, _ID);
        PROJECTION_MAP.put(TYPE, TYPE);
        PROJECTION_MAP.put(NUMBER, NUMBER);
        PROJECTION_MAP.put(MANUFACTURER, MANUFACTURER);
        PROJECTION_MAP.put(MODEL, MODEL);
        PROJECTION_MAP.put(DESCRIPTION, DESCRIPTION);
        PROJECTION_MAP.put(ABBR, ABBR);
        PROJECTION_MAP.put(SORT_ORDER, SORT_ORDER);
    }

    /** Protected default constructor for utility class. */
    protected Club() { }
    
}
