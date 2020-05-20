package com.lush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent  implements Component, Pool.Poolable{
    public Animation<Texture> animation;
    public float animationStateTime = 0;
    public float x = 0;
    public float y = 0;
    public float width;
    public float height;
    public int srcX = 0;
    public int srcY = 0;
    public int srcWidth = 0;
    public int srcHeight = 0;
    public boolean flipX = false;
    public boolean flipY = false;

    @Override
    public void reset(){
        animation = null;
        animationStateTime = 0;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        srcX = 0;
        srcY = 0;
        srcWidth = 0;
        srcHeight = 0;
        flipX = false;
        flipY = false;
    }
}
