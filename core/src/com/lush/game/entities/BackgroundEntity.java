package com.lush.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lush.game.components.BackgroundComponent;
import com.lush.game.components.TextureRegionComponent;
import com.lush.game.utils.Constants;

public class BackgroundEntity implements BaseEntity{
    public enum Upside{
        UPSIDE_UP,
        UPSIDE_DOWN
    }
    private Entity entity;

    public BackgroundEntity(PooledEngine engine, float xPos, float yPos, Upside upside){
        entity = engine.createEntity();
        TextureRegionComponent textureRegionComponent = engine.createComponent(TextureRegionComponent.class);
        BackgroundComponent backgroundComponent = engine.createComponent(BackgroundComponent.class);

        if(upside == Upside.UPSIDE_UP){
            textureRegionComponent.textureRegion = new TextureRegion(new Texture(Gdx.files.internal("assets/background.png")));
        }else if(upside == Upside.UPSIDE_DOWN){
            textureRegionComponent.textureRegion = new TextureRegion(new Texture(Gdx.files.internal("assets/background.png")));
        }
        textureRegionComponent.width = Constants.WORLD_WIDTH;
        textureRegionComponent.height = Constants.WORLD_HEIGHT;
        textureRegionComponent.x = xPos;
        textureRegionComponent.y = yPos;

        entity.add(textureRegionComponent);
        entity.add(backgroundComponent);
    }

    @Override
    public Entity getEntity(){
        return entity;
    }
}
