package mobi.whichclub.android;

import mobi.whichclub.android.data.Hole;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * The UI page to select the hole to go to.
 * @author camrdale
 */
public class SelectHole extends Activity {

    /** Logging tag. */
    private static final String TAG = null;
    /** The key to use to store the saved position of the cursor. */
    private static final String SAVED_POSITION = "saved_position";
    
    /** The position of the hole's id in the cursor. */
    private static final int HOLE_ID = 0;
    /** The position of the hole's number in the cursor. */
    private static final int HOLE_NUMBER = 1;
    /** The position of the hole's par in the cursor. */
    private static final int HOLE_PAR = 2;
    /** The position of the hole's handicap in the cursor. */
    private static final int HOLE_HANDICAP = 3;
    /** The columns to get when querying for holes. */
    private static final String[] HOLE_PROJECTION = new String[4];
    static {
        HOLE_PROJECTION[HOLE_ID] = Hole._ID;
        HOLE_PROJECTION[HOLE_NUMBER] = Hole.NUMBER;
        HOLE_PROJECTION[HOLE_PAR] = Hole.PAR;
        HOLE_PROJECTION[HOLE_HANDICAP] = Hole.HANDICAP;
    }
    
    /** The cursor containing the queried data. */
    private Cursor cursor;
    /** The text containing the number of the hole. */
    private TextView holeText;
    /** The text containing the par of the hole. */
    private TextView parText;
    /** The current position in the cursor. */
    private int currentPosition;
    
    /** The listener to use for changes to the par spinner. */
    private OnItemSelectedListener mParChosenListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(final AdapterView<?> parent, final View view,
                final int position, final long id) {
            parText.setText(parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(final AdapterView<?> parent) { }
    };
    /** The listener to use for presses on the Prev button. */
    private OnClickListener mPrevHoleListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            currentPosition -= 1;
            if (currentPosition < 0) {
                currentPosition = cursor.getCount() - 1;
            }
            updateState();
        }
    };
    /** The listener to use for presses on the Next button. */
    private OnClickListener mNextHoleListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            currentPosition += 1;
            if (currentPosition >= cursor.getCount()) {
                currentPosition = 0;
            }
            updateState();
        }
    };

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        Uri uri = intent.getData();
        
        // If we were unable to create a new round, then just finish
        // this activity.  A RESULT_CANCELED will be sent back to the
        // original activity if they requested a result.
        if (uri == null) {
            Log.e(TAG, "Failed to insert new round into " + getIntent().getData());
            finish();
            return;
        }

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.select_hole);
        
        // Hook up button presses to the appropriate event handlers.
        ((Button) findViewById(R.id.Prev)).setOnClickListener(mPrevHoleListener);
        ((Button) findViewById(R.id.Next)).setOnClickListener(mNextHoleListener);
        ((Spinner) findViewById(R.id.ParChooser)).setOnItemSelectedListener(mParChosenListener);

        // The state for the hole.
        holeText = (TextView) findViewById(R.id.HoleNumber);
        parText = (TextView) findViewById(R.id.Par);

        // Get the data for all this round's holes.
        cursor = managedQuery(uri, HOLE_PROJECTION, null, null, Hole.NUMBER + " ASC");

        // If an instance of this activity had previously stopped, we can
        // get the original position it was at.
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(SAVED_POSITION);
        } else {
            currentPosition = 0;
        }
    }

    @Override
    protected final void onResume() {
        super.onResume();

        // If we didn't have any trouble retrieving the data, it is now
        // time to get at the stuff.
        if (cursor != null) {
            updateState();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Failed to retrieve the data for the holes for this round.");
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

    @Override
    protected final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save away the current position, so we still have it if the activity
        // needs to be killed while paused.
        outState.putInt(SAVED_POSITION, currentPosition);
    }
    
    /**
     * Update the state of the various UI objects.
     */
    private void updateState() {
        cursor.moveToPosition(currentPosition);
        holeText.setText(Integer.toString(cursor.getInt(HOLE_NUMBER)));
        parText.setText(Integer.toString(cursor.getInt(HOLE_PAR)));
    }

}