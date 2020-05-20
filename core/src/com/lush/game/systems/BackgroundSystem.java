package com.lush.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.lush.game.components.BackgroundComponent;
import com.lush.game.components.TextureRegionComponent;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class BackgroundSystem extends IteratingSystem{
    private OrthographicCamera camera;

    public BackgroundSystem(OrthographicCamera camera){
        super(Family.all(BackgroundComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        TextureRegionComponent textureRegionComponent = Mappers.textureRegionComponentComponentMapper.get(entity);
        if(textureRegionComponent.y + Constants.WORLD_HEIGHT < camera.position.y - Constants.WORLD_HEIGHT / 2){
            textureRegionComponent.y = textureRegionComponent.y + (Constants.WORLD_HEIGHT * 2);
        }
    }
}
