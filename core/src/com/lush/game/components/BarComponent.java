package com.lush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BarComponent implements Component, Pool.Poolable{
    public boolean toMove = false;
    public boolean toMoveRight = true;

    @Override
    public void reset(){
        toMove = false;
        toMoveRight = true;
    }
}
