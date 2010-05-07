package mobi.whichclub.android;

import mobi.whichclub.android.data.Club;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * The UI page to select the hole to go to.
 * @author camrdale
 */
public abstract class ClubChooserActivity extends Activity {

    /** Logging tag. */
    private static final String TAG = "ClubChooserActivity";
    
    /** The position of the club's id in the cursor. */
    private static final int CLUB_ID = 0;
    /** The position of the club's abbreviation in the cursor. */
    private static final int CLUB_ABBR = 1;
    /** The columns to get when querying for clubs. */
    private static final String[] CLUB_PROJECTION = new String[2];
    static {
        CLUB_PROJECTION[CLUB_ID] = Club._ID;
        CLUB_PROJECTION[CLUB_ABBR] = Club.ABBR;
    }
    
    /** The ID of the club chooser dialog. */
    private static final int CLUB_DIALOG = 0;
    /** The buttons on the club chooser dialog. */
    private static final int[] CLUB_BUTTONS = new int[] { R.id.Club00,
            R.id.Club01, R.id.Club02, R.id.Club03, R.id.Club04, R.id.Club05,
            R.id.Club06, R.id.Club07, R.id.Club08, R.id.Club09, R.id.Club10,
            R.id.Club11, R.id.Club12, R.id.Club13 };

    /** The cursor containing the queried clubs. */
    private Cursor clubCursor;
    
    /** The listener to use for presses on the ClubDialog buttons. */
    private OnClickListener mClubButtonListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            clubChosen(v.getId());
        }
    };

    @Override
    protected Dialog onCreateDialog(final int id) {
        switch (id) {
        case CLUB_DIALOG:
            // Show the club chooser
            Dialog dialog = new Dialog(this);

            dialog.setContentView(R.layout.club_dialog);
            dialog.setTitle(getApplicationContext().getString(R.string.ChooseClub));
            
            clubCursor = managedQuery(Club.CONTENT_URI, CLUB_PROJECTION, null, null, null);
            clubCursor.moveToFirst();
            while (!clubCursor.isAfterLast() && clubCursor.getPosition() < CLUB_BUTTONS.length) {
                Button clubButton = (Button) dialog.findViewById(CLUB_BUTTONS[clubCursor.getPosition()]);
                clubButton.setText(clubCursor.getString(CLUB_ABBR));
                clubButton.setOnClickListener(mClubButtonListener);
                clubButton.setClickable(true);
                clubButton.setEnabled(true);
                clubButton.setFocusable(true);
                clubButton.setVisibility(View.VISIBLE);
                clubCursor.moveToNext();
            }
            
            for (int i = clubCursor.getCount(); i < CLUB_BUTTONS.length; i++) {
                Button clubButton = (Button) dialog.findViewById(CLUB_BUTTONS[clubCursor.getPosition()]);
                clubButton.setText("");
                clubButton.setClickable(false);
                clubButton.setEnabled(false);
                clubButton.setFocusable(false);
                clubButton.setVisibility(View.INVISIBLE);
            }
            ((Button) dialog.findViewById(R.id.ClubCancel)).setOnClickListener(mClubButtonListener);
            return dialog;
        
        default:
            return super.onCreateDialog(id);
        }
    }

    /**
     * Discover which club was chosen and return it.
     * @param id the view id of the club that was chosen
     */
    private void clubChosen(final int id) {
        removeDialog(CLUB_DIALOG);
        
        int i = 0;
        while (i < CLUB_BUTTONS.length && CLUB_BUTTONS[i] != id) { i++; }
        
        Uri uri = null;
        if (i < CLUB_BUTTONS.length) {
            clubCursor.moveToPosition(i);
            uri = ContentUris.withAppendedId(Club.CONTENT_URI, clubCursor.getLong(CLUB_ID));
            Log.d(TAG, "Club was chosen: " + uri);
        } else {
            Log.d(TAG, "No chosen club found, probably was cancelled");
        }
        clubCursor.close();
        clubChosen(uri);
    }
    
    /**
     * Displays the Club Chooser dialog box.
     */
    protected final void chooseClub() {
        showDialog(CLUB_DIALOG);
    }
    
    /**
     * The results of the Club Chooser dialog box.
     * @param clubUri the URI of the club that was chosen
     */
    protected abstract void clubChosen(Uri clubUri);

}