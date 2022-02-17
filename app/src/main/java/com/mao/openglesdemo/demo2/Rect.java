package com.mao.openglesdemo.demo2;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe 绘制矩形
 */
public class Rect {

    //颜色
    float[] color = {
            1.0f,0.5f,0f,0.5f
    };

    //顶点着色器
    public static final String v_shader="" +
            "attribute vec4 vPosition;\n" +
            "void main(){\n" +
            "    gl_Position = vPosition;\n" +
            "}";

    //片元着色器
    public static final String f_shader = "" +
            "precision mediump float;\n" +
            "uniform vec4 fColor;\n" +
            "void main() {\n" +
            "    gl_FragColor = fColor;\n" +
            "}";

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawOrderBuffer;

    private final int program;

    private final short[] drawOrder = {0, 1, 2, 0, 2, 3};

    public Rect(){
        //矩形的顶点坐标
        float[] rect = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(rect.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(rect);
        vertexBuffer.position(0);

        ByteBuffer drawOrderByteBuffer = ByteBuffer.allocateDirect(drawOrder.length * 2);
        drawOrderByteBuffer.order(ByteOrder.nativeOrder());
        drawOrderBuffer = drawOrderByteBuffer.asShortBuffer();
        drawOrderBuffer.put(drawOrder);
        drawOrderBuffer.position(0);

        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vShader,v_shader);
        GLES20.glCompileShader(vShader);

        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fShader,f_shader);
        GLES20.glCompileShader(fShader);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vShader);
        GLES20.glAttachShader(program,fShader);
        GLES20.glLinkProgram(program);
        GLES20.glDeleteShader(vShader);
        GLES20.glDeleteShader(fShader);
    }

    public void draw(){
        GLES20.glUseProgram(program);
        int vPositionLocation = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(vPositionLocation);
        //3 代表 每个顶点由几个元素组成
        GLES20.glVertexAttribPointer(vPositionLocation,3,GLES20.GL_FLOAT,false,0,vertexBuffer);

        int fColorLocation = GLES20.glGetUniformLocation(program, "fColor");
        GLES20.glUniform4fv(fColorLocation,1,color,0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,drawOrder.length,GLES20.GL_UNSIGNED_SHORT,drawOrderBuffer);
        GLES20.glDisableVertexAttribArray(vPositionLocation);
    }
}
