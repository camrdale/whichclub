/**
 * 
 */
package mobi.whichclub.android.data;

import java.util.Set;

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
    
    private String manufacturer;
    
    private String model;
    
    private Set<Shot> shots;

    /**
     * 
     */
    public Ball() {
        super();
    }

    /**
     * @param manufacturer
     * @param model
     */
    public Ball(final String manufacturer, final String model) {
        super();
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public final String getManufacturer() {
        return manufacturer;
    }

    public final void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public final String getModel() {
        return model;
    }

    public final void setModel(final String model) {
        this.model = model;
    }

    public final Set<Shot> getShots() {
        return shots;
    }

    public final void setShots(final Set<Shot> shots) {
        this.shots = shots;
    }

}
