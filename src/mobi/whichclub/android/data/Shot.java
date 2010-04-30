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
public class Shot implements BaseColumns {
    
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/shot");
    
    public static final String TABLE_NAME = "shot";

    public static final String HOLE = "hole";

    public static final String ROUND = "round";

    public static final String CLUB = "club";

    public static final String BALL = "ball";

    public static final String NUMBER = "number";

    public static final String DISTANCE = "distance";

    public static final String LATERAL = "lateral";

    public static final String WIND_DIR = "wind_dir";

    public static final String WIND_SPEED = "wind_speed";
    
    public static final String START_LATITUDE = "start_lat";
    
    public static final String END_LATITUDE = "end_lat";
    
    public static final String START_LONGITUDE = "start_long";
    
    public static final String END_LONGITUDE = "end_long";
    
    private Hole hole;
    
    private Round round;
    
    private Club club;
    
    private Ball ball;
    
    private Integer number;
    
    private Double distance;
    
    private Double lateralDistance;
    
    private Double windDirection;
    
    private Double windSpeed;
    
    private Double startLatitude;
    
    private Double startLongitude;

    private Double endLatitude;
    
    private Double endLongitude;

    public final Hole getHole() {
        return hole;
    }

    public final void setHole(final Hole hole) {
        this.hole = hole;
    }

    public final Round getRound() {
        return round;
    }

    public final void setRound(final Round round) {
        this.round = round;
    }

    public final Club getClub() {
        return club;
    }

    public final void setClub(final Club club) {
        this.club = club;
    }

    public final Ball getBall() {
        return ball;
    }

    public final void setBall(final Ball ball) {
        this.ball = ball;
    }

    public final Integer getNumber() {
        return number;
    }

    public final void setNumber(final Integer number) {
        this.number = number;
    }

    public final Double getDistance() {
        return distance;
    }

    public final void setDistance(final Double distance) {
        this.distance = distance;
    }

    public final Double getLateralDistance() {
        return lateralDistance;
    }

    public final void setLateralDistance(final Double lateralDistance) {
        this.lateralDistance = lateralDistance;
    }

    public final Double getWindDirection() {
        return windDirection;
    }

    public final void setWindDirection(final Double windDirection) {
        this.windDirection = windDirection;
    }

    public final Double getWindSpeed() {
        return windSpeed;
    }

    public final void setWindSpeed(final Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public final Double getStartLatitude() {
        return startLatitude;
    }

    public final void setStartLatitude(final Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public final Double getStartLongitude() {
        return startLongitude;
    }

    public final void setStartLongitude(final Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public final Double getEndLatitude() {
        return endLatitude;
    }

    public final void setEndLatitude(final Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public final Double getEndLongitude() {
        return endLongitude;
    }

    public final void setEndLongitude(final Double endLongitude) {
        this.endLongitude = endLongitude;
    }

}
