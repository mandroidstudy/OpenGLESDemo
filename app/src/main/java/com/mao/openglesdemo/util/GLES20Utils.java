package com.mao.openglesdemo.util;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author maoweiyi
 * @time 2022/2/13
 * @describe
 */
public class GLES20Utils {

    public static int CreateProgram(String v_shadow_source, String f_shadow_source) {
        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vShader,v_shadow_source);
        GLES20.glCompileShader(vShader);

        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fShader,f_shadow_source);
        GLES20.glCompileShader(fShader);

        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vShader);
        GLES20.glAttachShader(program,fShader);
        GLES20.glLinkProgram(program);
        return program;
    }

    public static FloatBuffer asFloatBuffer(float[] attr) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(attr.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(attr);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static ShortBuffer asShortBuffer(short[] attr) {
        ByteBuffer drawOrderByteBuffer = ByteBuffer.allocateDirect(attr.length * 2);
        drawOrderByteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = drawOrderByteBuffer.asShortBuffer();
        shortBuffer.put(attr);
        shortBuffer.position(0);
        return shortBuffer;
    }

    public static int createTextureWithBitmap(Bitmap bitmap) {
        int[] textures = new int[1];
        //生成纹理，n：生成纹理的数量，textures 存储生成的纹理id，offset偏移
        GLES20.glGenTextures(1,textures,0);
        //绑定纹理，绑定之后代码对2D纹理的任何操作都是针对索引为textures[0]的纹理的
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_MIRRORED_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_MIRRORED_REPEAT);
        //加载位图到 OpenGL 中，并把它复制到当前绑定的纹理对象
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        // 生成 MIP 贴图
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        //解除与这个纹理的绑定，以免用其他方法导致改变了这个纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,GLES20.GL_NONE);
        return textures[0];
    }
}
