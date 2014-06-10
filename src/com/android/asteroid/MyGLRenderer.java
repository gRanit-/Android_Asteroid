package com.android.asteroid;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Looper;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {
	public MyGLSurfaceView surface;
	public int points = 0;
	private static final String TAG = "MyGLRenderer";
	private float[] modelViewMatrix = new float[16];
	private float[] projectionViewMatrix = new float[16];
	private float width = 0.0f;
	private float height = 0.0f;
	// public CopyOnWriteArrayList<Ship> shipsList = new
	// CopyOnWriteArrayList<Ship>();
	int MAX_PLAYERS = 4;
	Ship[] shipsList = new Ship[MAX_PLAYERS];
	private float ratio = 1.0f;
	private Random random = new Random();

	public Ship player;

	// public Enemy enemy1;
	// public Enemy enemy2;

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {

		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		player = new Ship();
		shipsList[0] = (player);
		for (int i = 0; i < 3; i++)
			shipsList[i + 1] = (randomEnemy());

	}

	@Override
	public void onDrawFrame(GL10 unused) {

		try {
			checkForDamages();
			Thread.sleep(45);
			
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
					| GLES20.GL_DEPTH_BUFFER_BIT);
			
			
			
			
			// ship.draw();

			for (Ship ship : shipsList) {
				ship.projectionMatrix = this.projectionViewMatrix;
				ship.updatePosition();
				ship.draw();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		this.width = width;
		this.height = height;
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

		int shader = GLES20.glCreateShader(type);

		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	public void checkForDamages() {
		// LinkedList<Ship> l=new LinkedList<Ship>();

		// int listSize=shipsList.length;

		for (int i = 0; i < MAX_PLAYERS; i++) {
			Ship s = shipsList[i];
			for (Bullet b : s.list)
				// for(Ship d :shipsList){
				for (int k = 0; k < MAX_PLAYERS; k++) {
					if (k == i)
						continue;
					Ship d = shipsList[k];

					if (s == d)
						continue;

					float[] A = { 0.5f + d.x, d.y + 0.75f };
					float[] B = { d.x, d.y };
					float[] C = { +0.5f + d.x, d.y + 0.5f };

					float[] D = { +1.0f + d.x, d.y + 0.0f };

					float[] bulletPoint1 = { 0.5f + b.x, 0.5f + b.y };
					float[] bulletPoint2 = { 0.5f + b.x, 0.75f + b.y };
					float[] O = { 0.5f, 0.5f };
					float []O1={0.5f,0.75f};
					bulletPoint1 = rotate_point(O, b.angle - 90, bulletPoint1);
					bulletPoint2 = rotate_point(O1, b.angle - 90, bulletPoint2);

					A = rotate_point(O, d.angle - 90, A);
					B = rotate_point(O, d.angle - 90, B);
					C = rotate_point(O, d.angle - 90, C);
					D = rotate_point(O, d.angle - 90, D);
					/*
					 * A[0]+=d.x; A[1]+=d.y; B[0]+=d.x; B[1]+=d.y; C[0]+=d.x;
					 * C[1]+=d.y; D[0]+=d.x; D[1]+=d.y; bulletPoint[0]+=b.x;
					 * bulletPoint[1]+=b.y;
					 */
					if ((PointInTriangle(bulletPoint1, A, B, C) || PointInTriangle(
							bulletPoint1, A, C, D))
							|| (PointInTriangle(bulletPoint2, A, B, C) || PointInTriangle(
									bulletPoint2, A, C, D))) {
						System.out.println("Crashed");
						if (player == s){
							points++;
							
							//((MainActivity) surface.context).showPoints(points);
							//((MainActivity) surface.context).changeText(Integer.toString(points));
							
						}
						if (player == d) {
							System.out.println("Player Killed");
							((MainActivity) surface.context).goToMainMenu(points);
							//player = new Ship();
							//surface.context.goToMainMenu(points);
							try {
								this.finalize();
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//shipsList[k] = null;
							//shipsList[k] = (player);
						} else {
							shipsList[k] = null;
							shipsList[k] = (randomEnemy());
						}

					}

				}

		}

	}

	float[] rotate_point(float[] O, float angle, float[] P) {
		float ox = O[0];
		float oy = O[1];

		float px = P[0];
		float py = P[1];

		float newpx = (float) (Math.cos(angle) * (px - ox) - Math.sin(angle)
				* (py - oy) + ox);

		float newpy = (float) (Math.sin(angle) * (px - ox) + Math.cos(angle)
				* (py - oy) + oy);
		float[] temp = { newpx, newpy };
		return temp;
	}

	float sign(float[] p1, float[] p2, float[] p3) {
		return (p1[0] - p3[0]) * (p2[1] - p3[1]) - (p2[0] - p3[0])
				* (p1[1] - p3[1]);
	}

	boolean PointInTriangle(float[] pt, float[] v1, float[] v2, float[] v3) {

		boolean b1, b2, b3;

		b1 = sign(pt, v1, v2) < 0.0f;
		b2 = sign(pt, v2, v3) < 0.0f;
		b3 = sign(pt, v3, v1) < 0.0f;

		return ((b1 == b2) && (b2 == b3));
	}

	public Enemy randomEnemy() {
		float x = 0.0f;
		float y = 0.0f;

		x = randomInt(-20, 20);
		y = randomInt(-10, 10);

		Enemy e = new Enemy(player.x + x, player.y + y, player.angle, this,
				player.program);

		float[] colors = new float[4];
		for (int i = 0; i < 3; i++) {
			float tempColor = 0.0f;
			while (tempColor < 0.2f || tempColor > 0.8f)
				tempColor = random.nextFloat();
			colors[i] = tempColor;
		}
		colors[3] = 1.0f;
		e.color = colors;
		return e;

	}

	public int randomInt(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public float getShipAngle() {
		return player.angle;
	}

	public void shoot() {
		player.shoot();
	}

	public void right(float a) {
		player.angle += a;

	}

	public void left(float a) {
		player.angle -= a;

	}

}
