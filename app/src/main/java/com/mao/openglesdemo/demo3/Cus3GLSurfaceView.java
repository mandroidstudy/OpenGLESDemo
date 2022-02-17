package com.mao.openglesdemo.demo3;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.mao.openglesdemo.demo2.Cus2Renderer;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe
 */
public class Cus3GLSurfaceView extends GLSurfaceView {

    public Cus3GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public Cus3GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(new Cus3Renderer(getContext()));
        //设置渲染模式
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
