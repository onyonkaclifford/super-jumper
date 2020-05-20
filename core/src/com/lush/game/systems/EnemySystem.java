package com.lush.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.lush.game.components.AnimationComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.EnemyComponent;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class EnemySystem extends IteratingSystem{
    private OrthographicCamera camera;

    public EnemySystem(OrthographicCamera camera){
        super(Family.all(EnemyComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        EnemyComponent enemyComponent = Mappers.enemyComponentComponentMapper.get(entity);
        if(enemyComponent == null) return;
        AnimationComponent animationComponent =  Mappers.animationComponentComponentMapper.get(entity);
        BodyComponent bodyComponent = Mappers.bodyComponentComponentMapper.get(entity);
        if(animationComponent.animation != null){
            if(bodyComponent.body != null){
                if(animationComponent.y + animationComponent.height < camera.position.y - Constants.WORLD_HEIGHT / 2f){
                    getEngine().removeEntity(entity);
                }else{
                    if(animationComponent.x <= 0) enemyComponent.toMoveRight = true;
                    else if(animationComponent.x + animationComponent.width >= Constants.WORLD_WIDTH)
                        enemyComponent.toMoveRight = false;
                    if(enemyComponent.toMoveRight){
                        bodyComponent.body.setLinearVelocity(25, 0);
                        animationComponent.flipX = true;
                    }else{
                        bodyComponent.body.setLinearVelocity(-25, 0);
                        animationComponent.flipX = false;
                    }
                    animationComponent.x = bodyComponent.body.getPosition().x - animationComponent.width / 2;
                    animationComponent.y = bodyComponent.body.getPosition().y - animationComponent.height / 2;
                }
            }
        }
    }
}
