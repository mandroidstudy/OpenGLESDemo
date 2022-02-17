package com.mao.openglesdemo.demo2;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe 绘制三角形和矩形
 */
public class Cus2Renderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Rect mRect;

    //当surface创建时调用，可以做一些OpenGLES上下文的初始化
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0,0,0,0);
        mTriangle = new Triangle();
        mRect = new Rect();
    }

    //surface发生变化的时候调用
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    //绘制的时候调用
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }
}