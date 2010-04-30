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
    
    private ClubType type;
    
    private Integer number;
    
    private String manufacturer;
    
    private String model;
    
    private String description;

    private Set<Shot> shots;
    
    /**
     * 
     */
    public Club() {
        super();
    }

    /**
     * @param type
     * @param number
     */
    public Club(final ClubType type, final Integer number) {
        super();
        this.type = type;
        this.number = number;
        this.description = buildDescription();
    }

    /**
     * @param type
     * @param number
     * @param description
     */
    public Club(final ClubType type, final Integer number, final String description) {
        super();
        this.type = type;
        this.number = number;
        this.description = description;
    }

    public final ClubType getType() {
        return type;
    }

    public final void setType(final ClubType type) {
        boolean wasBuilt = false;
        if (description != null) {
            wasBuilt = description.equals(buildDescription());
        }
        this.type = type;
        if (description == null || wasBuilt) {
            description = buildDescription();
        }
    }

    public final Integer getNumber() {
        return number;
    }

    public final void setNumber(final Integer number) {
        boolean wasBuilt = false;
        if (description != null) {
            wasBuilt = description.equals(buildDescription());
        }
        this.number = number;
        if (description == null || wasBuilt) {
            description = buildDescription();
        }
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

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final Set<Shot> getShots() {
        return shots;
    }

    public final void setShots(final Set<Shot> shots) {
        this.shots = shots;
    }
    
    private String buildDescription() {
        StringBuilder newDescription = new StringBuilder();
        if (number != null) {
            newDescription.append(number.toString());
            newDescription.append(" ");
        }
        if (type != null) {
            newDescription.append(type.name());
        }
        if (newDescription.length() > 0) {
            return newDescription.toString();
        } else {
            return null;
        }
    }

}
