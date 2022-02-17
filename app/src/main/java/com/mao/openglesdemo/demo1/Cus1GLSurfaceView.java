package com.mao.openglesdemo.demo1;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe 搭建一个简单的opengl 框架
 */
public class Cus1GLSurfaceView extends GLSurfaceView {

    public Cus1GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public Cus1GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setRenderer(new Cus1Renderer());
        //设置渲染模式
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
