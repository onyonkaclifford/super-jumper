package com.lush.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.PlayerComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.utils.Constants;

public class PlayerEntity implements BaseEntity{
    private Entity entity;

    public PlayerEntity(PooledEngine engine, World world, float xPos, float yPos){
        entity = engine.createEntity();
        SpriteComponent spriteComponent = engine.createComponent(SpriteComponent.class);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        spriteComponent.sprite = new Sprite(new Texture(Gdx.files.internal("assets/player.png")));
        spriteComponent.sprite.setSize(10, 10);
        spriteComponent.sprite.setOrigin(spriteComponent.sprite.getWidth() / 2,
                spriteComponent.sprite.getHeight() / 2);
        spriteComponent.sprite.setPosition(xPos, yPos);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spriteComponent.sprite.getX() + spriteComponent.sprite.getWidth() / 2,
                spriteComponent.sprite.getY() + spriteComponent.sprite.getHeight() / 2);
        bodyDef.gravityScale = 15;
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.setUserData(spriteComponent.sprite);

        CircleShape shape = new CircleShape();
        shape.setRadius(spriteComponent.sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = Constants.PLAYER;
        fixtureDef.filter.maskBits = Constants.BAR_NOT_SENSOR | Constants.COIN | Constants.SPRING | Constants.ENEMY;
        bodyComponent.fixture = bodyComponent.body.createFixture(fixtureDef);

        shape.dispose();

        entity.add(spriteComponent);
        entity.add(bodyComponent);
        entity.add(playerComponent);
    }

    @Override
    public Entity getEntity(){
        return entity;
    }
}
