package com.example.ticktacktoe;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Bullet {

	private int program = 0;
	private float angle=0.0f;
	private float speed=0.0f;
	private float x = 0.0f;
	private float y = 0.0f;
	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;
	public float scale=0.25f;
	private short drawOrder[];
	public long timestamp= System.currentTimeMillis();
	public float[] projectionMatrix = new float[16];
	public float[] modelMatrix = new float[16];
	private float[] model=new float[4];
	private float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

	Bullet(int program, float angle, float speed) {
		this.program = program;
		this.angle = angle;
		this.speed = speed;
	}

	public void initialize(float x, float y) {
		float[] vector = { 0.5f + x, 0.75f + y, 0.0f,
						   0.5f + x, 0.9f + y, 0.0f };
		short[] t = { 0, 1};
		drawOrder = t;
		this.model = vector;
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
		// (# of coordinate values * 4 bytes per float)
				vector.length * 4);
		byteBuffer = byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(model);
		vertexBuffer.position(0);

		ByteBuffer dlb = ByteBuffer.allocateDirect(
		// (# of coordinate values * 2 bytes per short)
				drawOrder.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

	}
	public void draw(){
		if(program==0)
			return;
		
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.setLookAtM(modelMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		Matrix.scaleM(modelMatrix, 0, scale, scale, 0);

		x += (float) (speed * Math.cos(angle * 0.0174532925));
		y += (float) (speed * Math.sin(angle * 0.0174532925));
		
		
		Matrix.translateM(modelMatrix, 0, x+0.5f, y+0.75f , 0.0f);
		Matrix.rotateM(modelMatrix, 0, angle, 0, 0, 1);
		Matrix.translateM(modelMatrix, 0, -0.5f, -0.5f, 0.0f);

		float[] mvpMatrix = new float[16];

		Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, modelMatrix, 0);

		GLES20.glUseProgram(program);

		int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		// MyGLRenderer.checkGlError("glGetAttribLocation");
		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the LINE coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT,
				false, 12, vertexBuffer);

		int mColorHandle = GLES20.glGetUniformLocation(program, "vColor");
		// MyGLRenderer.checkGlError("glGetUniformLocation");

		GLES20.glUniform4fv(mColorHandle, 1, color, 0);

		int mMVPMatrixHandle = GLES20.glGetUniformLocation(program,
				"uMVPMatrix");
		// MyGLRenderer.checkGlError("glGetUniformLocation");

		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		// MyGLRenderer.checkGlError("glUniformMatrix4fv");

		GLES20.glDrawElements(GLES20.GL_LINES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}

}
