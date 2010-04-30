package mobi.whichclub.android;

import mobi.whichclub.android.data.Ball;
import mobi.whichclub.android.data.Club;
import mobi.whichclub.android.data.Course;
import mobi.whichclub.android.data.Hole;
import mobi.whichclub.android.data.Location;
import mobi.whichclub.android.data.Player;
import mobi.whichclub.android.data.Round;
import mobi.whichclub.android.data.Shot;
import mobi.whichclub.android.provider.DbHelper;

import mobi.whichclub.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity {

    private OnClickListener mNewRoundListener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            launchRound();
        }
    };
    
    private OnClickListener mFindCourseListener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            alertUnimplemented();
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.main);

        // Hook up button presses to the appropriate event handler.
        ((Button) findViewById(R.id.NewRound)).setOnClickListener(mNewRoundListener);
        ((Button) findViewById(R.id.FindCourse)).setOnClickListener(mFindCourseListener);
        
        StringBuilder stats = new StringBuilder();
        DbHelper helper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        stats.append(countRecords(db, Course.TABLE_NAME));
        stats.append(countRecords(db, Round.TABLE_NAME));
        stats.append(countRecords(db, Shot.TABLE_NAME));
        stats.append(countRecords(db, Player.TABLE_NAME));
        stats.append(countRecords(db, Hole.TABLE_NAME));
        stats.append(countRecords(db, Location.TABLE_NAME));
        stats.append(countRecords(db, Club.TABLE_NAME));
        stats.append(countRecords(db, Ball.TABLE_NAME));
        db.close();
        helper.close();
        
        ((TextView) findViewById(R.id.StatsView)).setText(stats.toString());
    }

    /**
     * @param db
     * @param class1
     */
    private String countRecords(SQLiteDatabase db, String table) {
        String stats = table + "s: ";
        Cursor cursor = db.query(table, new String[] {"count(_id)"}, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            stats += cursor.getLong(0) + "\n";
        } else {
            stats += 0 + "\n";
        }
        return stats;
    }

    /**
     * Display an alert indicating the feature has not yet been implemented.
     */
    protected void alertUnimplemented() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("That feature has not yet been implemented.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Launch the StartRound activity.
     */
    protected void launchRound() {
        Intent i = new Intent(this, StartRound.class);
        startActivity(i);
    }
}