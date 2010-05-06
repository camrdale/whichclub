package mobi.whichclub.android;

import mobi.whichclub.android.data.Course;
import mobi.whichclub.android.data.Hole;
import mobi.whichclub.android.data.Player;
import mobi.whichclub.android.data.Round;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * The UI page used to start a new round of golf.
 * @author camrdale
 */
public class StartRound extends Activity {
    
    /** Logging tag. */
    private static final String TAG = "StartRound";

    /** The selected course record's ID. */
    private Long courseId = null;
    /** The selected player record's ID. */
    private Long playerId = null;

	/** The listener for clicks on the StartRound button. */
    private OnClickListener mNewStartListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            startRound();
        }
    };

    /** The listener for clicks on the CourseName spinner. */
    private OnItemSelectedListener mSelectedCourseListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(final AdapterView<?> parent, final View view,
                final int position, final long id) {
            courseId = id;
        }

        @Override
        public void onNothingSelected(final AdapterView<?> parent) { }
    };
    
    /** The listener for clicks on the PersonChooser spinner. */
    private OnItemSelectedListener mSelectedPlayerListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(final AdapterView<?> parent, final View view,
                final int position, final long id) {
            playerId = id;
        }

        @Override
        public void onNothingSelected(final AdapterView<?> parent) { }
    };
    
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.start_round);

        // Hook up button presses to the appropriate event handler.
        ((Button) findViewById(R.id.StartRound))
        		.setOnClickListener(mNewStartListener);
        
        // Load the Courses spinner
        Spinner courseSpinner = (Spinner) findViewById(R.id.CourseName);
        Cursor cursor = managedQuery(Course.CONTENT_URI,
                Course.PROJECTION, null, null, Course.DEFAULT_SORT_ORDER);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, cursor,
                new String[] {Course.NAME}, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(mSelectedCourseListener);

        // Load the Players spinner
        Spinner playerSpinner = (Spinner) findViewById(R.id.PersonChooser);
        cursor = managedQuery(Player.CONTENT_URI,
                Player.PROJECTION, null, null, Player.DEFAULT_SORT_ORDER);
        adapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, cursor,
                new String[] {Player.NAME}, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerSpinner.setAdapter(adapter);
        playerSpinner.setOnItemSelectedListener(mSelectedPlayerListener);
    }

    /**
     * Start a new round of golf.
     */
    protected final void startRound() {
        Log.i(TAG, "Starting new round for player " + playerId + " on course " + courseId);
        ContentValues values = new ContentValues();
        values.put(Round.COURSE, courseId);
        values.put(Round.PLAYER, playerId);
        Uri newRound = getContentResolver().insert(Round.CONTENT_URI, values);

        Intent i = new Intent(this, SelectHole.class);
        i.setData(Uri.withAppendedPath(newRound, Hole.TABLE_NAME));
        startActivity(i);
        finish();
    }
}
