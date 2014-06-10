package com.android.asteroid;

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
	public Context context;
	public MyGLSurfaceView(Context context) {

		super(context);
		this.context=context;
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
		renderer.surface=this;
		setRenderer((Renderer) renderer);
		//renderer.surface=this;
		// setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	public MyGLSurfaceView(Context context, int x, int y) {
		super(context);
		this.context=context;
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		renderer = new MyGLRenderer();
		renderer.surface=this;
		setRenderer((Renderer) renderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		try {
			Thread.sleep(30);
			float x = e.getX();
			float y = e.getY();
			if (e.getAction() == MotionEvent.ACTION_MOVE) {
				float differenceX = x - prevX;
				float differenceY = y - prevY;
				
					if (differenceX < -2)
						swipeLeft();
					else if (differenceX > 2)
						swipeRight();
					else
						renderer.shoot();
				//} else {
					prevY = prevX = 0;
					//renderer.shoot();
				//}
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

		renderer.right(10);

	}

	public void swipeLeft() {

		renderer.left(10);

	}

}
