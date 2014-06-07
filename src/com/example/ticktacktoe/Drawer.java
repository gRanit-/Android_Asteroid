package com.example.ticktacktoe;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

//import com.example.android.opengl.MyGLRenderer;

//import com.example.android.opengl.MyGLRenderer;

import android.opengl.*;

public class Drawer {
	int []name=new int[1];
	int byteSize=0;
	int name2;
	int program;
	int vsh;
	int fsh;
	
	Drawer(){}
	
	public int getVsh() {
		return vsh;
	}

	public void setVsh(int vsh) {
		this.vsh = vsh;
	}
	public void setVsh(String vsh){
		this.vsh=MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vsh);
	}

	public int getFsh() {
		return fsh;
	}

	public void setFsh(int fsh) {
		this.fsh = fsh;
	}
	public void setFsh(String fsh) {
		this.fsh = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fsh);
	}


	public int getProgram() {
		return program;
	}
	public void setProgram(){
		 this.program = GLES20.glCreateProgram();             // create empty OpenGL Program
	        GLES20.glAttachShader(program, vsh);   // add the vertex shader to program
	        GLES20.glAttachShader(program, fsh); // add the fragment shader to program
	        GLES20.glLinkProgram(program);                  // create OpenGL program executables
	}

	public void init(FloatBuffer vertexBuffer,int stride){
		
		 
		

	}
	public void reinit(FloatBuffer vertexBuffer,int byteSize){

	}
	
	public void prepareToDraw(int index,int count){

		
	}
	public void draw(FloatBuffer vertexBuffer,int stride,ShortBuffer drawListBuffer,short[] drawOrder,float[] mvpMatrix,float[] color){
		
		GLES20.glUseProgram(program);
        
        int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                stride, vertexBuffer);


        int mColorHandle = GLES20.glGetUniformLocation(program, "vColor");


        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");


        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");


        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
		
	}
	
	
	public void deleteBuffer(){
	
	}
	
	
}
