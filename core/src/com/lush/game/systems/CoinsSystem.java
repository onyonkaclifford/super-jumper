package com.lush.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.lush.game.components.AnimationComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.CoinComponent;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class CoinsSystem extends IteratingSystem{
    private OrthographicCamera camera;

    public CoinsSystem(OrthographicCamera camera){
        super(Family.all(CoinComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        CoinComponent coinComponent = Mappers.coinComponentComponentMapper.get(entity);
        if(coinComponent == null) return;
        AnimationComponent animationComponent =  Mappers.animationComponentComponentMapper.get(entity);
        BodyComponent bodyComponent = Mappers.bodyComponentComponentMapper.get(entity);
        if(animationComponent.animation != null){
            if(bodyComponent.body != null){
                if(animationComponent.y + animationComponent.height < camera.position.y - Constants.WORLD_HEIGHT / 2f){
                    getEngine().removeEntity(entity);
                }
            }
        }
    }
}
