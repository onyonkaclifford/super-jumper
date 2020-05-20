package com.lush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

public class SpriteComponent implements Component, Pool.Poolable{
    public Sprite sprite = null;

    @Override
    public void reset(){
        sprite = null;
    }
}
