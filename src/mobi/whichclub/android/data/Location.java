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
    
    private Hole hole;
    
    private String description;
    
    private Double latitude;
    
    private Double longitude;

    public final Hole getHole() {
        return hole;
    }

    public final void setHole(Hole hole) {
        this.hole = hole;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final Double getLatitude() {
        return latitude;
    }

    public final void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public final Double getLongitude() {
        return longitude;
    }

    public final void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

}
