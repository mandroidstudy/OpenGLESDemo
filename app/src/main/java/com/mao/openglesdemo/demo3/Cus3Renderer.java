package com.mao.openglesdemo.demo3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.mao.openglesdemo.R;
import com.mao.openglesdemo.util.GLES20Utils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe 纹理
 */
public class Cus3Renderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    //顶点坐标
    private final float[] vertex = {
            -1f,1f,0,
            -1f,-1f,0,
             1f,-1f,0,
             1f,1f,0,
    };

    //纹理坐标
    private final float[] texCoord = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    public short[] indices = {0, 1, 2, 0, 2, 3};

    public final String v_shadow_source = "" +
            "attribute vec4 vPosition;\n" +
            "attribute vec2 vTexCoord;\n" +
            "varying vec2 fTexCoord;\n" +
            "void main() {\n" +
            "    gl_Position = vPosition;\n" +
            "    fTexCoord = vTexCoord;\n" +
            "}\n";

    public final String f_shadow_source ="" +
            "precision mediump float;\n" +
            "varying vec2 fTexCoord;\n" +
            "uniform sampler2D fTexture;\n" +
            "void main() {\n" +
            "    gl_FragColor = texture2D(fTexture,fTexCoord);\n" +
            "}\n";

    private int program;

    private FloatBuffer texCoordBuffer;
    private FloatBuffer vertexBuffer;
    private ShortBuffer indicesBuffer;
    private int vPositionLoc;

    public Cus3Renderer(Context context) {
        mContext = context;
    }

    //当surface创建时调用，可以做一些OpenGLES上下文的初始化
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0,0,0,0);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test);
        vertexBuffer = GLES20Utils.asFloatBuffer(vertex);
        texCoordBuffer = GLES20Utils.asFloatBuffer(texCoord);
        indicesBuffer = GLES20Utils.asShortBuffer(indices);

        program = GLES20Utils.CreateProgram(v_shadow_source, f_shadow_source);
        int texture = GLES20Utils.createTextureWithBitmap(bitmap);
        //设置激活的纹理单元
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        int fTextureLoc = GLES20.glGetUniformLocation(program, "fTexture");
        //被选定的纹理单元传递给片元着色器中的fTexture
        GLES20.glUniform1i(fTextureLoc,0);
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
        GLES20.glUseProgram(this.program);
        //给顶点坐标系vPosition属性赋值
        vPositionLoc = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(vPositionLoc);
        GLES20.glVertexAttribPointer(vPositionLoc,3,GLES20.GL_FLOAT,false,0,vertexBuffer);

        //给顶点坐标系vPosition属性赋值
        int vTexCoordLoc = GLES20.glGetAttribLocation(program, "vTexCoord");
        GLES20.glEnableVertexAttribArray(vTexCoordLoc);
        GLES20.glVertexAttribPointer(vTexCoordLoc,2,GLES20.GL_FLOAT,false,0,texCoordBuffer);

        //绘制
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,indices.length,GLES20.GL_UNSIGNED_SHORT,indicesBuffer);

        GLES20.glDisableVertexAttribArray(vPositionLoc);
        GLES20.glDisableVertexAttribArray(vTexCoordLoc);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, GLES20.GL_NONE);
        GLES20.glUseProgram(GLES20.GL_NONE);
    }
}