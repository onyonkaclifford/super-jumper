package com.lush.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.lush.game.components.BackgroundComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.PlayerComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.components.TextureRegionComponent;
import com.lush.game.screens.NewGameScreen;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class PlayerSystem extends EntitySystem{
    private OrthographicCamera camera;
    private Family playersFamily;
    private Family backgroundFamily;
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> backgroundEntities;

    public PlayerSystem(OrthographicCamera camera){
        this.camera = camera;
        playersFamily = Family.all(PlayerComponent.class).get();
        backgroundFamily = Family.all(BackgroundComponent.class).get();
    }

    @Override
    public void addedToEngine(Engine engine){
        super.addedToEngine(engine);
        playerEntities = engine.getEntitiesFor(playersFamily);
        backgroundEntities = engine.getEntitiesFor(backgroundFamily);
    }

    @Override
    public void removedFromEngine(Engine engine){
        super.removedFromEngine(engine);
        playerEntities = null;
        backgroundEntities = null;
    }

    @Override
    public void update(float deltaTime){
        TextureRegionComponent backgroundTextureRegionComponent = Mappers.textureRegionComponentComponentMapper.get(backgroundEntities.first());
        for(Entity entity : playerEntities){
            SpriteComponent spriteComponent = Mappers.spriteComponentComponentMapper.get(entity);
            BodyComponent bodyComponent = Mappers.bodyComponentComponentMapper.get(entity);
            if(spriteComponent.sprite != null){
                if(bodyComponent.body != null){
                    if(bodyComponent.body.getPosition().x < 0){
                        bodyComponent.body.setTransform(Constants.WORLD_WIDTH, bodyComponent.body.getPosition().y, 0);
                    }else if(bodyComponent.body.getPosition().x > Constants.WORLD_WIDTH){
                        bodyComponent.body.setTransform(0, bodyComponent.body.getPosition().y, 0);
                    }

                    if(bodyComponent.body.getPosition().y > camera.position.y){
                        backgroundTextureRegionComponent.y += bodyComponent.body.getPosition().y - camera.position.y;
                        camera.position.y = bodyComponent.body.getPosition().y;
                    }
                    camera.update();

                    spriteComponent.sprite.setPosition(bodyComponent.body.getPosition().x - spriteComponent.sprite.getWidth() / 2,
                            bodyComponent.body.getPosition().y - spriteComponent.sprite.getHeight() / 2);
                    spriteComponent.sprite.setRotation((float) Math.toDegrees(bodyComponent.body.getAngle()));

                    if(bodyComponent.body.getPosition().y + spriteComponent.sprite.getHeight() < camera.position.y - (float)Constants.WORLD_HEIGHT / 2){

                        NewGameScreen.gameEnded = true;
                    }
                }
            }
        }
    }
}
