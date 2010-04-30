/**
 * 
 */
package mobi.whichclub.android.data;

import java.util.HashSet;
import java.util.Set;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author cdale
 *
 */
public class Course implements BaseColumns {

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/course");
    
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.whichclub.course";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.whichclub.course";

    public static final String DEFAULT_SORT_ORDER = "name ASC";

    public static final String TABLE_NAME = "course";

    public static final String NAME = "name";

    public static final String CITY = "city";

    public static final String STATE_PROV = "state_prov";

    public static final String COUNTRY = "country";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String PAR = "par";

    private String name;
    
    private String city;
    
    private String stateProvince;
    
    private String country;
    
    private Double latitude;
    
    private Double longitude;
    
    private Integer par;
    
    private Set<Hole> holes = new HashSet<Hole>();
    
    private Set<Round> rounds;

    /**
     * 
     */
    public Course() {
        super();
    }

    /**
     * @param name
     */
    public Course(final String name) {
        super();
        this.name = name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public final String getCity() {
        return city;
    }

    public final void setCity(final String city) {
        this.city = city;
    }

    public final String getStateProvince() {
        return stateProvince;
    }

    public final void setStateProvince(final String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public final String getCountry() {
        return country;
    }

    public final void setCountry(final String country) {
        this.country = country;
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

    public final Integer getPar() {
        return par;
    }

    public final void setPar(final Integer par) {
        this.par = par;
    }

    public final Set<Hole> getHoles() {
        return holes;
    }

    /**
     * @param hole
     */
    public void addHole(final Hole hole) {
        holes.add(hole);
        if (!this.equals(hole.getCourse())) {
            hole.setCourse(this);
        }
    }

    public final Set<Round> getRounds() {
        return rounds;
    }

    public final void setRounds(final Set<Round> rounds) {
        this.rounds = rounds;
    }

}
