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
public class Player implements BaseColumns {

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI
            = Uri.parse("content://mobi.whichclub.android.data/player");
    
    public static final String TABLE_NAME = "player";

    public static final String NAME = "name";
    
    private String name;
    
    private Set<Round> rounds;

    /**
     * 
     */
    public Player() {
        super();
    }

    /**
     * @param name
     */
    public Player(final String name) {
        super();
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Set<Round> getRounds() {
        return rounds;
    }

    public final void setRounds(final Set<Round> rounds) {
        this.rounds = rounds;
    }

}
