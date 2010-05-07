package mobi.whichclub.android.provider;

import java.io.File;

import mobi.whichclub.android.data.Ball;
import mobi.whichclub.android.data.Club;
import mobi.whichclub.android.data.Course;
import mobi.whichclub.android.data.Hole;
import mobi.whichclub.android.data.Location;
import mobi.whichclub.android.data.Player;
import mobi.whichclub.android.data.Round;
import mobi.whichclub.android.data.Shot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database helper for working with the WhichClub database.
 * @author camrdale
 */
public class DbHelper extends SQLiteOpenHelper {
    
    /** Logging tag. */
    private static final String TAG = "DbHelper";
    /** The name of the database file. */
    public static final String DATABASE_FILENAME = "WhichClub.db";
    /** The current version of the database. */
    public static final int DATABASE_VERSION = 1;

    /**
     * Get the database file used by this helper.
     * @param context the application context
     * @return the database file
     */
    public static final File getDatabaseFile(final Context context) {
        return context.getDatabasePath(DATABASE_FILENAME);
    }

    /**
     * Delete the database.
     * @param context the application context
     * @return whether the database was deleted
     */
    public static final boolean deleteDatabase(final Context context) {
        Log.w(TAG, "Deleting the database: " + getDatabaseFile(context));
        return context.deleteDatabase(DATABASE_FILENAME);
    }

    /**
     * Initialize the object to create the database.
     * @param context the application context
     */
    public DbHelper(final Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {
        Log.i(TAG, "Creating a new database: " + db.getPath());
        db.execSQL("CREATE TABLE " + Player.TABLE_NAME + " ("
                + Player._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Player.NAME + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Course.TABLE_NAME + " ("
                + Course._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Course.CITY + " TEXT,"
                + Course.COUNTRY + " TEXT,"
                + Course.LATITUDE + " REAL,"
                + Course.LONGITUDE + " REAL,"
                + Course.NAME + " TEXT,"
                + Course.PAR + " INTEGER,"
                + Course.STATE_PROV + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Ball.TABLE_NAME + " ("
                + Ball._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Ball.MANUFACTURER + " TEXT,"
                + Ball.MODEL + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Club.TABLE_NAME + " ("
                + Club._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Club.DESCRIPTION + " TEXT,"
                + Club.ABBR + " TEXT,"
                + Club.MANUFACTURER + " TEXT,"
                + Club.MODEL + " TEXT,"
                + Club.NUMBER + " INTEGER,"
                + Club.TYPE + " TEXT,"
                + Club.SORT_ORDER + " INTEGER"
                + ");");
        db.execSQL("CREATE TABLE " + Hole.TABLE_NAME + " ("
                + Hole._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Hole.COURSE + " INTEGER,"
                + Hole.HANDICAP + " INTEGER,"
                + Hole.NUMBER + " INTEGER,"
                + Hole.PAR + " INTEGER,"
                + "FOREIGN KEY(" + Hole.COURSE + ") REFERENCES " + Course.TABLE_NAME + "(" + Course._ID + ")"
                + ");");
        db.execSQL("CREATE TABLE " + Location.TABLE_NAME + " ("
                + Location._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Location.DESCRIPTION + " TEXT,"
                + Location.HOLE + " INTEGER,"
                + Location.LATITUDE + " REAL,"
                + Location.LONGITUDE + " REAL,"
                + "FOREIGN KEY(" + Location.HOLE + ") REFERENCES " + Hole.TABLE_NAME + "(" + Hole._ID + ")"
                + ");");
        db.execSQL("CREATE TABLE " + Round.TABLE_NAME + " ("
                + Round._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Round.COURSE + " INTEGER,"
                + Round.DATE + " INTEGER,"
                + Round.GIR + " INTEGER,"
                + Round.PLAYER + " INTEGER,"
                + Round.SCORE + " INTEGER,"
                + "FOREIGN KEY(" + Round.COURSE + ") REFERENCES " + Course.TABLE_NAME + "(" + Course._ID + "),"
                + "FOREIGN KEY(" + Round.PLAYER + ") REFERENCES " + Player.TABLE_NAME + "(" + Player._ID + ")"
                + ");");
        db.execSQL("CREATE TABLE " + Shot.TABLE_NAME + " ("
                + Shot._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Shot.ROUND + " INTEGER,"
                + Shot.HOLE + " INTEGER,"
                + Shot.CLUB + " INTEGER,"
                + Shot.BALL + " INTEGER,"
                + Shot.NUMBER + " INTEGER,"
                + Shot.DISTANCE + " REAL,"
                + Shot.LATERAL + " REAL,"
                + Shot.START_LATITUDE + " REAL,"
                + Shot.START_LONGITUDE + " REAL,"
                + Shot.END_LATITUDE + " REAL,"
                + Shot.END_LONGITUDE + " REAL,"
                + Shot.WIND_DIR + " REAL,"
                + Shot.WIND_SPEED + " REAL,"
                + "FOREIGN KEY(" + Shot.ROUND + ") REFERENCES " + Round.TABLE_NAME + "(" + Round._ID + "),"
                + "FOREIGN KEY(" + Shot.HOLE + ") REFERENCES " + Hole.TABLE_NAME + "(" + Hole._ID + "),"
                + "FOREIGN KEY(" + Shot.CLUB + ") REFERENCES " + Club.TABLE_NAME + "(" + Club._ID + "),"
                + "FOREIGN KEY(" + Shot.BALL + ") REFERENCES " + Ball.TABLE_NAME + "(" + Ball._ID + ")"
                + ");");
        initializeDatabase(db);
    }
    
    /**
     * Initialize a new database with some default records.
     * @param db the database to initialize
     */
    private static void initializeDatabase(final SQLiteDatabase db) {
        Log.i(TAG, "Initializing the new database");
        initializeBall(db);
        initializeClubs(db);
        initializePlayer(db);
        initializeCourse(db);
    }
    
    /**
     * Initialize a new database with a default player.
     * @param db the database to initialize
     */
    private static void initializePlayer(final SQLiteDatabase db) {
        Log.d(TAG, "Creating a default player");
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "Default");
        db.insert(Player.TABLE_NAME, null, values);
        
    }
    
