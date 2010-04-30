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
    
    private Course course;
    
    private Integer number;
    
    private Integer par;
    
    private Integer handicap;
    
    /**
     * @param course
     * @param number
     */
    public Hole(final Course course, final Integer number) {
        super();
        this.course = course;
        this.number = number;
    }

    private Set<Location> locations;
    
    private Set<Shot> shots;

    public final Course getCourse() {
        return course;
    }

    public final void setCourse(final Course course) {
        if (course != null && !course.equals(this.course)) {
            course.addHole(this);
        }
        this.course = course;
    }

    public final Integer getNumber() {
        return number;
    }

    public final void setNumber(final Integer number) {
        this.number = number;
    }

    public final Integer getPar() {
        return par;
    }

    public final void setPar(final Integer par) {
        this.par = par;
    }

    public final Integer getHandicap() {
        return handicap;
    }

    public final void setHandicap(final Integer handicap) {
        this.handicap = handicap;
    }

    public final Set<Location> getLocations() {
        return locations;
    }

    public final void setLocations(final Set<Location> locations) {
        this.locations = locations;
    }

    public final Set<Shot> getShots() {
        return shots;
    }

    public final void setShots(final Set<Shot> shots) {
        this.shots = shots;
    }

}
