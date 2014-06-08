package com.android.asteroid;

import com.android.asteroid.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	private MyGLSurfaceView surfaceView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// int []displaySize=getDisplaySize(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		surfaceView = new MyGLSurfaceView(this);
		setContentView(surfaceView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// The activity must call the GL surface view's onResume() on activity
		// onResume().
		super.onResume();
		surfaceView.onResume();
	}

	@Override
	protected void onPause() {
		// The activity must call the GL surface view's onPause() on activity
		// onPause().
		super.onPause();
		surfaceView.onPause();
	}

	public int[] getDisplaySize(Context context) {
		WindowManager w = (WindowManager) (this
				.getSystemService(Context.WINDOW_SERVICE));
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);
		// since SDK_INT = 1;
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		try {
			// used when 17 > SDK_INT >= 14; includes window decorations
			// (statusbar bar/menu bar)
			widthPixels = (Integer) Display.class.getMethod("getRawWidth")
					.invoke(d);
			heightPixels = (Integer) Display.class.getMethod("getRawHeight")
					.invoke(d);
		} catch (Exception ignored) {
		}
		try {
			// used when SDK_INT >= 17; includes window decorations (statusbar
			// bar/menu bar)
			Point realSize = new Point();
			Display.class.getMethod("getRealSize", Point.class).invoke(d,
					realSize);
			widthPixels = realSize.x;
			heightPixels = realSize.y;
		} catch (Exception ignored) {
		}

		int[] array = new int[2];
		array[0] = widthPixels;
		array[1] = heightPixels;
		return array;

	}

	
}
