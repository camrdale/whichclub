package mobi.whichclub.android;

import mobi.whichclub.android.data.Round;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

/**
 * The UI page used to start a new round of golf.
 * @author camrdale
 */
public class StartRound extends Activity {

	/** The listener for clicks on the StartRound button. */
    private OnClickListener mNewStartListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            startRound();
        }
    };
    
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.start_round);

        // Hook up button presses to the appropriate event handler.
        ((Button) findViewById(R.id.StartRound))
        		.setOnClickListener(mNewStartListener);
    }

    /**
     * Start a new round of golf.
     */
    protected final void startRound() {
        Intent i = new Intent(this, SelectHole.class);
        
        ((Spinner) findViewById(R.id.CourseName)).getSelectedItem();
        ((Spinner) findViewById(R.id.PersonChooser)).getSelectedItem();
        Long roundId = null;
        i.putExtra(Round.class.getCanonicalName(), roundId);
        startActivity(i);
    }
}