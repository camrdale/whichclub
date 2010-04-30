/**
 * 
 */
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
 * @author cdale
 *
 */
public class DbHelper extends SQLiteOpenHelper {
    
    private static final String TAG = "DbHelper";
    
    public static final String DATABASE_FILENAME = "WhichClub.db";
    
    public static final int DATABASE_VERSION = 1;

    public static final File getDatabaseFile(Context context) {
        return context.getDatabasePath(DATABASE_FILENAME);
    }

    /**
     * @param context
     */
    public DbHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating a new database");
        db.execSQL("CREATE TABLE " + Player.TABLE_NAME + " ("
                + Player._ID + " INTEGER PRIMARY KEY,"
                + Player.NAME + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Course.TABLE_NAME + " ("
                + Course._ID + " INTEGER PRIMARY KEY,"
                + Course.CITY + " TEXT,"
                + Course.COUNTRY + " TEXT,"
                + Course.LATITUDE + " REAL,"
                + Course.LONGITUDE + " REAL,"
                + Course.NAME + " TEXT,"
                + Course.PAR + " INTEGER,"
                + Course.STATE_PROV + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Ball.TABLE_NAME + " ("
                + Ball._ID + " INTEGER PRIMARY KEY,"
                + Ball.MANUFACTURER + " TEXT,"
                + Ball.MODEL + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Club.TABLE_NAME + " ("
                + Club._ID + " INTEGER PRIMARY KEY,"
                + Club.DESCRIPTION + " TEXT,"
                + Club.MANUFACTURER + " TEXT,"
                + Club.MODEL + " TEXT,"
                + Club.NUMBER + " INTEGER,"
                + Club.TYPE + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE " + Hole.TABLE_NAME + " ("
                + Hole._ID + " INTEGER PRIMARY KEY,"
                + Hole.COURSE + " INTEGER,"
                + Hole.HANDICAP + " INTEGER,"
                + Hole.NUMBER + " INTEGER,"
                + Hole.PAR + " INTEGER"
                + ");");
        db.execSQL("CREATE TABLE " + Location.TABLE_NAME + " ("
                + Location._ID + " INTEGER PRIMARY KEY,"
                + Location.DESCRIPTION + " TEXT,"
                + Location.HOLE + " INTEGER,"
                + Location.LATITUDE + " REAL,"
                + Location.LONGITUDE + " REAL"
                + ");");
        db.execSQL("CREATE TABLE " + Round.TABLE_NAME + " ("
                + Round._ID + " INTEGER PRIMARY KEY,"
                + Round.COURSE + " INTEGER,"
                + Round.DATE + " INTEGER,"
                + Round.GIR + " INTEGER,"
                + Round.PLAYER + " INTEGER"
                + Round.SCORE + " INTEGER"
                + ");");
        db.execSQL("CREATE TABLE " + Shot.TABLE_NAME + " ("
                + Shot._ID + " INTEGER PRIMARY KEY,"
                + Shot.BALL + " INTEGER,"
                + Shot.CLUB + " INTEGER,"
                + Shot.DISTANCE + " REAL,"
                + Shot.HOLE + " INTEGER,"
                + Shot.LATERAL + " REAL,"
                + Shot.NUMBER + " INTEGER,"
                + Shot.ROUND + " INTEGER,"
                + Shot.WIND_DIR + " REAL,"
                + Shot.WIND_SPEED + " REAL,"
                + Shot.START_LATITUDE + " REAL,"
                + Shot.START_LONGITUDE + " REAL,"
                + Shot.END_LATITUDE + " REAL,"
                + Shot.END_LONGITUDE + " REAL"
                + ");");
        initializeDatabase(db);
    }
    
    private static void initializeDatabase(SQLiteDatabase db) {
        Log.i(TAG, "Initializing the new database");
        initializeBall(db);
        initializeClubs(db);
        initializePlayer(db);
        initializeCourse(db);
    }
    
    private static void initializePlayer(SQLiteDatabase db) {
        Log.d(TAG, "Creating a default player");
        ContentValues values = new ContentValues();
        values.put(Player.NAME, "Default");
        db.insert(Player.TABLE_NAME, null, values);
        
    }
    
    private static void initializeBall(SQLiteDatabase db) {
        Log.d(TAG, "Creating a default ball");
        ContentValues values = new ContentValues();
        values.put(Ball.MANUFACTURER, "Unknown");
        values.put(Ball.MODEL, "Default");
        db.insert(Ball.TABLE_NAME, null, values);
        
    }
    
    private static void initializeCourse(SQLiteDatabase db) {
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
    
    private static void initializeClubs(SQLiteDatabase db) {
        Log.d(TAG, "Creating a set of default clubs");
        ContentValues values = new ContentValues();
        values.put(Club.TYPE, Club.ClubType.Driver.name());
        values.put(Club.DESCRIPTION, "Driver");
        db.insert(Club.TABLE_NAME, null, values);
        values.put(Club.TYPE, Club.ClubType.Wood.name());
        values.put(Club.NUMBER, 3);
        values.put(Club.DESCRIPTION, "3 Wood");
        db.insert(Club.TABLE_NAME, null, values);
        values.put(Club.NUMBER, 5);
        values.put(Club.DESCRIPTION, "5 Wood");
        db.insert(Club.TABLE_NAME, null, values);
        values.put(Club.TYPE, Club.ClubType.Iron.name());
        for (int i = 3; i <= 9; i++) {
            values.put(Club.NUMBER, i);
            values.put(Club.DESCRIPTION, i + " Iron");
            db.insert(Club.TABLE_NAME, null, values);
        }
        values.put(Club.TYPE, Club.ClubType.Wedge.name());
        values.put(Club.NUMBER, 10);
        values.put(Club.DESCRIPTION, "Pitching Wedge");
        db.insert(Club.TABLE_NAME, null, values);
        values.put(Club.NUMBER, 11);
        values.put(Club.DESCRIPTION, "Sand Wedge");
        db.insert(Club.TABLE_NAME, null, values);
        values.put(Club.TYPE, Club.ClubType.Putter.name());
        values.put(Club.DESCRIPTION, "Putter");
        db.insert(Club.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
