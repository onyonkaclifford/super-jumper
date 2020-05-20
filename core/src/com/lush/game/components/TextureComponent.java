package com.lush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable{
    public Texture texture = null;
    public float x = 0;
    public float y = 0;
    public float originX = 0;
    public float originY = 0;
    public float width = 0;
    public float height = 0;
    public float scaleX = 1;
    public float scaleY = 1;
    public float rotation = 0;
    public boolean clockWise = false;

    @Override
    public void reset(){
        texture = null;
        x = 0;
        y = 0;
        originX = 0;
        originY = 0;
        width = 0;
        height = 0;
        scaleX = 1;
        scaleY = 1;
        rotation = 0;
        clockWise = false;
    }
}
