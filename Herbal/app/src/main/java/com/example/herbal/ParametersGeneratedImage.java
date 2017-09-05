package com.example.herbal;

import android.view.View;

import java.lang.reflect.Constructor;

/**
 * Created by Алатиэль on 01.08.2017.
 */

public class ParametersGeneratedImage {
   ParametersGeneratedImage(View v, int type){
        view = v;
        coordinateX = 0;
        coordinateY = 0;
        _rotation = 0;
        _color = -5;
        _type = type;
    }
    ParametersGeneratedImage(View v, int type, float rotation, float size, int color, int xStart, int yStart, int x, int y){
        view = v;
        startPosX = xStart;
        startPosY = yStart;
        coordinateX = x;
        coordinateY = y;
        _rotation = rotation;
        _color = color;
        _type = type;
        _size = size;
    }
    public int coordinateX, coordinateY;
    public View view;
    public int _type;
    public int _color;
    public float _rotation;
    public int startPosX, startPosY;
    public float _size;
}
