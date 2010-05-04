package mobi.whichclub.android;

import android.app.Activity;
import android.os.Bundle;

/**
 * The UI page to select the hole to go to.
 * @author camrdale
 */
public class SelectHole extends Activity {

//    private OnClickListener mNewStartListener = new OnClickListener() {
//        
//        @Override
//        public void onClick(View v) {
//            startRound();
//        }
//    };
    
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.select_hole);

        // Hook up button presses to the appropriate event handler.
//        ((Button) findViewById(R.id.StartRound))
//        		.setOnClickListener(mNewStartListener);
    }

//    /**
//     * 
//     */
//    protected void startRound() {
//        Intent i = new Intent(this, SelectHole.class);
//        
//        ((Spinner) findViewById(R.id.CourseName)).getSelectedItem();
//        ((Spinner) findViewById(R.id.PersonChooser)).getSelectedItem();
//        Round round = new Round(course, player, new Date());
//        startActivity(i);
//    }
}