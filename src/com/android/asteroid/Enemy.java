package com.android.asteroid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class Enemy extends Ship {
	MyGLRenderer renderer;
	Random rgen = new Random();
	public long timestamp = 0;

	Enemy(float x, float y, float angle, MyGLRenderer renderer, int program) {
		// super(x,y);
		// this.angle=angle;

		// float []temp={ 1.0f, 0.709803922f, 0.898039216f, 1.0f };
		// this.color=temp;
		this.program = program;
		this.renderer = renderer;
		this.scale = 0.15f;
		this.speed = 0.09f;

		this.x = x;
		this.y = y;

		float[] test = {

		0.5f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f,

		0.5f, 0.75f, 0.0f, 1.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f,

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

	@Override
	public void draw() {
		// /updatePosition();
		super.draw();
	}

	public void updatePosition() {
		if (!crashed) {
			long diff = System.currentTimeMillis() - timestamp;
			if (diff > 1000) {
				float diffX = renderer.player.x + renderer.randomInt(-2, 2)
						- this.x;
				float diffY = renderer.player.y + renderer.randomInt(-2, 2)
						- this.y;
				this.angle = (float) ((float) Math.atan2(diffY, diffX) * 180 / 3.141592653589793238462643383279)
						+ renderer.randomInt(-15, 15);
				// this.angle=renderer.ship.angle;

				timestamp = System.currentTimeMillis();
			} else if (diff > 500 && diff % 10 == 0) {
				shoot();
			}
		}
	}
}
