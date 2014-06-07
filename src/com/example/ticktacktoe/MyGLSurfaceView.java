package com.example.ticktacktoe;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

public class MyGLSurfaceView extends GLSurfaceView {
	MyGLRenderer renderer;
	private float prevX = 0;
	private float prevY = 0;

	public MyGLSurfaceView(Context context) {

		super(context);
		/*
		 * this.setOnTouchListener(new OnSwipeTouchListener(context) { public
		 * void onSwipeTop() {
		 * 
		 * } public void onSwipeRight() { swipeRight(); } public void
		 * onSwipeLeft() { swipeLeft(); } public void onSwipeBottom() {
		 * 
		 * }
		 * 
		 * 
		 * });
		 */
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		renderer = new MyGLRenderer();
		setRenderer((Renderer) renderer);
		// setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	public MyGLSurfaceView(Context context, int x, int y) {
		super(context);
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		renderer = new MyGLRenderer();
		setRenderer((Renderer) renderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		try {
			Thread.sleep(30);
			float x = e.getX();
			float y = e.getY();
			if (e.getAction() == MotionEvent.ACTION_MOVE) {
				float differenceX = Math.abs(x - prevX);
				float differenceY = Math.abs(y - prevY);
				if (differenceX > differenceY - 10) {
					if (differenceX > 10)
						swipeLeft();
					else if (differenceX < 10)
						swipeRight();
					else
						renderer.shoot();
				} else {
					prevY = prevX = 0;
					renderer.shoot();
				}
				prevX = x;
				prevY = y;
			}else if (e.getAction() == MotionEvent.ACTION_DOWN){renderer.shoot();}
		} catch (InterruptedException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		// this.requestRender();
		return true;

	}

	

	public void swipeRight() {

		renderer.right(5);

	}

	public void swipeLeft() {

		renderer.left(5);

	}

}
