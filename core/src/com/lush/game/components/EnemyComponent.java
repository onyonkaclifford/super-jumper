package com.lush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable{
    public boolean toMoveRight = true;

    @Override
    public void reset(){
        toMoveRight = true;
    }
}
