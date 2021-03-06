package com.android.asteroid;

import java.util.concurrent.CopyOnWriteArrayList;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.LinkedList;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Ship {
	public float x = 0.0f;
	public float y = 0.0f;
	public float[] model;
	public float scale = 0.25f;
	public float[] modelMatrix = new float[16];
	public float[] projectionMatrix = new float[16];
	public float angle = 90.0f;
	public FloatBuffer vertexBuffer;
	public ShortBuffer drawListBuffer;
	public float speed = 0.08f;
	public boolean crashed = false;
	// LinkedList <Bullet>list=new LinkedList<Bullet>();
	public CopyOnWriteArrayList<Ship> enemiesList = new CopyOnWriteArrayList<Ship>();
	public CopyOnWriteArrayList<Bullet> list = new CopyOnWriteArrayList<Bullet>();
	public short drawOrder[];

	public float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };
	int vsh, fsh;
	public int program;
	public final String vertexShaderCode =

	"uniform mat4 uMVPMatrix;" + "attribute vec4 vPosition;" + "void main() {" +

	"  gl_Position = uMVPMatrix * vPosition;" + "}";

	public final String fragmentShaderCode = "precision mediump float;"
			+ "uniform vec4 vColor;" + "void main() {"
			+ "  gl_FragColor = vColor;" + "}";

	Ship() {
		initialize(0, 0);

	}

	public void updatePosition() {
	};

	Ship(int program) {
		this.program = program;
		float[] test = {

		0.5f, 0.75f, 0.0f,

		0.0f, 0.0f, 0.0f,

		0.5f, 0.5f, 0.0f,

		0.5f, 0.75f, 0.0f,

		1.0f, 0.0f, 0.0f,

		0.5f, 0.5f, 0.0f,

		};
		short[] t = { 0, 1, 2, 3, 4, 5, 6 };
		drawOrder = t;
		this.model = test;
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
		// (# of coordinate values * 4 bytes per float)
				test.length * 4);
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

	Ship(float x, float y) {
		initialize(x, y);

	}

	public void initialize(float x, float y) {
		this.x = x;
		this.y = y;

		float[] test = {

		0.5f + x, 0.75f + y, 0.0f, 0.0f + x, 0.0f + y, 0.0f, 0.5f + x,
				0.5f + y, 0.0f, 0.5f + x, 0.75f + y, 0.0f, 1.0f + x, 0.0f + y,
				0.0f, 0.5f + x, 0.5f + y, 0.0f,

		};
		short[] t = { 0, 1, 2, 3, 4, 5, 6 };
		drawOrder = t;
		this.model = test;
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
		// (# of coordinate values * 4 bytes per float)
				test.length * 4);
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

		this.fsh = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
				fragmentShaderCode);
		this.vsh = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
				vertexShaderCode);

		this.program = GLES20.glCreateProgram(); // create empty OpenGL Program
		GLES20.glAttachShader(program, vsh); // add the vertex shader to program
		GLES20.glAttachShader(program, fsh); // add the fragment shader to
												// program
		GLES20.glLinkProgram(program);

	}

	public void shoot() {
		if(!crashed)
		if (list.size() <= 20) {

			Bullet b = new Bullet(program, angle, speed + 0.09f);
			b.initialize(x, y);
			b.scale = scale;
			b.color = color;
			b.projectionMatrix = this.projectionMatrix;
			list.add(b);
		}
	}

	public void draw() {
		if (crashed)
			color[3] -= 0.2;
		else {
			Bullet temp = null;

			if (list.size() > 0)
				temp = list.get(0);
			if (temp != null) {
				if (System.currentTimeMillis() - temp.timestamp >= 2000)
					list.remove(0);
				for (Bullet bullet : list)
					bullet.draw();
			}

			// ////////////////////////////
			x += (float) (speed * Math
					.cos(angle * 3.141592653589793238462643383279 / 180));
			y += (float) (speed * Math
					.sin(angle * 3.141592653589793238462643383279 / 180));
			// ///////////////////////////
		}
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.setLookAtM(modelMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		Matrix.scaleM(modelMatrix, 0, scale, scale, 0);

		Matrix.translateM(modelMatrix, 0, x + 0.5f, y + 0.5f, 0.0f);
		Matrix.rotateM(modelMatrix, 0, angle - 90, 0, 0, 1);
		Matrix.translateM(modelMatrix, 0, -0.5f, -0.5f, 0.0f);

		float[] mvpMatrix = new float[16];

		Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, modelMatrix, 0);

		GLES20.glUseProgram(program);

		int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		// MyGLRenderer.checkGlError("glGetAttribLocation");
		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the triangle coordinate data
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

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);

	}
}
