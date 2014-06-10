package com.android.asteroid;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Ranking extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Show the Up button in the action bar.
		//setupActionBar();
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_ranking);
		
		Intent i=getIntent();
		int p=i.getIntExtra("points", 0);
		TextView tv=(TextView)findViewById(R.id.rankTextView);
		tv.setText("You are dead! Your score: "+Integer.toString(p));
		
		
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.ranking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	class SQLiteManager extends SQLiteOpenHelper{
		private static final String SQL_CREATE_ENTRIES =
			    "CREATE TABLE " + "RANKING" + " (" +
			    "ID" + " INTEGER PRIMARY KEY," +
			    "POINTS, INTEGER"+
			   
			  
			    " )";

			private static final String SQL_DELETE_ENTRIES =
			    "DROP TABLE IF EXISTS " + "RANKING";
		 public static final int DATABASE_VERSION = 1;
		 public static final String DATABASE_NAME = "Players.db";
		 public SQLiteManager(Context context) {
		        super(context, DATABASE_NAME, null, DATABASE_VERSION);
		    }
		 public void onCreate(SQLiteDatabase db) {
		        db.execSQL(SQL_CREATE_ENTRIES);
		    }
		    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		        // This database is only a cache for online data, so its upgrade policy is
		        // to simply to discard the data and start over
		        db.execSQL(SQL_DELETE_ENTRIES);
		        onCreate(db);
		    }
		    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		        onUpgrade(db, oldVersion, newVersion);
		    } 
	}

}
