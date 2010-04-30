/**
 * 
 */
package mobi.whichclub.android.data;

import java.util.Date;
import java.util.Set;

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
    
    private Course course;
    
    private Player player;
    
    private Integer score;
    
    private Integer greensInRegulation;
    
    private Date date;
    
    private Set<Shot> shots;

    /**
     * 
     */
    public Round() {
        super();
    }

    /**
     * @param course
     * @param player
     * @param date
     */
    public Round(final Course course, final Player player, final Date date) {
        super();
        this.course = course;
        this.player = player;
        this.date = date;
    }

    public final Course getCourse() {
        return course;
    }

    public final void setCourse(final Course course) {
        this.course = course;
    }

    public final Player getPlayer() {
        return player;
    }

    public final void setPlayer(final Player player) {
        this.player = player;
    }

    public final Integer getScore() {
        return score;
    }

    public final void setScore(final Integer score) {
        this.score = score;
    }

    public final Integer getGreensInRegulation() {
        return greensInRegulation;
    }

    public final void setGreensInRegulation(final Integer greensInRegulation) {
        this.greensInRegulation = greensInRegulation;
    }

    public final Date getDate() {
        return date;
    }

    public final void setDate(final Date date) {
        this.date = date;
    }

    public final Set<Shot> getShots() {
        return shots;
    }

    public final void setShots(final Set<Shot> shots) {
        this.shots = shots;
    }

}
