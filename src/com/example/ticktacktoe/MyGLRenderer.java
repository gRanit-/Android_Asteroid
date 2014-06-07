package com.example.ticktacktoe;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.*;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = "MyGLRenderer";
	private float[] modelViewMatrix=new float[16];
	private float[] projectionViewMatrix=new float[16];
	private float[]	finalMatrix=new float[16];
    private final float[] mRotationMatrix = new float[16];
    private final float[] motionMatrix = new float[16];
    float []scratch=new float[16];
	private Board board=new Board();
	private Ship ship;
	private float angle=90.0f;
	private Square square;

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		 GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		 ship=new Ship();
		 //square=new Square();
		 
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		
		try {
			Thread.sleep(45);
	
		// Draw background color
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		
		Matrix.setIdentityM(modelViewMatrix, 0);
		// Set the camera position (View matrix)
		Matrix.setLookAtM(ship.modelViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		

		Matrix.scaleM(ship.modelViewMatrix, 0, 0.25f, 0.25f, 0);

		ship.x+=(float) (ship.speed*Math.cos(angle*0.0174532925));
    	ship.y+=(float) (ship.speed*Math.sin(angle*0.0174532925));
    	
    	
		Matrix.translateM(ship.modelViewMatrix, 0, ship.x+0.5f, ship.y+0.5f, 0.0f);
		Matrix.rotateM(ship.modelViewMatrix, 0, angle-90, 0, 0, 1);
		Matrix.translateM(ship.modelViewMatrix, 0, -0.5f, -0.5f, 0.0f);
		
		

		Matrix.multiplyMM(finalMatrix, 0, projectionViewMatrix, 0, ship.modelViewMatrix, 0);
		

		ship.draw(finalMatrix);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 @Override
	    public void onSurfaceChanged(GL10 unused, int width, int height) {
	        // Adjust the viewport based on geometry changes,
	        // such as screen rotation
	        GLES20.glViewport(0, 0, width, height);

	        float ratio = (float) width / height;

	        // this projection matrix is applied to object coordinates
	        // in the onDrawFrame() method
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
	
	public float getAngle() {
        return angle;
    }

    /**
     * Sets the rotation angle of the square shape (mSquare).
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void right(){
    	
    }
    public void left(){
    	//ship.x+=ship.speed*Math.cos(angle);
    	//ship.y+=ship.speed*Math.sin(angle);
    	
    }

}
