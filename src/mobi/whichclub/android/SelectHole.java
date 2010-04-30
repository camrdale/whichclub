package mobi.whichclub.android;

import java.util.Date;

import mobi.whichclub.android.data.Course;
import mobi.whichclub.android.data.Player;
import mobi.whichclub.android.data.Round;

import mobi.whichclub.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class SelectHole extends Activity {

//    private OnClickListener mNewStartListener = new OnClickListener() {
//        
//        @Override
//        public void onClick(View v) {
//            startRound();
//        }
//    };
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.select_hole);

        // Hook up button presses to the appropriate event handler.
//        ((Button) findViewById(R.id.StartRound)).setOnClickListener(mNewStartListener);
    }

//    /**
//     * 
//     */
//    protected void startRound() {
//        Intent i = new Intent(this, SelectHole.class);
//        
//        Course course = (Course) ((Spinner) findViewById(R.id.CourseName)).getSelectedItem();
//        Player player = (Player) ((Spinner) findViewById(R.id.PersonChooser)).getSelectedItem();
//        Round round = new Round(course, player, new Date());
//        startActivity(i);
//    }
}