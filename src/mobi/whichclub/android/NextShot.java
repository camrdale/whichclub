package mobi.whichclub.android;

import mobi.whichclub.android.data.Shot;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * The UI page to select the hole to go to.
 * @author camrdale
 */
public class NextShot extends ClubChooserActivity {

    /** Logging tag. */
    private static final String TAG = "NextShot";
    
    /** The position of the shot's id in the cursor. */
    private static final int SHOT_ID = 0;
    /** The position of the shot's round in the cursor. */
    private static final int SHOT_ROUND = 1;
    /** The position of the shot's hole in the cursor. */
    private static final int SHOT_HOLE = 2;
    /** The position of the shot's ball in the cursor. */
    private static final int SHOT_BALL = 3;
    /** The position of the shot's number in the cursor. */
    private static final int SHOT_NUMBER = 4;
    /** The position of the shot's starting latitude in the cursor. */
    private static final int SHOT_START_LATITUDE = 5;
    /** The position of the shot's starting longitude in the cursor. */
    private static final int SHOT_START_LONGITUDE = 6;
    /** The columns to get when querying for holes. */
    private static final String[] SHOT_PROJECTION = new String[7];
    static {
        SHOT_PROJECTION[SHOT_ID] = Shot._ID;
        SHOT_PROJECTION[SHOT_ROUND] = Shot.ROUND;
        SHOT_PROJECTION[SHOT_HOLE] = Shot.HOLE;
        SHOT_PROJECTION[SHOT_BALL] = Shot.BALL;
        SHOT_PROJECTION[SHOT_NUMBER] = Shot.NUMBER;
        SHOT_PROJECTION[SHOT_START_LATITUDE] = Shot.START_LATITUDE;
        SHOT_PROJECTION[SHOT_START_LONGITUDE] = Shot.START_LONGITUDE;
    }
    
    /** The URI of the data for this activity. */
    private Uri uri;
    /** The cursor containing the queried data. */
    private Cursor cursor;
    /** The text containing the current distance of the shot. */
    private TextView distanceText;
    
    /** The listener to use for presses on the NextShot button. */
    private OnClickListener mNextShotListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            nextShot();
        }
    };
    /** The listener to use for presses on the Putt button. */
    private OnClickListener mPuttListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
        }
    };

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        uri = intent.getData();
        
        // If we were unable to create a new shot, then just finish
        // this activity.  A RESULT_CANCELED will be sent back to the
        // original activity if they requested a result.
        if (uri == null) {
            Log.e(TAG, "Failed to insert new shot into " + getIntent().getData());
            finish();
            return;
        }

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.next_shot);
        
        // Hook up button presses to the appropriate event handlers.
        ((Button) findViewById(R.id.NextShot)).setOnClickListener(mNextShotListener);
        ((Button) findViewById(R.id.Putt)).setOnClickListener(mPuttListener);

        // The state for the hole.
        distanceText = (TextView) findViewById(R.id.DistanceShot);

        // Get the data for all this round's holes.
        cursor = managedQuery(uri, SHOT_PROJECTION, null, null, null);
    }

    @Override
    protected final void onResume() {
        super.onResume();

        // If we didn't have any trouble retrieving the data, it is now
        // time to get at the stuff.
        if (cursor != null) {
            cursor.moveToFirst();
            updateState();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Failed to retrieve the data for the shot.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            finish();
        }
    }

    /**
     * Update the state of the various UI objects.
     */
    private void updateState() {
        distanceText.setText(Integer.toString(cursor.getInt(SHOT_NUMBER)));
    }

    /**
     * Finish this shot and start a new one.
     */
    private void nextShot() {
        chooseClub();
    }

    @Override
    protected final void clubChosen(final Uri clubUri) {
        ContentValues values = new ContentValues();
        values.put(Shot.DISTANCE, 0.000);
        values.put(Shot.END_LATITUDE, 0.000);
        values.put(Shot.END_LONGITUDE, 0.000);
        getContentResolver().update(uri, values, null, null);
        
        values.clear();
        values.put(Shot.CLUB, Long.parseLong(clubUri.getPathSegments().get(1)));
        values.put(Shot.BALL, cursor.getLong(SHOT_BALL));
        values.put(Shot.HOLE, cursor.getLong(SHOT_HOLE));
        values.put(Shot.ROUND, cursor.getLong(SHOT_ROUND));
        values.put(Shot.NUMBER, cursor.getInt(SHOT_NUMBER) + 1);
        values.put(Shot.START_LATITUDE, 0.000);
        values.put(Shot.START_LONGITUDE, 0.000);
        Uri newShot = getContentResolver().insert(Shot.CONTENT_URI, values);
        
        Intent shotIntent = new Intent(this, NextShot.class);
        shotIntent.setData(newShot);
        startActivity(shotIntent);
        finish();
    }

}