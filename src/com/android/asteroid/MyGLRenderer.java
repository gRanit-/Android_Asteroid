package com.android.asteroid;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = "MyGLRenderer";
	private float[] modelViewMatrix = new float[16];
	private float[] projectionViewMatrix = new float[16];
	private float width=0.0f;
	private float height=0.0f;
	//public CopyOnWriteArrayList<Enemy> enemiesList = new CopyOnWriteArrayList<Enemy>();
	private float ratio = 1.0f;
	private Random random=new Random();
	
	public Ship ship=new Ship();
	public Enemy enemy1;
	public Enemy enemy2;
	//MyGLRenderer(){
	//	super();
	//	randomEnemy();
	//}
	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		ship = new Ship();
		//enemy1=randomEnemy();
		//enemy2=randomEnemy();
		enemy1=new Enemy(ship.x,ship.y,ship.angle,this,ship.program);
		// square=new Square();

	}

	@Override
	public void onDrawFrame(GL10 unused) {

		try {
			Thread.sleep(45);

			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
					| GLES20.GL_DEPTH_BUFFER_BIT);

			ship.projectionMatrix = this.projectionViewMatrix;
			ship.draw();
			
			//for(Enemy enemy:enemiesList){
				enemy1.projectionMatrix = this.projectionViewMatrix;
				enemy1.updatePosition();
				enemy1.draw();
				//enemy2.projectionMatrix = this.projectionViewMatrix;
				//enemy2.updatePosition();
				//enemy2.draw();
			//}
			//
			//ship.x=0.0f;
			//ship.y=0.0f;
			
			//

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		this.width=width;
		this.height=height;
		GLES20.glViewport(0, 0, width, height);
		System.out.println(width);
		ratio = (float) width / height;

		Matrix.frustumM(projectionViewMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

	}

	public static void checkGlError(String glOperation) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e(TAG, glOperation + ": glError " + error);
			throw new RuntimeException(glOperation + ": glError " + error);
		}
	}

	public static int loadShader(int type, String shaderCode) {

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	/**
	 * Sets the rotation angle of the square shape (mSquare).
	 * @throws  
	 */
	
	public Enemy randomEnemy(){
		float x=0.0f;
		float y=0.0f;
		if(random.nextInt()%2==0){
			x=0;y=1.0f;}
		else {x=width;y=-1.0f;}
		
		
			return (new Enemy(ship.x+x,ship.y+y,ship.angle,this,ship.program));
		

	}
	public float getShipAngle(){
		return ship.angle;
	}
	public void shoot() {
		ship.shoot();
	}
	public void right(float a) {
		ship.angle += a;
		

	}

	public void left(float a) {
		ship.angle -= a;
		
	}

}
