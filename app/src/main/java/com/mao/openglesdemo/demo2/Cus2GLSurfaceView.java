package com.mao.openglesdemo.demo2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.mao.openglesdemo.demo1.Cus1Renderer;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe
 */
public class Cus2GLSurfaceView extends GLSurfaceView {

    public Cus2GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public Cus2GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(new Cus2Renderer());
        //设置渲染模式
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