    /**
     * Initialize a new database with a default ball.
     * @param db the database to initialize
     */
    private static void initializeBall(final SQLiteDatabase db) {
        Log.d(TAG, "Creating a default ball");
        ContentValues values = new ContentValues();
        values.put(Ball.MANUFACTURER, "Unknown");
        values.put(Ball.MODEL, "Default");
        db.insert(Ball.TABLE_NAME, null, values);
        
    }
    
    /**
     * Initialize a new database with a default 18-hole course.
     * @param db the database to initialize
     */
    private static void initializeCourse(final SQLiteDatabase db) {
        Log.d(TAG, "Creating a default course");
        ContentValues values = new ContentValues();
        values.put(Course.NAME, "Default");
        long courseId = db.insert(Course.TABLE_NAME, null, values);
        values.clear();
        values.put(Hole.COURSE, courseId);
        for (int i = 1; i <= 18; i++) {
            values.put(Hole.NUMBER, i);
            db.insert(Hole.TABLE_NAME, null, values);
        }
        values.clear();
        
    }
    
    /**
     * Initialize a new database with a default set of clubs.
     * @param db the database to initialize
     */
    private static void initializeClubs(final SQLiteDatabase db) {
        Log.d(TAG, "Creating a set of default clubs");
        ContentValues values = new ContentValues();

        values.put(Club.TYPE, Club.ClubType.Driver.name());
        values.put(Club.SORT_ORDER, Club.ClubType.Driver.getSortOrder());
        values.put(Club.DESCRIPTION, "Driver");
        values.put(Club.ABBR, "D");
        db.insert(Club.TABLE_NAME, null, values);
        
        values.put(Club.TYPE, Club.ClubType.Wood.name());
        values.put(Club.SORT_ORDER, Club.ClubType.Wood.getSortOrder());
        values.put(Club.NUMBER, 3);
        values.put(Club.DESCRIPTION, "3 Wood");
        values.put(Club.ABBR, "3W");
        db.insert(Club.TABLE_NAME, null, values);

        values.put(Club.NUMBER, 5);
        values.put(Club.DESCRIPTION, "5 Wood");
        values.put(Club.ABBR, "5W");
        db.insert(Club.TABLE_NAME, null, values);

        values.put(Club.TYPE, Club.ClubType.Iron.name());
        values.put(Club.SORT_ORDER, Club.ClubType.Iron.getSortOrder());
        for (int i = 3; i <= 9; i++) {
            values.put(Club.NUMBER, i);
            values.put(Club.DESCRIPTION, i + " Iron");
            values.put(Club.ABBR, i + "I");
            db.insert(Club.TABLE_NAME, null, values);
        }

        values.put(Club.TYPE, Club.ClubType.Wedge.name());
        values.put(Club.SORT_ORDER, Club.ClubType.Wedge.getSortOrder());
        values.put(Club.NUMBER, 10);
        values.put(Club.DESCRIPTION, "Pitching Wedge");
        values.put(Club.ABBR, "PW");
        db.insert(Club.TABLE_NAME, null, values);
        
        values.put(Club.NUMBER, 11);
        values.put(Club.DESCRIPTION, "Sand Wedge");
        values.put(Club.ABBR, "SW");
        db.insert(Club.TABLE_NAME, null, values);
        
        values.clear();
        values.put(Club.TYPE, Club.ClubType.Putter.name());
        values.put(Club.SORT_ORDER, Club.ClubType.Putter.getSortOrder());
        values.put(Club.DESCRIPTION, "Putter");
        values.put(Club.ABBR, "P");
        db.insert(Club.TABLE_NAME, null, values);
    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db,
            final int oldVersion, final int newVersion) {
        Log.w(TAG, "Upgrading from " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + Shot.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Round.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Location.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Hole.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Club.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Ball.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Player.TABLE_NAME);
        onCreate(db);
    }

}
