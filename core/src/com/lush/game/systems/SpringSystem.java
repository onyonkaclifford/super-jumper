package com.lush.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.SpringComponent;
import com.lush.game.components.TextureComponent;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class SpringSystem extends IteratingSystem{
    private OrthographicCamera camera;

    public SpringSystem(OrthographicCamera camera){
        super(Family.all(SpringComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        SpringComponent springComponent = Mappers.springComponentComponentMapper.get(entity);
        if(springComponent == null) return;
        TextureComponent textureComponent =  Mappers.textureComponentComponentMapper.get(entity);
        BodyComponent bodyComponent = Mappers.bodyComponentComponentMapper.get(entity);
        if(textureComponent.texture != null){
            if(bodyComponent.body != null){
                if(textureComponent.y + textureComponent.height < camera.position.y - Constants.WORLD_HEIGHT / 2f){
                    getEngine().removeEntity(entity);
                }
            }
        }
    }
}
