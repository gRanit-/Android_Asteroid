package com.example.ticktacktoe;

import android.opengl.GLES20;

public class GeneralShader {
	
    public static final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}";

    public static final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";
    
    public static int loadShader(int type,String shaderCode){
    	
    	int shader = GLES20.glCreateShader(type);
    	GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    	
    }
    
    
    
    
}
