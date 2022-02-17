package com.mao.openglesdemo.demo2;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author maoweiyi
 * @time 2022/2/10
 * @describe 绘制三角形
 */
public class Triangle {

    //三角形顶点位置
    static float[] triangle = {
            -0.5f,-0.5f,0f,
            0.5f,-0.5f,0f,
            0f,0.5f,0f
    };

    //颜色：R,G,B,A
    static float[] color = {
            1.0f,0.0f,0f,1f
    };

    //顶点着色器
    public static final String VERTEX_SHADER_SOURCE ="" +
            "attribute vec4 vPosition;\n" +
            "void main(){\n" +
            "    gl_Position = vPosition;\n" +
            "}";

    //片元着色器
    public static final String FRAGMENT_SHADER_SOURCE = "" +
            "precision mediump float;\n" +
            "uniform vec4 fColor;\n" +
            "void main() {\n" +
            "    gl_FragColor = fColor;\n" +
            "}";


    private final FloatBuffer vertexBuffer;
    private final int glProgram;

    private int COORDS_PER_VERTEX = 3;
    private final int vertexCount = triangle.length / COORDS_PER_VERTEX;

    public Triangle(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangle.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(triangle);
        vertexBuffer.position(0);

        //创建顶点着色器，vShader是创建的着色器的句柄
        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        //设置着色器的源码
        GLES20.glShaderSource(vShader, VERTEX_SHADER_SOURCE);
        //编译代码
        GLES20.glCompileShader(vShader);

        //创建片元着色器
        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        //设置着色器的源码
        GLES20.glShaderSource(fShader, FRAGMENT_SHADER_SOURCE);
        //编译
        GLES20.glCompileShader(fShader);

        //创建程序
        glProgram = GLES20.glCreateProgram();
        //将两个着色器添加到程序中
        GLES20.glAttachShader(glProgram,vShader);
        GLES20.glAttachShader(glProgram,fShader);
        //链接程序
        GLES20.glLinkProgram(glProgram);
    }

    public void draw() {
        //加载并使用链接好的程序
        GLES20.glUseProgram(glProgram);
        //获取指定名称的attribute变量位置
        int vPositionLocation = GLES20.glGetAttribLocation(glProgram, "vPosition");
        //允许使用顶点坐标数组
        GLES20.glEnableVertexAttribArray(vPositionLocation);
        //将顶点数据赋值到指定属性
        GLES20.glVertexAttribPointer(vPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        //获取指定名称的uniform变量位置
        int fColorLocation = GLES20.glGetUniformLocation(glProgram, "fColor");
        //给指定的Uniform变量赋值 因为color包含4个值所以调用glUniform4fv
        GLES20.glUniform4fv(fColorLocation,1,color,0);

        //绘制
        //GL_TRIANGLES会是三角形  0：告诉 OpenGL 从顶点数组的开头处开始读顶点，vertexCount：告诉 OpenGL 读入vertexCount个顶点
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);
        //禁用指定顶点属性
        GLES20.glDisableVertexAttribArray(vPositionLocation);
        GLES20.glUseProgram(GLES20.GL_NONE);
    }
}
