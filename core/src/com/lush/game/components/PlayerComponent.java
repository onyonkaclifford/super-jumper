package com.lush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable{
    public static float velocityX = 0;

    @Override
    public void reset(){
        velocityX = 0;
    }
}
