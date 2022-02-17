package com.mao.openglesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mao.openglesdemo.demo1.Cus1GLSurfaceView;
import com.mao.openglesdemo.demo2.Cus2GLSurfaceView;
import com.mao.openglesdemo.demo3.Cus3GLSurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Cus2GLSurfaceView(this));
    }
}