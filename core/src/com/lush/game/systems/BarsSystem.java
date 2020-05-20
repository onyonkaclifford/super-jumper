package com.lush.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.lush.game.components.BarComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.screens.NewGameScreen;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class BarsSystem extends IteratingSystem{
    private OrthographicCamera camera;
    private NewGameScreen newGameScreen;

    public BarsSystem(OrthographicCamera camera, NewGameScreen newGameScreen){
        super(Family.all(BarComponent.class).get());
        this.camera = camera;
        this.newGameScreen = newGameScreen;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        BarComponent barComponent = Mappers.barComponentComponentMapper.get(entity);
        if(barComponent == null) return;
        SpriteComponent spriteComponent =  Mappers.spriteComponentComponentMapper.get(entity);
        BodyComponent bodyComponent = Mappers.bodyComponentComponentMapper.get(entity);
        if(spriteComponent.sprite != null){
            if(bodyComponent.body != null){
                if(spriteComponent.sprite.getY() + spriteComponent.sprite.getHeight() < camera.position.y - Constants.WORLD_HEIGHT / 2f){
                    bodyComponent.fixture.setSensor(true);
                    getEngine().removeEntity(entity);
                    newGameScreen.createBar();
                }else{
                    if(barComponent.toMove){
                        if(spriteComponent.sprite.getX() <= 0) barComponent.toMoveRight = true;
                        else if(spriteComponent.sprite.getX() + spriteComponent.sprite.getWidth() >= Constants.WORLD_WIDTH)
                            barComponent.toMoveRight = false;
                        if(barComponent.toMoveRight){
                            bodyComponent.body.setLinearVelocity(25, 0);
                        }else{
                            bodyComponent.body.setLinearVelocity(-25, 0);
                        }
                    }
                    spriteComponent.sprite.setPosition(bodyComponent.body.getPosition().x - spriteComponent.sprite.getWidth() / 2,
                            bodyComponent.body.getPosition().y - spriteComponent.sprite.getHeight() / 2);
                    spriteComponent.sprite.setRotation((float) Math.toDegrees(bodyComponent.body.getAngle()));
                }
            }
        }
    }
}
