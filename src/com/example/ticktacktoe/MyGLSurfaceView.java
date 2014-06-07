package com.example.ticktacktoe;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

public class MyGLSurfaceView extends GLSurfaceView  {
	MyGLRenderer renderer;
	private float prevX=0;
	private float prevY=0;
	public MyGLSurfaceView(Context context) {
		
		super(context);
		/*this.setOnTouchListener(new OnSwipeTouchListener(context) {
		    public void onSwipeTop() {
		       
		    }
		    public void onSwipeRight() {
		       swipeRight();
		    }
		    public void onSwipeLeft() {
		      swipeLeft();
		    }
		    public void onSwipeBottom() {
		       
		    }

		
		});*/
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		renderer=new MyGLRenderer();
		setRenderer((Renderer)renderer);
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	public MyGLSurfaceView(Context context,int x,int y) {
		super(context);
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		renderer=new MyGLRenderer();
		setRenderer((Renderer)renderer);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		
		float x=e.getX();
		float y=e.getY();
		if(e.getAction()==MotionEvent.ACTION_MOVE){
			float differenceX =Math.abs(x-prevX);
			float differenceY=Math.abs(y-prevY);
			if(differenceX>differenceY-10){
			if(differenceX>10)
				swipeLeft();
			else if(differenceX<10)
				swipeRight();
			else shoot();
			}else{prevY=prevX=0;}
			prevX=x;
			prevY=y;
		}
		//this.requestRender();
		return true;

	}
	public void shoot(){
		
	}
	
	public void swipeRight(){
		float angle=renderer.getAngle();
		renderer.setAngle(angle+10);
		//renderer.right();
		
	
	}
	public void swipeLeft(){
		float angle=renderer.getAngle();
		renderer.setAngle(angle-10);
		//renderer.left();
		
	}
	
	
	
	
	
}
